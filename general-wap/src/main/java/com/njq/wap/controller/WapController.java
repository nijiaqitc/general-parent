/**
 * wap页面接口
 */
package com.njq.wap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.njq.common.base.interceptor.NeedPwd;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.BaseCodeService;
import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.model.po.TbkDoc;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkTip;
import com.njq.common.model.po.TbkType;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlNote;
import com.njq.common.model.po.YxlNoteGeneral;
import com.njq.common.util.other.PropertyUtil;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.impl.GrabService;
import com.njq.tbk.service.TbkDocService;
import com.njq.tbk.service.TbkPicService;
import com.njq.tbk.service.TbkTipService;
import com.njq.tbk.service.TbkTypeService;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;
import com.njq.yxl.service.YxlNoteService;
import com.njq.zxgj.service.ToolNameService;


@RequestMapping("wap")
@Controller
public class WapController {

    @Resource
    public TbkTypeService tbktypeService;
    @Resource
    public TbkDocService tbkdocService;
    @Resource
    public TbkPicService tbkpicService;
    @Resource
    public TbkTipService tbktipService;
    @Resource
    public BaseUserService userService;
    @Resource
    public ToolNameService nameService;
    @Resource
    public BaseCodeService codeService;
    @Resource
    private YxlDocSearchService yxlDocSearchService;
    @Resource
    private YxlDocService yxlDocService;
    @Resource
    private YxlNoteService yxlNoteService;
    
    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping(value = "/toIndex", method = RequestMethod.GET)
    public String toIndex(Model model) {
//		model.addAttribute("typeVoList", tbkdocService.queryTypeVO(1,10));
        model.addAttribute("yxlList", yxlDocSearchService.queryyxlType());
        return "wap/index";
    }

    @RequestMapping(value = "/toQueryDocList", method = RequestMethod.GET)
    public String toQueryDocList(Model model, String typeName) {
        model.addAttribute("docList", yxlDocSearchService.queryyxlTypeList(typeName, null, null));
        if (StringUtil.IsNotEmpty(typeName)) {
            model.addAttribute("typeName", typeName);
        }
        return "wap/toQueryDocList";
    }

