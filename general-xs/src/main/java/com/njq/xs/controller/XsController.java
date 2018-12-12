package com.njq.xs.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.xs.service.XsDocDetailService;
import com.njq.xs.service.XsTitleDetailService;

@Controller
@RequestMapping("xl")
public class XsController {

	@Resource
    private XsTitleDetailService xsTitleDetailService;
    @Resource
    public XsTitleDetailService titleService;
    @Resource
    private XsDocDetailService xsDocDetailService;
    
    @RequestMapping(value="novelList",method=RequestMethod.GET)
    public String novelList(Model model){
    	XsTitleDetail docInfo=titleService.queryNameById(1l);
//    	Map<String, Object> m=titleService.queryMaxNum(docId);
        List<XsTitleDetail> list=titleService.queryAllTitleListByDocId(1l);
//        XsDocGeneralInfo info=docGeneralInfoService.queryByTitleId(docId);
        model.addAttribute("list", list);
//        model.addAttribute("info", info);
        model.addAttribute("docInfo", docInfo);
//        model.addAttribute("titleIndex", m.get("titleIndex"));
//        model.addAttribute("orderIndex", m.get("orderIndex"));
        return "zxgj/novelList";
    }
    
    
    /**
     * 小说文章阅览
     * @return
     */
    @RequestMapping(value="novelRead/{docId}",method=RequestMethod.GET)
    public String novelList(Model model,@PathVariable(value="docId") Long docId){
    	XsDocDetail detail=xsDocDetailService.queryByTitleId(docId);
    	if(detail!=null){
    		Map<String, Object> m=xsTitleDetailService.queryBeforeAndNextNo(detail.getThcId());
    		model.addAttribute("pn", m);
    		model.addAttribute("doc", detail);
    		return "zxgj/novelRead";    		
    	}else{
    		return "aaaa";	
    	}
    }
}
