package com.njq.tbk.controller;
/**
 * 文章管理接口
 */

import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.TbkDoc;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkTip;
import com.njq.common.model.po.TbkType;
import com.njq.common.util.date.DateUtil;
import com.njq.tbk.service.TbkDocService;
import com.njq.tbk.service.TbkPicService;
import com.njq.tbk.service.TbkTipService;
import com.njq.tbk.service.TbkTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("doc")
@Controller
public class DocController {

    @Resource
    public TbkDocService tbkdocService;
    @Resource
    public TbkPicService tbkpicService;
    @Resource
    public TbkTipService tbktipService;
    @Resource
    public TbkTypeService tbktypeService;
    @Resource
    public BaseUserService userService;

    /**
     * 跳转到后台主页
     *
     * @return 2015-11-2
     * author njq
     */
    @RequestMapping(value = "docManage", method = RequestMethod.GET)
    public String jumpToPage(Model model) {
        return "back/docManage/docManage";
    }

    /**
     * 预览文档
     *
     * @param model
     * @return 2016-3-30
     * author njq
     */
    @RequestMapping(value = "/docView/{docId}", method = RequestMethod.GET)
    public String docView(Model model, @PathVariable(value = "docId") Long docId, String ismob) {
        //正文内容
        TbkDoc doc = tbkdocService.queryDocByUrlAndId(docId);
        if (doc != null) {
            TbkPic tbkPic = tbkpicService.queryTbkpicByDocId(docId);
            TbkType tbkType = tbktypeService.queryTbktypeByDocId(docId);
            List<TbkTip> tbkTipList = tbktipService.queryTbktipByDocId(docId);
            model.addAttribute("docViewText", doc);
            model.addAttribute("docViewTbkPic", tbkPic);
            model.addAttribute("docViewTbkTipList", tbkTipList);
            String tipList = "";
            /*
             * 将tip组合成"aaa,bbb,vvv"的格式用于放在页面中的meta中
             */
            for (TbkTip tip : tbkTipList) {
                tipList += tip.getName() + ",";
            }
            if (tipList.length() > 0) {
                model.addAttribute("tipString", tipList.substring(0, tipList.length() - 1));
            }
            model.addAttribute("tbkType", tbkType);
            model.addAttribute("author", userService.queryUserById(doc.getCreateBy()).getUserName());
            //左侧推荐文章目前取5篇
            List<TbkRecommendDocView> viewList = tbkdocService.queryRecommendDoc(new HashMap<String, Object>(), docId, 1, 5);
            model.addAttribute("recommendDocList", viewList);
            model.addAttribute("ismob", ismob);
            tbkdocService.updateDocById(doc.getId(), null);
            return "tbk/doc";
        } else {
            return "tbk/nothing";
        }
    }

    /**
     * 图片墙显示文章列表根据阅读数从高到低排行
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "queryDocByNumDesc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryLittleDoc(@RequestParam(required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TbkRecommendDocView> viewList = tbkdocService.queryRecommendDoc(map, page, 8);
        map.put("viewList", viewList);
        if (viewList.size() > 0) {
            MessageCommon.getSuccessMap(map);
        } else {
            MessageCommon.getFalseMap(map);
        }
        return map;
    }

    /**
     * 检索文章
     *
     * @param model
     * @param searchValue 检索关键字
     * @return
     */
    @RequestMapping(value = "searchDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchDoc(Model model, String[] searchValue, int page, int size) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (searchValue.length > 0) {
            PageList<TbkRecommendDocView> list = tbkdocService.querySearchDoc(page, size, searchValue);
            map.put("doc", list);
            if (list.getTotal() > 0) {
                MessageCommon.getSuccessMap(map);
            } else {
                MessageCommon.getFalseMap(map);
                map.put("message", "未查询到数据！");
            }
        } else {
            MessageCommon.getFalseMap(map);
            map.put("message", "请输入检索条件！");
        }
        return map;
    }

    /**
     * 跳转到显示相关类型的文章
     * @param model
     * @param typeId
     * @return
     */
    @RequestMapping(value = "docForType/{typeId}", method = RequestMethod.GET)
    public String searchDocForType(Model model, @PathVariable("typeId") Long typeId) {
        model.addAttribute("typeVoList", tbkdocService.queryTypeVO(1, 10));
        model.addAttribute("typeId", typeId);
        model.addAttribute("selectType", tbktypeService.queryTbktypeById(typeId));
        return "tbk/docList";
    }

    /**
     * 根据类型查询文章列表
     *
     * @param model
     * @param typeId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "docListForType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchDocForType(Model model, Long typeId, @RequestParam(defaultValue = "1", required = false) int page,
                                                @RequestParam(defaultValue = "10", required = false) int size) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageList<TbkRecommendDocView> pageList = tbkdocService.queryDocForType(page, size, typeId);
        map.put("doc", pageList);
        if (pageList.getTotal() > 0) {
            MessageCommon.getSuccessMap(map);
        } else {
            MessageCommon.getFalseMap(map, "暂无数据");
        }
        return map;
    }

    /**
     * 无须登录快速发表文章
     *
     * @param doc     发表的文章
     * @param errors
     * @param typeId  类型id
     * @param tips    标签字符串（标签1,标签2,标签3）
     * @param docType 文章类型 1为原创  2为转载
     * @return
     */
    @RequestMapping(value = "fastIssueDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fastIssueDoc(@Valid TbkDoc doc, Errors errors, @RequestParam(required = true) Long typeId,
                                            @RequestParam(required = true) String tips, @RequestParam(required = false, defaultValue = "2") String docType) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (errors.hasErrors()) {
            for (FieldError file : errors.getFieldErrors()) {
                map.put("message", file.getField() + file.getDefaultMessage());
            }
            map.put("state", "0");
            return map;
        }
        doc.setUserId(2l);
        doc.setCreateBy(2l);
        doc.setModiBy(2l);
        doc.setCreateDate(new Timestamp(System.currentTimeMillis()));
        if ("2".equals(docType)) {
            doc.setReprint(2);
            tbkdocService.saveDoc(doc, typeId, tbkpicService.queryZhuanPicForRandom(), tips.replace(" ", "").split("&"), 2l, map);
        } else {
            doc.setReprint(1);
            tbkdocService.saveDoc(doc, typeId, tbkpicService.queryYuanPicForRandom(), tips.replace(" ", "").split("&"), 2l, map);
        }
        return map;
    }

    @RequestMapping(value = "openPostWindow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> openPostWindow(Model model, @RequestParam(required = true) String text, @RequestParam(required = true) String general,
                                              @RequestParam(required = true) String tips, @RequestParam(required = true) String title, @RequestParam(required = false, defaultValue = "2") String docType, HttpSession session) {
        session.setAttribute("docViewText", text);
        if (docType.equals("2")) {
            session.setAttribute("docViewGeneral", general.split("&&")[1].toString());
        } else {
            session.setAttribute("docViewGeneral", general);
        }
        session.setAttribute("docViewTips", tips);
        session.setAttribute("docViewTitle", title);
        session.setAttribute("docViewTime", DateUtil.toDateString1(new Date()));
        session.setAttribute("docType", docType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", 1);
        map.put("message", "ok");
        return map;
    }


    @RequestMapping(value = "fastView", method = RequestMethod.GET)
    public String fastView(Model model) {
        return "tbk/docView";
    }


    @RequestMapping("test")
    public void test(Model model) {
        tbktipService.test();
    }

}
