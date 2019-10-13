package com.njq.common.model.vo;

import java.io.Serializable;

public class StudyNotesVO extends NovelDocVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long chunkId;
	private Integer index;
	public Long getChunkId() {
		return chunkId;
	}
	public void setChunkId(Long chunkId) {
		this.chunkId = chunkId;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
