package com.njq.wap.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.basis.service.impl.YxlNotesChunkService;
import com.njq.basis.service.impl.YxlNotesReviewService;
import com.njq.common.model.po.YxlNotesChunk;
import com.njq.common.model.po.YxlNotesReview;
import com.njq.common.model.vo.StudyNotesVO;

@Service
public class WapNotesReviewService {

	@Resource
	private YxlNotesReviewService yxlNotesReviewService;
	@Resource
	private YxlNotesChunkService yxlNotesChunkService;
	
	public StudyNotesVO loadFirst(Long chunkId) {
		StudyNotesVO vo = new StudyNotesVO();
		YxlNotesReview nv = yxlNotesReviewService.queryFirstReview(chunkId);
		if(nv == null) {
			vo.setChunkId(chunkId);
			return vo;
		}
		this.fillBefore(vo, nv.getId(), chunkId);
		this.fillNext(vo, nv.getId(), chunkId);
		vo.setCreateDate(nv.getCreateDate());
		vo.setText(nv.getDoc());
		vo.setTitle(nv.getGeneral());
		vo.setChunkId(nv.getChunkId());
		vo.setIndex(nv.getIndex1());
		return vo;
	}
	
	public StudyNotesVO loadDoc(Long id,String queryFlag) {
		YxlNotesReview review = yxlNotesReviewService.queryById(id);
		StudyNotesVO vo = new StudyNotesVO();
		if("1".equals(queryFlag)) {
			this.fillBefore(vo, id, review.getChunkId());
		}else {
			this.fillNext(vo, id, review.getChunkId());			
		}
		vo.setCreateDate(review.getCreateDate());
		vo.setText(review.getDoc());
		vo.setChunkId(review.getChunkId());
		vo.setTitle(review.getGeneral());
		vo.setIndex(review.getIndex1());
		return vo;
	}
	
	public void fillNext(StudyNotesVO vo,Long id,Long chunkId) {
		YxlNotesReview nextReview = yxlNotesReviewService.queryNextReview(id);
		if(nextReview == null) {
			YxlNotesReview nv = this.diGuiNext(chunkId);
			if(nv != null) {
				vo.setAfterMenuId(nv.getId());
			}
		}else {
			vo.setAfterMenuId(nextReview.getId());
		}
	}
	
	public YxlNotesReview diGuiNext(Long chunkId) {
		YxlNotesChunk nextChunk = yxlNotesChunkService.queryNextChunk(chunkId);
		if(nextChunk != null) {
			YxlNotesReview nv = yxlNotesReviewService.queryFirstReview(nextChunk.getId());
			if(nv == null) {
				return diGuiNext(chunkId);
			}
			return nv;
		}
		return null;
	}
	
	public void fillBefore(StudyNotesVO vo , Long id,Long chunkId) {
		YxlNotesReview beforeReview = yxlNotesReviewService.queryBeforeReview(id);
		if(beforeReview == null) {
			YxlNotesReview nv = diGuiBefore(chunkId);
			if(nv != null) {
				vo.setBeforeMenuId(nv.getId());
			}
		}else {
			vo.setBeforeMenuId(beforeReview.getId());
		}
	}
	
	public YxlNotesReview diGuiBefore(Long chunkId) {
		YxlNotesChunk beforeChunk = yxlNotesChunkService.queryBeforeChunk(chunkId);
		if(beforeChunk != null) {
			YxlNotesReview bv = yxlNotesReviewService.queryLastReview(beforeChunk.getId());
			if(bv == null) {
				return diGuiBefore(beforeChunk.getId());
			}
			return bv;
		}
		return null;
	}
	
	public List<StudyNotesVO> loadAllChunk() {
		List<YxlNotesChunk> list =yxlNotesChunkService.queryAll();
		return list.stream().map(n->{
			StudyNotesVO vo = new StudyNotesVO();
			vo.setTitle(n.getName());
			vo.setChunkId(n.getId());
			return vo;
		}).collect(Collectors.toList());
	}
}
