package com.njq.yxl.controller;

import com.njq.common.base.interceptor.NeedPwd;
import com.njq.common.base.other.TokenCheck;
import com.njq.common.model.po.BaseUser;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlFolder;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.po.YxlType;
import com.njq.common.util.encrypt.Md5Util;
import com.njq.common.util.other.CookieUtil;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;
import com.njq.yxl.service.YxlNoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2018/12/28
 */
@Controller
public class PersonalController {
    @Resource
    private YxlDocService yxlDocService;
    @Resource
    private YxlDocSearchService yxlDocSearchService;
    @Resource
    public YxlNoteService yxlNoteService;

    /**
     * 跳转到发表yxl文章页面
     *
     * @param model
     * @return
     */
    @NeedPwd
    @RequestMapping(value = "yxlIssueDoc", method = RequestMethod.GET)
    public String yxlIssueDoc(Model model) {
        List<YxlType> list = yxlDocService.queryTypeTitleList();
        model.addAttribute("list", list);
        return "back/issueDoc/yxlBackIssueDoc";
    }

    /**
     * 修改yxl文章进行跳转
     *
     * @param model
     * @param docId
     * @return
     */
    @NeedPwd
    @RequestMapping(value = "updateYxlDocPage", method = RequestMethod.GET)
    public String updateYxlDocPage(Model model, Long docId) {
        List<YxlType> list = yxlDocService.queryTypeTitleList();
        model.addAttribute("list", list);
        if (docId == null) {
            return "back/issueDoc/yxlBackIssueDoc";
        }
        YxlDocSearch search = yxlDocSearchService.queryByDocId(docId);
        if (search == null) {
            return "back/issueDoc/yxlBackIssueDoc";
        }
        if (search.getTypeId() != null) {
            YxlType type = yxlDocService.queryTypeById(search.getTypeId());
            model.addAttribute("type", type);
        }
        YxlDoc doc = yxlDocService.queryById(docId);
        List<YxlTip> tipList = yxlDocService.queryDocTipList(docId);
        String tipName = "";
        for (YxlTip t : tipList) {
            tipName += t.getTipName() + ",";
        }
        model.addAttribute("doc", doc);
        model.addAttribute("tipName", tipName);
        model.addAttribute("isUpdate", true);
        model.addAttribute("search", search);
        return "back/issueDoc/yxlBackIssueDoc";
    }

    /**
     * 进行笔记管理后门
     *
     * @param token
     * @param request
     * @param model
     * @return
     */
    @NeedPwd
    @RequestMapping(value = "noteManager", method = RequestMethod.GET)
    public String noteManager(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession();
    	session.setAttribute("sessionId", request.getSession().getId());
    	BaseUser user = new BaseUser();
    	user.setAccount("admin");
    	user.setId(2L);
    	session.setAttribute("user", user);
    	List<YxlFolder> folderList = yxlNoteService.queryFolderList(1);
    	model.addAttribute("fdList", folderList);
    	return "zxgj/noteManager";
    }

    @NeedPwd
    @RequestMapping(value = "recordManager", method = RequestMethod.GET)
    public String recordManager(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession();
    	session.setAttribute("sessionId", request.getSession().getId());
    	BaseUser user = new BaseUser();
    	user.setAccount("admin");
    	user.setId(2L);
    	session.setAttribute("user", user);
    	List<YxlFolder> folderList = yxlNoteService.queryFolderList(2);
    	model.addAttribute("fdList", folderList);
    	return "zxgj/recordManager";
    }

    @ResponseBody
    @RequestMapping(value = "mdValue", method = RequestMethod.GET)
    public String mdValue(@RequestParam String value){
        return Md5Util.getMD5Password(value);
    }

    @RequestMapping(value = "pwdPage", method = RequestMethod.GET)
    public String pwdPage(){
    	CookieUtil.removeCookieByName("loginFlag");
        return "zxgj/pwdPage";
    }

    @RequestMapping(value = "setPwd", method = RequestMethod.POST)
    public String setPwd(@RequestParam String token ,@RequestParam String jumpurl){
        if(!TokenCheck.checkToken(token)){
            return "请设置正确的密码！";
        }
        CookieUtil.addCookie("loginFlag", "true");
        return "redirect:"+jumpurl;
    }



}