    @RequestMapping(value = "/reloadDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reloadDoc(Model model, @RequestParam(required = false, defaultValue = "0") int type,
                                         @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size, String typeName) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> queryyxlTypeList = yxlDocSearchService.queryyxlTypeList(typeName, page, size);
        map.put("docList", queryyxlTypeList);
        if (StringUtil.IsNotEmpty(typeName)) {
            map.put("typeName", typeName);
        }
        return map;
    }

    @RequestMapping(value = "/doc/{docId}", method = RequestMethod.GET)
    public String doc(Model model, @PathVariable(value = "docId") Long docId) {
        YxlDoc doc = yxlDocService.queryById(docId);
        model.addAttribute("docViewText", doc);
        return "wap/yxldoc";
    }

    /**
     * 消息
     *
     * @return
     */
    @RequestMapping(value = "/toMessage", method = RequestMethod.GET)
    public String toMessage() {
        return "wap/message";
    }

    @NeedPwd
    @RequestMapping(value = "/note/noteList", method = RequestMethod.GET)
    public String noteList(Model model) {
        List<YxlNoteGeneral> list = yxlNoteService.queryTitleList();
        model.addAttribute("noteList", list);
        return "wap/noteList";
    }

    @NeedPwd
    @RequestMapping(value = "/note/{noteId}", method = RequestMethod.GET)
    public String note(Model model, @PathVariable(value = "noteId") Long noteId) {
        YxlNote doc = yxlNoteService.queryById(noteId);
        model.addAttribute("docViewText", doc);
        return "wap/yxlnote";
    }

    

    /**
     * 跳转到动态
     *
     * @return
     */
    @RequestMapping(value = "/toTrends", method = RequestMethod.GET)
    public String toTrends(Model model, String typeName) {
        List<TbkRecommendDocView> viewList = tbkdocService.queryDocForTypeList(1, 10, typeName);
        model.addAttribute("viewList", viewList);
        if (StringUtil.IsNotEmpty(typeName)) {
            model.addAttribute("typeName", typeName);
        }
        return "wap/docTrends";
    }

    /**
     * 加载动态文章
     *
     * @param model
     * @param type  0:重新加载 1:后续加载
     * @param page
     * @param size
     * @return 2016-6-13
     * author njq
     */
    @RequestMapping(value = "/loadTrends", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reloadTrends(Model model, @RequestParam(required = false, defaultValue = "0") int type,
                                            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size, String typeName) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TbkRecommendDocView> viewList = tbkdocService.queryDocForTypeList(page, size, typeName);
        map.put("docList", viewList);
        if (StringUtil.IsNotEmpty(typeName)) {
            map.put("typeName", typeName);
        }
        return map;
    }

    @RequestMapping(value = "/toGrab", method = RequestMethod.GET)
    public String toGrab() {
        return "wap/grab";
    }

    /**
     * 我的
     *
     * @return
     */
    @RequestMapping(value = "/toAboutMe", method = RequestMethod.GET)
    public String toAboutMe() {
        return "wap/aboutMe";
    }

    /**
     * 文章预览
     *
     * @param model
     * @return 2016年6月28日
     * author njq
     */
    @RequestMapping(value = "/docView/{docId}", method = RequestMethod.GET)
    public String docView(Model model, @PathVariable(value = "docId") Long docId) {
        //正文内容
        TbkDoc doc = tbkdocService.queryDocByUrlAndId(docId);
        if (doc != null) {
            TbkPic tbkPic = tbkpicService.queryTbkpicByDocId(docId);
            TbkType tbkType = tbktypeService.queryTbktypeByDocId(docId);
            List<TbkTip> tbkTipList = tbktipService.queryTbktipByDocId(docId);
            //如果字符串中含有回车符将会导致页面接收字符串直接报错！所以要替换掉所有的回车符
            doc.setText(doc.getText().replaceAll("(\r|\n|\r\n|\n\r)", ""));
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
            List<TbkRecommendDocView> viewList = tbkdocService.queryRecommendDoc(new HashMap<String, Object>(), 1, 5);
            model.addAttribute("recommendDocList", viewList);
            tbkdocService.updateDocById(doc.getId(), null);
        }
        model.addAttribute("authUrl", PropertyUtil.get("auth.url") + "/doc/docView/" + docId);
        return "wap/docView";
    }

    /**
     * 文章搜索
     *
     * @param model
     * @param searchValue
     * @param page
     * @param size
     * @return 2016年6月30日
     * author njq
     */
    @RequestMapping(value = "searchDoc", method = RequestMethod.GET)
    public String searchDoc(Model model, @RequestParam String searchValue, @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "10") int size) {
        List<TbkRecommendDocView> list = tbkdocService.querySearchDocForList(page, size, searchValue.split(" "));
        model.addAttribute("viewList", list);
        model.addAttribute("searchValue", StringUtil.StringsToString(searchValue.split(" ")));
        return "wap/searchDocList";
    }

    /**
     * 重新加载搜索的文章
     *
     * @param model
     * @param searchValue
     * @param page
     * @param size
     * @return 2016年6月30日
     * author njq
     */
    @RequestMapping(value = "/reLoadsearchDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reLoadsearchDoc(Model model, @RequestParam String[] searchValue, @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TbkRecommendDocView> list = tbkdocService.querySearchDocForList(page, size, searchValue);
        map.put("docList", list);
        if (StringUtil.IsNotEmpty(searchValue)) {
            map.put("searchValue", searchValue);
        }
        return map;
    }

    /**
     * 跳转到选区名字页面
     *
     * @param type1
     * @param type2
     * @return
     */
    @RequestMapping(value = "/toSelectName", method = RequestMethod.GET)
    public String toSelectName(String type1, String type2) {
        return "wap/nameTypeForSelect";
    }

    /**
     * 获取名字（非人名）
     *
     * @param type
     * @param typeName
     * @param model
     * @return
     */
    @RequestMapping(value = "/toOtherName", method = RequestMethod.GET)
    public String toOtherName(Long type, String typeName, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("codeList", codeService.queryChildrenCodeList(null, type));
        model.addAttribute("typeName", typeName);
        return "wap/getOtherName";
    }

    /**
     * 跳转到获取名字页面
     *
     * @param type1
     * @param type2
     * @return
     */
    @RequestMapping(value = "/toPeopleName", method = RequestMethod.GET)
    public String toPeopleName(String type1, String type2) {
        return "wap/getName";
    }

    /**
     * 获取名字（人名）
     *
     * @param type1
     * @param type2
     * @return
     */
    @RequestMapping(value = "/getName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getName(String type1, String type2, String namea, String nameb) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", nameService.queryName(type1, type2, namea, nameb));
        return map;
    }


    /**
     * 获取名字（非人名）
     *
     * @param type
     * @param type2
     * @param check
     * @return
     */
    @RequestMapping(value = "/getOtherName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOtherName(String type, String type2, String check) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", nameService.queryOtherName(type, type2, check));
        map.put("type", type);
        return map;
    }

    /**
     * 删除对应的字
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/delName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delName(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        nameService.updateName(name);
        return map;
    }


    @RequestMapping(value = "/zjJump", method = RequestMethod.GET)
    public String zjJump(Model model) {
        return "wap/zjJump";
    }

}
