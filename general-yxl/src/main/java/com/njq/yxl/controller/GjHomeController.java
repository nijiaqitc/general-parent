package com.njq.yxl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseBanner;
import com.njq.common.model.po.BaseUser;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlFolder;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.DocVO;
import com.njq.common.util.other.PropertyUtil;
import com.njq.yxl.service.YxlDocSearchService;
import com.njq.yxl.service.YxlDocService;
import com.njq.yxl.service.YxlNoteService;

@Controller
public class GjHomeController {

    @Resource
    private YxlDocSearchService yxlDocSearchService;
    @Resource
    private YxlDocService yxlDocService;
    /**
     * 跳转到首页
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value="",method=RequestMethod.GET)
    public String toIndex(Model model,String ismob){
//    	List<YxlDocSearch> xlList=yxlDocSearchService.queryTitleYxlList(true);
//    	List<DocVO> docList=yxlDocSearchService.queryTitlelist();
//        
//        
//        model.addAttribute("doclist",docList);
//        model.addAttribute("xlDoclist",xlList);
//        model.addAttribute("ismob", ismob);
        return "zxgj/index";
    }
    
    /**
     * 获取banner图片
     * @return
     */
    @RequestMapping(value="getShowBannerList",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShowBannerList(){
        Map<String, Object> m=new HashMap<String, Object>();
        //todo 从redis里面拿
        List<BaseBanner> bannerList=null;
        for(BaseBanner b:bannerList){
            b.setPicPlace(b.getPicPlace());
        }
        m.put("bannerList",bannerList);
        MessageCommon.getSuccessMap(m);
        return m;
    }
    
    
    /**
     * 跳转到关于我们
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value="aboutUs",method=RequestMethod.GET)
    public String aboutUs(Model model,String ismob){
        model.addAttribute("ismob", ismob);
        return "zxgj/aboutMe";
    }
    
    
    /**
     * 跳转到富文本编辑器下载区域
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value="editor",method=RequestMethod.GET)
    public String editor(Model model,String ismob,String modelStyle){
        model.addAttribute("ismob", ismob);
        model.addAttribute("modelStyle", modelStyle);
        return "zxgj/editor";
    }
    
    /**
     * 跳转到在线工具区域
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value="tools",method=RequestMethod.GET)
    public String tools(Model model,String ismob){
        model.addAttribute("ismob", ismob);
        return "zxgj/tools";
    }
    
    /**
     * 跳转到文章列表
     * @param model
     * @param ismob
     * @return
     */
    @RequestMapping(value="docList",method=RequestMethod.GET)
    public String docList(Model model,String ismob,String tipName){
        List<YxlTip> tipList=yxlDocService.queryTipList();
        try {
            model.addAttribute("tipName",tipName);
            /*if(StringUtil.IsNotEmpty(tipName)){
                model.addAttribute("tipName", new String(tipName.getBytes("iso8859-1"),"utf-8"));                
            }*/
            model.addAttribute("tipList",tipList);
            model.addAttribute("ismob", ismob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "zxgj/docList";
    }
    
    
    @RequestMapping(value="knowledgeDoc",method=RequestMethod.GET)
    public String knowledgeDoc(Model model,String docId){
        model.addAttribute("ismob", docId);
        return "zxgj/knowledgeDoc";
    }
    
    /**
     * 引用顶部公共部分
     * @return
     */
    @RequestMapping(value="top",method=RequestMethod.GET)
    public String top(){
        return "zxgj/top";
    }
    
    /**
     * 引用底部公共部分
     * @return
     */
    @RequestMapping(value="bottom",method=RequestMethod.GET)
    public String bottom(){
        return "zxgj/bottom";
    }
    
    
    @Resource
    public YxlNoteService yxlNoteService;
    
    
    /**
     * 跳转到发表yxl文章页面
     * @param model
     * @return
     */
    @RequestMapping(value="yxlIssueDoc",method=RequestMethod.GET)
    public String yxlIssueDoc(Model model,
            @RequestParam(required=true) String token){
        if(!"njq".equals(token)){
            return "aaa";
        }
        List<YxlType> list=yxlDocService.queryTypeTitleList();
        model.addAttribute("list", list);
        return "back/issueDoc/yxlBackIssueDoc";
    }
    
    /**
     * 修改yxl文章进行跳转
     * @param model
     * @param docId
     * @return
     */
    @RequestMapping(value="updateYxlDocPage",method=RequestMethod.GET)
    public String updateYxlDocPage(Model model,Long docId,
            @RequestParam(required=true) String token){
        if(!"njq".equals(token)){
            return "aaa";
        }
        List<YxlType> list=yxlDocService.queryTypeTitleList();
        model.addAttribute("list", list);
        if(docId==null){
            return "back/issueDoc/yxlBackIssueDoc";
        }
        YxlDocSearch search=yxlDocSearchService.queryByDocId(docId);
        if(search==null){
            return "back/issueDoc/yxlBackIssueDoc";
        }
        if(search.getTypeId()!=null){
            YxlType type=yxlDocService.queryTypeById(search.getTypeId());
            model.addAttribute("type", type);
        }
        YxlDoc doc=yxlDocService.queryById(docId);
        List<YxlTip> tipList=yxlDocService.queryDocTipList(docId);
        String tipName="";
        for(YxlTip t:tipList){
            tipName+=t.getTipName()+",";
        }
        model.addAttribute("doc", doc);
        model.addAttribute("tipName", tipName);
        model.addAttribute("isUpdate", true);
        model.addAttribute("search",search);
        return "back/issueDoc/yxlBackIssueDoc";
    }
    
    /**
     * 进行笔记管理后门
     * @param token
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="noteManager",method=RequestMethod.GET)
    public String noteManager(String token,HttpServletRequest request,Model model){
        if("njqnote".equals(token)){
            HttpSession session=request.getSession();
            session.setAttribute("sessionId", request.getSession().getId());
            BaseUser user=new BaseUser();
            user.setAccount("admin");
            user.setId(2l);
            session.setAttribute("user",user);
            List<YxlFolder> folderList=yxlNoteService.queryFolderList(1);
            model.addAttribute("fdList", folderList);
            return "zxgj/noteManager";            
        }else{
            return "xxbbss";
        }
    }
    
    @RequestMapping(value="recordManager",method=RequestMethod.GET)
    public String recordManager(String token,HttpServletRequest request,Model model){
        if("njqrecord".equals(token)){
            HttpSession session=request.getSession();
            session.setAttribute("sessionId", request.getSession().getId());
            BaseUser user=new BaseUser();
            user.setAccount("admin");
            user.setId(2l);
            session.setAttribute("user",user);
            List<YxlFolder> folderList=yxlNoteService.queryFolderList(2);
            model.addAttribute("fdList", folderList);
            return "zxgj/recordManager";            
        }else{
            return "xxbbss";
        }
    }
    
    
    
    /**
     * 跳转到文档
     * @return
     */
    @RequestMapping(value="editDoc",method=RequestMethod.GET)
    public String editDoc(Model model){
    	List<Map<String, Object>> listType=yxlDocSearchService.queryListType(71);
    	List<Map<String, Object>> listTitle=yxlDocSearchService.queryListTitle();
    	model.addAttribute("titleList", listType);
    	model.addAttribute("nameList", listTitle);
    	System.out.println(listType);
        return "zxgj/editDoc";
    }
    
    /**
     * 下载文件
     * @param request
     * @return
     */
    @SuppressWarnings("resource")
    @RequestMapping(value="downLoadEditor",method=RequestMethod.GET)
    public  ResponseEntity<byte[]> downLoadEditor(HttpServletRequest request){
        try {
            File file = new File(PropertyUtil.get("downLoadFile"));
            byte[] body = null;
            InputStream is = new FileInputStream(file);
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + file.getName());
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
            return entity;            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
