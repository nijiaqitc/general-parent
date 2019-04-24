package com.njq.common.model.vo;

import java.io.Serializable;

public class YxlStudyTitleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;

	private Long parentId;

	private String title;

	private String titleType;

	private Long typeId;

	private String typeName;
	
	private Boolean sure;
	
	private String general;
	
	private String options;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getSure() {
		return sure;
	}

	public void setSure(Boolean sure) {
		this.sure = sure;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	
}
