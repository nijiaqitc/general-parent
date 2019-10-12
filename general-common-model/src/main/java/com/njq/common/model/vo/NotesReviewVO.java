package com.njq.common.model.vo;

import java.io.Serializable;
import java.util.Date;

public class NotesReviewVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String general;
	private String chunkName;
	private Date createdTime;
	private Integer index;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGeneral() {
		if(general == null) {
			return "";
		}
		return general;
	}
	public void setGeneral(String general) {
		this.general = general;
	}
	public String getChunkName() {
		return chunkName;
	}
	public void setChunkName(String chunkName) {
		this.chunkName = chunkName;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getIndex() {
		if(index == null) {
			return 0;
		}
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
}
