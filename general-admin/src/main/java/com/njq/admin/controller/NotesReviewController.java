package com.njq.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlNotesReviewService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlNotesReview;

@RequestMapping("admin/notes")
@Controller
public class NotesReviewController {

	@Resource
	private YxlNotesReviewService yxlNotesReviewService;
	
	@RequestMapping("reviewManage")
	public String page(Model model) {
		return "back/noteManage/reviewManage";
	}
	
	@RequestMapping(value = "reviewList", method = RequestMethod.POST)
	public @ResponseBody PageList<YxlNotesReview> reviewList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<YxlNotesReview> reviewList = yxlNotesReviewService.queryList(map, page, size);
		return reviewList;
	}
	
	@RequestMapping(value="dealReviews", method = RequestMethod.POST)
	public Map<String, Object> dealReviews(Long id,Long chunkId, String doc,Integer index,String general) {
		if(id == null) {
			yxlNotesReviewService.saveNotes(doc, index, chunkId, general);
		}else {
			yxlNotesReviewService.updateNotes(id, doc, index, chunkId, general);
		}
		return MessageCommon.getSuccessMap();
	}
	
	@RequestMapping(value="aupage", method = RequestMethod.GET)
	public String page(Model model,Long chunkId,Long id) {
		if(id != null) {
			model.addAttribute("review", yxlNotesReviewService.queryById(id));
		}
		if(chunkId != null) {
			model.addAttribute("chunkId", chunkId);
		}
		return "back/noteManage/editNotes";
	}
	
	
}
