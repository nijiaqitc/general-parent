package com.njq.wap.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.interceptor.NeedPwd;
import com.njq.common.base.other.IpUtil;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.GrabNovelDoc;
import com.njq.common.model.po.GrabNovelMenu;
import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsDocUserOp;
import com.njq.common.model.po.XsTitleDesign;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.model.vo.NovelDocVO;
import com.njq.common.model.vo.TitlethcVO;
import com.njq.common.util.string.StringUtil;
import com.njq.xs.service.XsDocDetailService;
import com.njq.xs.service.XsDocDiscussService;
import com.njq.xs.service.XsDocUserOpService;
import com.njq.xs.service.XsTitleDesignService;
import com.njq.xs.service.XsTitleDetailService;


@RequestMapping("novel")
@Controller
public class NovelController {

    @Resource
    public XsTitleDesignService titleService;
    @Resource
    public XsDocDiscussService docDiscussService;
    @Resource
    public XsDocUserOpService docUserOpService;
    @Resource
    public XsDocDetailService docDetailService;
    @Resource
    public XsTitleDetailService xsTitleDetailService; 
    /**
     * 添加章节
     *
     * @param title
     * @return
     */
    @RequestMapping(value = "/addTitle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addTitle(XsTitleDesign title) {
        Map<String, Object> map = new HashMap<String, Object>();
        titleService.saveTitle(title);
        System.out.println(title);
        map.put("index", title.getIndexOne());
        map.put("title", title.getTitle());
        map.put("grade", title.getGrade());
        map.put("desc", title.getContextDesc());
        MessageCommon.getSuccessMap(map);
        return map;
    }

