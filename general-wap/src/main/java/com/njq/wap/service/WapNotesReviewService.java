package com.njq.wap.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.basis.service.impl.YxlNotesChunkService;
import com.njq.basis.service.impl.YxlNotesReviewService;
import com.njq.common.model.po.YxlNotesChunk;
import com.njq.common.model.po.YxlNotesReview;
import com.njq.common.model.vo.NovelDocVO;
import com.njq.common.model.vo.XtitleVO;

@Service
public class WapNotesReviewService {

	@Resource
	private YxlNotesReviewService yxlNotesReviewService;
	@Resource
	private YxlNotesChunkService yxlNotesChunkService;
	
	public NovelDocVO loadFirst(Long chunkId) {
		YxlNotesReview nv = yxlNotesReviewService.queryFirstReview(chunkId);
		NovelDocVO vo = new NovelDocVO();
		if(nv == null) {
			return vo;
		}
		this.fillBefore(vo, nv.getId(), chunkId);
		this.fillNext(vo, nv.getId(), chunkId);
		vo.setCreateDate(nv.getCreateDate());
		vo.setText(nv.getDoc());
		vo.setDocId(nv.getId());
		return vo;
	}
	
	public NovelDocVO loadDoc(Long id,String queryFlag) {
		YxlNotesReview review = yxlNotesReviewService.queryById(id);
		NovelDocVO vo = new NovelDocVO();
		if("1".equals(queryFlag)) {
			this.fillBefore(vo, id, review.getChunkId());
		}else {
			this.fillNext(vo, id, review.getChunkId());			
		}
		vo.setCreateDate(review.getCreateDate());
		vo.setText(review.getDoc());
		vo.setChunkId(review.getChunkId());
		vo.setTitle(review.getGeneral());
		return vo;
	}
	
	public void fillNext(NovelDocVO vo,Long id,Long chunkId) {
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
	
	public void fillBefore(NovelDocVO vo , Long id,Long chunkId) {
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
	
	public List<XtitleVO> loadAllChunk() {
		List<YxlNotesChunk> list =yxlNotesChunkService.queryAll();
		return list.stream().map(n->{
			XtitleVO vo = new XtitleVO();
			vo.setTitle(n.getName());
			vo.setDocId(n.getId());
			return vo;
		}).collect(Collectors.toList());
	}
}
