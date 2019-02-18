package com.njq.yxl.controller;

import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.vo.DocListVO;
import com.njq.common.util.string.StringUtil;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("yxl")
public class YxlController {

    @Resource
    private YxlDocService yxlDocService;
    @Resource
    private YxlDocSearchService yxlDocSearchService;

    /**
     * 文章预览
     */
    @RequestMapping(value = "docView", method = RequestMethod.GET)
    public String view(Model model, Long id) {
        return "back/docManage/docView";
    }

    /**
     * 查询文章列表（分页）
     */
    @ResponseBody
    @RequestMapping(value = "getDocList", method = RequestMethod.POST)
    public PageList<YxlDocSearch> getDocList(Model model, @RequestParam(required = false, defaultValue = "-1") int page,
                                             @RequestParam(required = false, defaultValue = "-1") int size) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageList<YxlDocSearch> docList = yxlDocSearchService.queryPageList(map, page, size);
        return docList;
    }


    /**
     * 根据id删除文章
     */
    @ResponseBody
    @RequestMapping(value = "delDoc", method = RequestMethod.POST)
    public Map<String, Object> delDoc(@RequestParam Long[] ids, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        yxlDocService.deleteByIds(ids);
        MessageCommon.getSuccessMap();
        return map;
    }

    /**
     * 获取文章列表
     *
     * @param model
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryDocList", method = RequestMethod.POST)
    public PageList<DocListVO> queryDocList(Model model, @RequestParam(required = false, defaultValue = "-1") int page,
                                            @RequestParam(required = false, defaultValue = "-1") int size, String[] tipNames, String searchValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageList<DocListVO> docList = yxlDocSearchService.queryDocList(map, page, size, tipNames, searchValue);
        return docList;
    }


    /**
     * 保存文章内容
     *
     * @param name
     * @param typeId
     * @param doc
     * @param isShow
     * @param tips
     * @param general
     * @param specalType
     * @return
     */
    @RequestMapping(value = "saveKnowledge", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveKnowledge(String name, Long typeId, YxlDoc doc, String isShow, String[] tips, String general, String specalType) {
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            if (StringUtil.IsNotEmpty(doc.getTitle())) {
                /*
                 * 判断用户在1分钟内是否已经发表过文章了，如果发表过了那么不支持发表
                 * 避免系统被爆
                 */
//    	        if(RedisCommon.getString("doc"+UserCommon.getUserId())!=null){
//    	            MessageCommon.getFalseMap(m,"不好意思不能在1分钟内连续发表文章！");
//    	            return m;
//    	        }else{
//    	            RedisCommon.setString("doc"+UserCommon.getUserId(), doc.getTitle(), 1l);
//    	        }
                if (doc.getId() != null) {
                    m = yxlDocService.updateDoc(name, typeId, doc, isShow, tips, 2L, general, specalType);
                } else {
                    m = yxlDocService.saveDoc(name, typeId, doc, isShow, tips, 2L, general, specalType);
                }
                MessageCommon.getSuccessMap(m);
                return m;
            } else {
                return MessageCommon.getFalseMap("请输入标题");
            }
        } catch (Exception e) {
            Map<String, Object> m = new HashMap<String, Object>();
            MessageCommon.getFalseMap(m, e.getMessage());
            return m;
        }
    }

    /**
     * 修改文章内容
     *
     * @param name
     * @param typeId
     * @param doc
     * @param isShow
     * @param tips
     * @param general
     * @return
     */
    @RequestMapping(value = "updateKnowledge", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateKnowledge(String name, Long typeId, YxlDoc doc, String isShow, String[] tips, String general, String specalType) {
        try {
            if (StringUtil.IsNotEmpty(doc.getTitle())) {
                Map<String, Object> m = yxlDocService.updateDoc(name, typeId, doc, isShow, tips, 2L, general, specalType);
                MessageCommon.getSuccessMap(m);
                return m;
            } else {
                return MessageCommon.getFalseMap("请输入标题");
            }
        } catch (Exception e) {
            Map<String, Object> m = new HashMap<String, Object>();
            MessageCommon.getFalseMap(m, e.getMessage());
            return m;
        }
    }

    /**
     * 设置文章展示或不展示
     *
     * @param ids
     * @param isShow
     * @return
     */
    @RequestMapping(value = "setShow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setShow(Long[] ids, String isShow) {
        Map<String, Object> m = new HashMap<String, Object>();
        int n = yxlDocSearchService.updateSearchSetShow(isShow, ids);
        if (n > 0) {
            MessageCommon.getSuccessMap(m);
        } else {
            MessageCommon.getFalseMap(m, "修改失败!");
        }
        return m;
    }

    /**
     * 阅读文章
     *
     * @param model
     * @param docId
     * @return
     */
    @RequestMapping(value = "/knowledge/{docId}", method = RequestMethod.GET)
    public String docView(Model model, @PathVariable(value = "docId") Long docId) {
        YxlDoc doc = yxlDocService.queryById(docId);
        if (doc != null) {
            List<YxlDocSearch> list = yxlDocSearchService.queryTitleList(doc.getId());
            System.out.println(list.get(0).getId());
            model.addAttribute("doc", doc);
            model.addAttribute("titleList", list);
            if (list.size() > 0) {
                model.addAttribute("showLeft", "1");
            }
        }
        return "zxgj/knowledgeDoc";
    }

    /**
     * 直接获取文章内容
     *
     * @param model
     * @param docId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "doc", method = RequestMethod.GET)
    public YxlDoc docText(Model model, Long docId) {
        YxlDoc doc = yxlDocService.queryById(docId);
        if (doc == null) {
            return new YxlDoc();
        }
        return doc;
    }

    @RequestMapping(value = "/editor/{docId}", method = RequestMethod.GET)
    public String editDoc(Model model, @PathVariable(value = "docId") Long docId) {
        YxlDoc doc = yxlDocService.queryById(docId);
        if (doc != null) {
            List<YxlDocSearch> list = yxlDocSearchService.queryTitleList(doc.getId());
            model.addAttribute("doc", doc);
            model.addAttribute("titleList", list);
            if (list.size() > 0) {
                model.addAttribute("showLeft", "1");
            }
        }
        return "zxgj/editDoc";
    }
}
