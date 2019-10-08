package com.njq.grab.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.basis.service.impl.BaseTypeService;
import com.njq.basis.service.impl.UpFileService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.interceptor.NeedPwd;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.LabelNameVO;
import com.njq.common.model.vo.XtitleVO;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.impl.GrabService;
import com.njq.grab.service.impl.GrabUrlInfoService;
import com.njq.grab.service.impl.csdn.CsdnCookieLoadOperation;
import com.njq.grab.service.impl.novel.NovelLoadPerformer;
import com.njq.grab.service.impl.novel.NovelSearchPerformer;
import com.njq.grab.service.impl.novel.XxsyConsultPerformer;

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
    @Resource
    private BaseTipService baseTipService;
    @Resource
    private BaseTypeService baseTypeService;
    @Autowired
    private UpFileService upFileService;
    
    @NeedPwd
    @RequestMapping("")
    public String pandect(Model model) {
        return "/grab/grabPandect";
    }
    
    @RequestMapping("index")
    public String index(Model model) {
        return "/grab/grabIndex";
    }

    @ResponseBody
    @RequestMapping("loadJob")
    public String loadJob(Model model) {
        grabService.loadPageJobTask();
        return "正在处理中...";
    }

    @ResponseBody
    @RequestMapping("loadMenu")
    public String loadMenu(Model model) {
        grabService.loadMenuJobTask();
    	return "正在处理中...";
    }

    @ResponseBody
    @RequestMapping("reloadJob")
    public String reloadJob(Model model,@RequestParam(required=false) String channel,
    		@RequestParam(required=false) Long docId) {
    	if(StringUtil.isEmpty(channel)&&docId==null) {
    		return "channel和docId不能同时为空！";	
    	}
    	grabService.reloadPageJobTask(channel, docId);
    	return "正在处理中...";
    }
    
    @RequestMapping("editKnowledge")
    public String editKnowledge(Model model, @RequestParam Long docId) {
    	if(docId == null) {
    		return "/grab/noDoc";
    	}
    	GrabDoc doc = grabService.readDoc(docId);
    	model.addAttribute("doc", doc);
    	return "/grab/editKnowledge";
    }
    
    @ResponseBody
    @RequestMapping("editToSave")
    public Map<String, Object> editToSave(Model model, HttpServletRequest req ) {
    	MultipartHttpServletRequest params=((MultipartHttpServletRequest) req); 
    	String docId=params.getParameter("docId");
    	String text = params.getParameter("text");
    	String title = params.getParameter("title");
    	if(StringUtil.isEmpty(text)) {
    		return MessageCommon.getFalseMap("内容为空，暂不保存！");
    	}
    	grabService.updateDoc(Long.parseLong(docId), text,title);
    	return MessageCommon.getSuccessMap();
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
        if (StringUtils.isEmpty(type)) {
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
        if (StringUtils.isEmpty(type)) {
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
     *
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
    public String docView(Model model, @PathVariable(value = "docId") Long docId,Boolean allInfo) {
    	GrabDoc doc = grabService.readDoc(docId);
    	if(doc == null) {
    		return "grab/noDoc";
    	}
    	BaseTitle title = baseTitleService.getTitleByDocId(docId);
    	if(allInfo!=null&&allInfo) {
    		BaseTitleLoading loading =  baseTitleService.getLoadingByTitleId(title.getId());
    		model.addAttribute("loaded", loading);
    	}
    	Pair<BaseTitle,BaseTitle> pairTitle = baseTitleService.getlrTitle(title.getId());
    	model.addAttribute("titleInfo", title);
    	if(pairTitle.getLeft() != null) {
    		XtitleVO leftTitle = new XtitleVO();
    		BeanUtils.copyProperties(pairTitle.getLeft(), leftTitle);
    		model.addAttribute("leftTitle", leftTitle);
    	}
    	if(pairTitle.getRight() != null) {
    		XtitleVO rightTitle = new XtitleVO();
    		BeanUtils.copyProperties(pairTitle.getRight(), rightTitle);
    		model.addAttribute("rightTitle", rightTitle);
    	}
        model.addAttribute("doc", doc);
        model.addAttribute("tipList", baseTipService.getTipsByTitleId(title.getId()));
        model.addAttribute("topList", baseTipService.getTopTips());
        return "grab/knowledgeDoc";
    }

    /**
     * 根据type 和 loadingId 加载文章
     *
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
     *
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
     *
     * @param model
     * @param docId
     * @param tipName
     */
    @ResponseBody
    @RequestMapping(value = "addTip", method = RequestMethod.POST)
    public Map<String, Object> addTip(Model model, Long docId, String tipName) {
        if (docId == null) {
            return MessageCommon.getFalseMap("参数有误");
        }
        if (StringUtils.isEmpty(tipName)) {
            return MessageCommon.getFalseMap("标签不能为空！");
        }
        grabService.updateTips(tipName, docId);
        return MessageCommon.getSuccessMap();
    }


    @ResponseBody
    @RequestMapping(value = "reloadFile", method = RequestMethod.GET)
    public String reloadFile(){
        grabService.reloadFile();
        return "执行成功";
    }

    @ResponseBody
    @RequestMapping(value = "fileLoadQuery", method = RequestMethod.GET)
    public String fileLoadQuery(){
        grabService.fileLoadQuery();
        return "执行成功";
    }
    
    @ResponseBody
    @RequestMapping(value = "getTipList", method = RequestMethod.POST)
    public List<LabelNameVO> getTipList(Model model){
    	return baseTipService.getAllTips();
    }
    
    
    @ResponseBody
    @RequestMapping(value = "getTypeList", method = RequestMethod.POST)
    public List<LabelNameVO> getTypeList(Model model){
    	return baseTypeService.getAllTypes();
    }
    
    @RequestMapping(value="showTitleListByType" , method= RequestMethod.GET)
    public String showTitleListByType(Long typeId,Model model) {
    	model.addAttribute("typeInfo", baseTypeService.getTypeById(typeId));
    	model.addAttribute("typeId", typeId);
    	return "grab/menuListByType";
    }
    
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value="getTitleListByType" , method= RequestMethod.POST)
    public List<BaseTitle> getTitleListByType(Long typeId,Long parentId) {
    	if(typeId == null) {
    		return Collections.EMPTY_LIST;
    	}
    	return baseTitleService.getTitleByType(typeId,parentId);
    }
    
    @RequestMapping(value="showTitleListByTip" , method= RequestMethod.GET)
    public String showTitleListByTip(Long tipId,String tipName,Model model) {
    	if(tipId != null) {
    		model.addAttribute("tipInfo", baseTipService.getById(tipId));    		
    	}
    	if(!StringUtils.isEmpty(tipName)) {
    		model.addAttribute("tipInfo", baseTipService.getByName(tipName));
    	}
    	model.addAttribute("tipId", tipId);
    	model.addAttribute("tipName", tipName);
    	return "grab/menuListByTip";
    }
    
    @RequestMapping(value="searchForList" , method= RequestMethod.GET)
    public String searchForList(String searchValue,Boolean star , Model model) {
    	if(star!=null&&star) {
    		model.addAttribute("titleList",baseTitleService.getStarTitleList());
    		model.addAttribute("searchTitle", "星标");
    	}else {
    		model.addAttribute("titleList", baseTitleService.getSearchTitleList(StringUtil.isEmpty(searchValue) ? null : searchValue.trim().split(" ")));
    		model.addAttribute("searchTitle", searchValue);    		
    	}
    	return "grab/searchList";
    }
    
    @RequestMapping(value="docListPage" , method= RequestMethod.GET)
    public String docListPage(Integer page , Model model) {
    	return "grab/docList";
    }
    
    @ResponseBody
    @RequestMapping(value="pageList" , method= RequestMethod.POST)
    public PageList<BaseTitle> pageList(@RequestParam(required=false,defaultValue="1") Integer page , Model model) {
    	return baseTitleService.searchPage(page, 20);
    }
    
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value="getTitleListByTip" , method= RequestMethod.POST)
    public List<BaseTitle> getTitleListByTip(Long tipId,String tipName) {
    	if(tipId == null&&StringUtils.isEmpty(tipName)) {
    		return Collections.EMPTY_LIST;
    	}
    	if(tipId != null) {
    		return baseTitleService.getTitleByTip(tipId);    		
    	}
    	if(!StringUtils.isEmpty(tipName)) {
    		return baseTitleService.getTitleByTipName(tipName);
    	}
    	return Collections.EMPTY_LIST;
    }
    
    @RequestMapping(value = "editPicUp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editPicUp(HttpServletRequest req) {
    	
    	return upFileService.upEditPic(req);
    }
    
    
    
    @Autowired
    private NovelSearchPerformer performer;
    
    
    
    @RequestMapping(value = "loadbook", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loadbook(String bookName, HttpServletRequest req) {
    	if(StringUtil.isEmpty(bookName)) {
    		return MessageCommon.getFalseMap("请输入书名");
    	}
    	performer.search(bookName.trim());
    	return MessageCommon.getSuccessMap();
    }
    
    
    @RequestMapping(value = "loaddoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loaddoc(HttpServletRequest req) {
    	performer.loadDoc();
    	return MessageCommon.getSuccessMap();
    }
    
    
    @RequestMapping(value = "upNovel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upNovel(Long[] menuId) {
    	if(menuId != null) {
    		for(Long id:menuId) {
    			performer.loadMenu(null, id);    			
    		}
    	}
    	return MessageCommon.getSuccessMap();
    }
    
    
    
    @Resource
    private NovelLoadPerformer dingdianLoadPerformer;
    @Resource
    private XxsyConsultPerformer xxsyConsultPerformer;
    @RequestMapping(value = "searchtest", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchtest(String bookName, HttpServletRequest req) {
    	dingdianLoadPerformer.loadMenu("https://www.23wx.so/62_62292/", 0L);
    	return MessageCommon.getSuccessMap();
    }
    
    @RequestMapping(value = "grabPage", method = RequestMethod.GET)
    public String grabPage(String url,String cookie){
    	return "grab/loadUrl";
    }
    
    @Resource
    private CsdnCookieLoadOperation csdnCookieLoadOperation;
    
    @RequestMapping(value = "grabAppointPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> grabAppointPage(String url,String cookie){
    	csdnCookieLoadOperation.load(url, cookie);
    	return MessageCommon.getSuccessMap();
    }
    
    
}
