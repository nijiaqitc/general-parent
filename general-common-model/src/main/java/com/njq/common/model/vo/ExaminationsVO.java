package com.njq.common.model.vo;

import java.io.Serializable;
import java.util.List;

public class ExaminationsVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long parentId;

	private String title;

	private String titleType;

	private Long typeId;

	private List<String[]> optionsub;
	
	private List<AnswerVO> answerList;
	
	private Boolean isNeedStudy;

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

	public List<String[]> getOptionsub() {
		return optionsub;
	}

	public void setOptionsub(List<String[]> optionsub) {
		this.optionsub = optionsub;
	}

	public List<AnswerVO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<AnswerVO> answerList) {
		this.answerList = answerList;
	}

	public Boolean getIsNeedStudy() {
		return isNeedStudy;
	}

	public void setIsNeedStudy(Boolean isNeedStudy) {
		this.isNeedStudy = isNeedStudy;
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
