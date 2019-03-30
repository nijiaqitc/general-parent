package com.njq.xs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.model.vo.TitlethcVO;
import com.njq.common.model.vo.XsListVO;
import com.njq.xs.service.XsDocDetailService;
import com.njq.xs.service.XsDocDiscussService;
import com.njq.xs.service.XsDocGeneralInfoService;
import com.njq.xs.service.XsTitleDetailService;


@Controller
@RequestMapping("xs")
public class XsController {

	@Resource
    private XsTitleDetailService xsTitleDetailService;
    @Resource
    public XsTitleDetailService titleService;
    @Resource
    private XsDocDetailService xsDocDetailService;
    @Resource
    private XsDocDiscussService xsDocDiscussService;
    @Resource
    private XsDocGeneralInfoService xsDocGeneralInfoService;
    
    @RequestMapping(value="",method=RequestMethod.GET)
    public String index(Model model){
    	List<TitlethcVO> docList = xsTitleDetailService.queryDocList(0L);
    	model.addAttribute("docList", docList);	
    	return "xs/novelIndex";
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="novelList",method=RequestMethod.GET)
    public String novelList(Model model,Long titleId){
    	if(titleId==null) {
    		return "grab/noDoc";
    	}
    	XsTitleDetail docInfo=titleService.queryById(titleId);
    	model.addAttribute("docInfo", docInfo);
		Map<String, Object> paramMap = new HashMap();
        paramMap.put("docId", titleId);
        model.addAttribute("discussCount", xsDocDiscussService.queryCount(paramMap));
        XsDocGeneralInfo info = xsDocGeneralInfoService.queryByTitleId(docInfo.getId());
        model.addAttribute("generalInfo", info);
        List<XsListVO> xsList =  xsTitleDetailService.queryAllTitleGroupJuan(titleId);
        model.addAttribute("xsList", xsList);
        return "xs/novelList";
    }
    
    
    /**
     * 小说文章阅览
     * @return
     */
    @RequestMapping(value="novelRead/{docId}",method=RequestMethod.GET)
    public String novelRead(Model model,@PathVariable(value="titleId") Long titleId){
    	XsTitleDetail title = xsTitleDetailService.queryById(titleId);
    	if(title.getDocId()!=null) {
    		XsDocDetail detail = xsDocDetailService.queryById(title.getDocId());
    		Map<String, Object> m=xsTitleDetailService.queryBeforeAndNextNo(title.getId());
    		model.addAttribute("pn", m);
    		model.addAttribute("doc", detail);
    		return "xs/novelRead";
    	}else {
    		return "aaaa";
    	}
    }
}
