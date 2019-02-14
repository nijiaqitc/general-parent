package com.njq.grab.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.grab.service.impl.GrabService;
import com.njq.grab.service.impl.GrabUrlInfoService;

@RequestMapping("grab")
@Controller
public class GrabController {
    private static final Logger logger = LoggerFactory.getLogger(GrabController.class);
    @Autowired
    public GrabService grabService;
    @Autowired
    public GrabUrlInfoService grabUrlInfoService;
    @Resource
    private BaseTitleService baseTitleService;

    /**
     * 1、加唯一主键在 title_loading    ok
     * 2、解决js处理出错问题
     * 3、保存文章之前 先修改title_loading的状态为1，并判断是否修改成功，未成功跑出异常 ok
     * 4、java执行js抛出异常，别捕捉异常，要回滚事物 ok
     * 5、url直接以/开头的图片下载要处理下 ok
     * 6、读取doc应该放在事物外面，若放在事物内部会导致事物超时  ok
     * @param model
     * @return
     */

    @RequestMapping("")
    public String index(Model model) {
        return "/grab/grabIndex";
    }

    @RequestMapping("loadJob")
    public void loadJob(Model model) {
        grabService.loadPageJobTask();
    }

    @RequestMapping("loadMenu")
    public void loadMenu(Model model) {
        grabService.loadMenuJobTask();
    }

    @RequestMapping("config")
    public String grabPage(Model model) {
        model.addAttribute("channelList", ChannelType.getChannelValueList());
        return "/grab/grabUrl";
    }

    @ResponseBody
    @RequestMapping("saveAndGrab")
    public String saveAndGrab(String title, String url, String docId,
                              String channel, String type, String tips, Boolean reload) {
    	if(StringUtils.isEmpty(type)) {
    		return "操作失败，类型必填";
    	}
        try {
            grabService.grabOperation(title, url, docId, channel, type, tips, reload);
        } catch (Exception e) {
            logger.info("保存出错", e);
            return e.getMessage();
        }
        return "操作成功！";
    }

    @ResponseBody
    @RequestMapping("grabCustomDoc")
    public String grabCustomDoc(String title, String url, String docId,
                                String channel, String type, String tips, int getType, String name) {
    	if(StringUtils.isEmpty(type)) {
    		return "操作失败，类型必填";
    	}
        try {
            grabService.grabCustomDoc(title, url, docId, channel, type, tips, name, getType);
        } catch (Exception e) {
            logger.info("保存出错", e);
            return e.getMessage();
        }
        return "操作成功！";
    }

    @RequestMapping("loadMenuPage")
    public String loadMenuPage(Model model) {
        model.addAttribute("infoList", grabUrlInfoService.getInfoList(null));
        return "/grab/loadMenu";
    }

    @ResponseBody
    @RequestMapping(value = "queryMenuConfig", method = RequestMethod.POST)
    public String queryMenuConfig(@RequestParam(value = "pageIndex", required = true) String pageIndex) {
        GrabUrlInfo info = grabUrlInfoService.getUrlInfoByIndexPage(pageIndex);
        if (info == null) {
            return "不存在配置，无法读取";
        } else {
            return info.getMenuUrl();
        }
    }

    /**
     * 添加已有的类型下的其他地址
     * @param pageIndex
     * @param menuUrl
     * @param typeName
     * @return
     */
    @ResponseBody
    @RequestMapping("saveNewMenu")
    public String saveNewMenu(@RequestParam(value = "pageIndex", required = true) String pageIndex,
                              @RequestParam(value = "menuUrl", required = true) String menuUrl,
                              @RequestParam(value = "typeName", required = true) String typeName) {
        try {
            grabUrlInfoService.saveUrlInfo(pageIndex, menuUrl, typeName);
        } catch (Exception e) {
            logger.info("保存活动出错", e);
            return e.getMessage();
        }
        return "操作成功！";
    }

    @RequestMapping(value = "/knowledge/{docId}", method = RequestMethod.GET)
    public String docView(Model model, @PathVariable(value = "docId") Long docId) {
        model.addAttribute("doc", grabService.queryById(docId));
        return "grab/knowledgeDoc";
    }

    /**
     * 根据type 和 loadingId 加载文章
     * @param model
     * @param type
     * @param loadingId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadDocNow", method = RequestMethod.GET)
    public String loadDocNow(Model model,
                             @RequestParam(required = false, defaultValue = "false") boolean type,
                             Long loadingId) {
        if (type) {
            return grabService.modiSingleDoc(loadingId);
        } else {
            grabService.loadSingleDoc(loadingId);
        }
        return "访问成功";
    }

    /**
     * 根据 type和url 加载文章
     * @param model
     * @param type
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadDocUrl", method = RequestMethod.GET)
    public String loadDocUrl(Model model,
                             @RequestParam(required = false, defaultValue = "false") boolean type,
                             String url) {
        BaseTitleLoading loading = baseTitleService.getLoadingByUrl(url);
        if (loading != null) {
            this.loadDocNow(model, type, loading.getId());
        }
        return "访问成功";
    }

    @ResponseBody
    @RequestMapping(value = "starTitle", method = RequestMethod.POST)
    public String starTitle(Model model, Long titleId, Boolean isStar) {
        baseTitleService.updateStarTitle(titleId, isStar);
        return "标记或取消成功";
    }

    @ResponseBody
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    public String updateStatus(Long id, Boolean status) {
        grabUrlInfoService.updateStatus(id, status);
        return "处理成功";
    }

    /**
     * 在原文章上添加标签
     * @param model
     * @param docId
     * @param tipName
     */
    @ResponseBody
    @RequestMapping(value = "addTip", method = RequestMethod.POST)
    public Map<String, Object> addTip(Model model, Long docId, String tipName) {
    	if(docId == null) {
    		return  MessageCommon.getFalseMap("参数有误");
    	}
    	if(tipName == null) {
    		return  MessageCommon.getFalseMap("参数有误");
    	}
        grabService.updateTips(tipName, docId);
        return MessageCommon.getSuccessMap();
    }
}
