package com.njq.wap.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.model.vo.NovelDocVO;
import com.njq.wap.service.WapNotesReviewService;

@RequestMapping("review")
@Controller
public class ReviewController {

	@Resource
	private WapNotesReviewService wapNotesReviewService;
	
	@RequestMapping(value = "/reviewInfo", method = RequestMethod.GET)
    public String reviewInfo(Model model,HttpServletRequest request,Long menuId) {
		model.addAttribute("chunkList", wapNotesReviewService.loadAllChunk());
        return "wap/notesReviewDoc";
    }
	
	@RequestMapping(value = "/getReviewDoc", method = RequestMethod.POST)
    @ResponseBody
    public NovelDocVO getReviewDoc(Model model,HttpServletRequest request,Long reviewId,Long chunkId,String queryFlag) {
		NovelDocVO vo = null; 
    	if(reviewId != null) {
    		vo = wapNotesReviewService.loadDoc(reviewId,queryFlag);
    		return vo;
    	}
    	if(chunkId != null) {
    		vo = wapNotesReviewService.loadFirst(chunkId);
    		return vo;
    	}
    	return vo;
    }
	
	
}