    /**
     * 修改章节
     *
     * @param title
     * @return
     */
    @RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateTitle(XsTitleDesign title) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", title.getIndexOne());
        map.put("title", title.getTitle());
        map.put("grade", title.getGrade());
        map.put("desc", title.getContextDesc());
        titleService.updateTitleById(title);
        MessageCommon.getSuccessMap(map);
        return map;
    }

    /**
     * 删除章节
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delTitle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delTitle(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        titleService.deleteTitleById(id);
        MessageCommon.getSuccessMap(map);
        return map;
    }


    /**
     * 查询章节
     *
     * @param model
     * @param docId
     * @return
     */
    @RequestMapping(value = "/queryTitleList", method = RequestMethod.GET)
    public String queryTitleList(Model model, Long docId) {
        model.addAttribute("titleList", titleService.queryDocTitleList(docId));
        model.addAttribute("docId", docId);
        XsTitleDesign thc = titleService.queryNameById(docId);
        model.addAttribute("titleName", thc.getTitle());
        return "wap/titlethc";
    }

    /**
     * 查询文章标题列表
     */
    @NeedPwd
    @RequestMapping(value = "/showDocTitleList", method = RequestMethod.GET)
    public String showDocTitleList(Model model) {
        List<TitlethcVO> list = titleService.queryDocList(0L);
        model.addAttribute("list", list);
        return "wap/docTitleList";
    }

    /**
     * 保存书本名字
     *
     * @return
     */
    @RequestMapping(value = "/saveDocName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveDocName(String newName, String oldName, Long id, String contextDetail) {
        Map<String, Object> m = new HashMap<String, Object>();
        XsTitleDesign thc = new XsTitleDesign();
        thc.setParentId(0L);
        thc.setTitle(newName);
        thc.setContextDesc(contextDetail);
        thc.setType(ConstantsCommon.Novel_Type.TYPE_DOC);
        if (StringUtil.IsNotEmpty(id)) {
            thc.setId(id);
            titleService.updateTitleById(thc);
        } else {
            titleService.saveTitle(thc);
        }
        MessageCommon.getSuccessMap(m);
        return m;
    }

    /**
     * 保存卷名
     *
     * @param newName
     * @param oldName
     * @param id
     * @return
     */
    @RequestMapping(value = "/saveJuanName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveJuanName(String newName, String oldName, Long id, Long docId) {
        Map<String, Object> m = new HashMap<String, Object>();
        XsTitleDesign thc = new XsTitleDesign();
        thc.setParentId(docId);
        thc.setTitle(newName);
        thc.setType(ConstantsCommon.Novel_Type.TYPE_JUAN);
        if (StringUtil.IsNotEmpty(id)) {
            thc.setId(id);
            if (docId == null) {
                MessageCommon.getFalseMap(m);
                return m;
            }
            titleService.updateTitleById(thc);
        } else {
            titleService.saveTitle(thc);
            XsTitleDesign t = new XsTitleDesign();
            t.setIndexOne(1);
            t.setTitle("初始章节");
            t.setType(ConstantsCommon.Novel_Type.TYPE_TITLE);
            t.setParentId(thc.getId());
            t.setGrade("0");
            titleService.saveTitle(t);
        }
        MessageCommon.getSuccessMap(m);
        return m;
    }


    /**
     * 跳转到demo页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/demoShow", method = RequestMethod.GET)
    public String demoShow(Model model) {
        return "wap/demo";
    }
    
    
    /*@RequestMapping(value="/showDocList",method=RequestMethod.GET)
    public String showDocList(Model model){
        return "docList";
    }*/


    /**
     * 阅读文章
     *
     * @param model
     * @param docId
     * @return
     */
    @SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/readDoc/{docId}", method = RequestMethod.GET)
    public String readDoc(Model model, @PathVariable(value = "docId") Long docId, HttpServletRequest request) {
    	XsTitleDetail title = xsTitleDetailService.queryById(docId);
        XsDocDetail detail = docDetailService.queryById(title.getId());
        NovelDocVO novel = new NovelDocVO();
//        novel.setDocId(detail.getThcId());
        novel.setCreateDate(detail.getCreateDate());
        novel.setText(detail.getDoc());
        novel.setTitle(detail.getTitle());
        Map<Integer, Integer> m = docUserOpService.queryDocOpNum(docId);
        if (m != null) {
            novel.setDown(m.get(ConstantsCommon.User_Op.DOWN) == null ? 0 : m.get(ConstantsCommon.User_Op.DOWN));
            novel.setGood(m.get(ConstantsCommon.User_Op.UP) == null ? 0 : m.get(ConstantsCommon.User_Op.UP));
            novel.setView(m.get(ConstantsCommon.User_Op.VIEW) == null ? 0 : m.get(ConstantsCommon.User_Op.VIEW));
        } else {
            novel.setDown(0);
            novel.setGood(0);
            novel.setView(0);
        }
        model.addAttribute("novel", novel);
        //用户浏览记录到数据库
        this.userOp(docId, "1", null, null, "1", request);
        List<XsDocUserOp> opList = docUserOpService.queryByDocIdAndIp(IpUtil.getIpAddr(request), docId);
        for (XsDocUserOp op : opList) {
            if (op.getOp().equals(ConstantsCommon.User_Op.UP)) {
                model.addAttribute("docUp", 1);
            }
            if (op.getOp().equals(ConstantsCommon.User_Op.DOWN)) {
                model.addAttribute("docDown", 1);
            }
        }
        return "wap/readDoc";
    }

    /**
     * 保存用户操作
     *
     * @param docId
     * @param op
     * @param userId
     * @param titleId
     * @param request
     * @return
     */
    @RequestMapping(value = "/userOp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userOp(Long docId, @RequestParam(required = true) String op, Long userId, Long titleId, String appId,
                                      HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        int num = docUserOpService.queryCount(op, ip, docId, appId);
        if (num == 0) {
            XsDocUserOp userOp = new XsDocUserOp();
            userOp.setCreateDate(new Date());
            userOp.setModiDate(new Timestamp(System.currentTimeMillis()));
            userOp.setDocId(docId);
            userOp.setOp(op);
            userOp.setUserIp(ip);
            userOp.setUserId(userId);
            userOp.setTitleId(titleId);
            userOp.setAppId(appId);
            docUserOpService.saveObject(userOp);
        }
        return MessageCommon.getSuccessMap();
    }

    /**
     * 发表文章
     *
     * @param title
     * @param text
     * @param docId
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveNovel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveNovel(String title, String text, Long docId, Long userId, HttpServletRequest request) {
        XsDocDetail detail = new XsDocDetail();
        detail.setCreateDate(new Date());
        detail.setDoc(text);
        detail.setTitle(title);
        detail.setUserId(userId);
        docDetailService.saveObject(detail);
        return MessageCommon.getSuccessMap();
    }
    
    
    
    
    
    @RequestMapping(value = "/queryNovelTitle", method = RequestMethod.GET)
    public String queryNovelTitle(Model model,HttpServletRequest request,@RequestParam(required=false) Long parentId,@RequestParam(required=false,defaultValue="desc") String sort) {
    	List<TitlethcVO> list = titleService.queryGrabNovelTitleList(parentId,sort);
        model.addAttribute("list", list);
        if(parentId !=null) {
        	GrabNovelMenu menu = titleService.queryMenu(parentId);
        	model.addAttribute("title", menu.getName());
        	return "wap/grabNovelMenu";        	
        }else {
        	return "wap/grabNovelTitle";
        }
    }
    
    
    
    @RequestMapping(value = "/queryNovelDoc", method = RequestMethod.GET)
    public String queryNovelDoc(Model model,HttpServletRequest request,Long menuId) {
        GrabNovelMenu beforeMenu = titleService.queryBeforeMenu(menuId);
        model.addAttribute("menuId", menuId);
        model.addAttribute("beforeMenuId", beforeMenu.getId());
        return "wap/grabNovelDoc";
    }
    
    @RequestMapping(value = "/getNovelDoc", method = RequestMethod.POST)
    @ResponseBody
    public NovelDocVO getNovelDoc(Model model,HttpServletRequest request,Long menuId) {
    	if(menuId == null) {
    		return null;
    	}
    	GrabNovelMenu menu = titleService.queryMenu(menuId);
    	GrabNovelDoc doc = titleService.queryGrabNovelDoc(menuId);
        NovelDocVO novel = new NovelDocVO();
        novel.setCreateDate(doc.getCreateDate());
        novel.setText(doc.getDoc());
        novel.setTitle(menu.getName());
        GrabNovelMenu nextMenu = titleService.queryNextMenu(menuId);
        if(nextMenu != null) {
        	novel.setAfterMenuId(nextMenu.getId());
        }
        GrabNovelMenu beforeMenu = titleService.queryBeforeMenu(menuId);
        if(beforeMenu != null) {
        	novel.setBeforeMenuId(beforeMenu.getId());
        }
        return novel;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
