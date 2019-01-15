package com.njq.common.model.vo.grab;

import java.io.Serializable;

public class GrabTitleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long parantId;	
	
	private String tips;

	private String title;
	
	private Long typeId;
	
	private int childrenCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParantId() {
		return parantId;
	}

	public void setParantId(Long parantId) {
		this.parantId = parantId;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}
	
}
