package com.njq.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlNotesChunkService;
import com.njq.basis.service.impl.YxlNotesReviewService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlNotesChunk;
import com.njq.common.model.po.YxlNotesReview;
import com.njq.common.model.vo.NotesReviewVO;

@RequestMapping("admin/notes")
@Controller
public class NotesReviewController {

	@Resource
	private YxlNotesReviewService yxlNotesReviewService;
	@Resource
	private YxlNotesChunkService yxlNotesChunkService;
	
	@RequestMapping("reviewManage")
	public String page(Model model) {
		return "back/noteManage/reviewManage";
	}
	
	@RequestMapping(value = "reviewList", method = RequestMethod.POST)
	public @ResponseBody PageList<NotesReviewVO> reviewList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<YxlNotesReview> reviewList = yxlNotesReviewService.queryList(map, page, size);
		List<NotesReviewVO> list= reviewList.getList().stream().map(n->{
			NotesReviewVO vo = new NotesReviewVO();
			vo.setCreatedTime(n.getCreateDate());
			vo.setGeneral(n.getGeneral());
			vo.setId(n.getId());
			vo.setIndex(n.getIndex1());
			YxlNotesChunk chunk = yxlNotesChunkService.queryById(n.getChunkId());
			vo.setChunkName(chunk.getName());
			return vo;
		}).collect(Collectors.toList());
		PageList<NotesReviewVO> pg = new PageList<NotesReviewVO>();
		pg.setTotal(reviewList.getTotal());
		pg.setList(list);
		return pg;
	}
	
	@RequestMapping(value="dealReviews", method = RequestMethod.POST)
	@ResponseBody
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
			YxlNotesReview view = yxlNotesReviewService.queryById(id);
			model.addAttribute("review", view);
			model.addAttribute("chunkId", view.getChunkId());
			model.addAttribute("maxIndex", view.getIndex1());
		}else if(chunkId != null) {
			YxlNotesReview lview = yxlNotesReviewService.queryLastReview(chunkId);
			if(lview == null) {
				model.addAttribute("maxIndex", 1);
			}else {
				model.addAttribute("maxIndex", lview.getIndex1()+1);
			}
			model.addAttribute("chunkId", chunkId);
		}
		return "back/noteManage/editNotes";
	}
	
	
	@RequestMapping(value = "delReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delReview(@RequestParam(required = true) Long[] delIds) {
		if(delIds != null) {
			for(int i=0;i<delIds.length;i++) {
				yxlNotesReviewService.deleteNotes(delIds[i]);
			}
		}
		return MessageCommon.getSuccessMap();
	}
}
