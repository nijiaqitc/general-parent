package com.njq.common.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.njq.common.model.po.TbkRecommendDocView;

public class TbkTypeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String columDesc;

	private Long createBy;

	private Date createDate;

	private Date delDate;

	private Integer inTurn;

	private Long modiBy;

	private Timestamp modiDate;

	private String name;

	private Long parentId;

	private Integer status;

	private List<TbkRecommendDocView> textList;
	
	private String parentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumDesc() {
		return columDesc;
	}

	public void setColumDesc(String columDesc) {
		this.columDesc = columDesc;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public Integer getInTurn() {
		return inTurn;
	}

	public void setInTurn(Integer inTurn) {
		this.inTurn = inTurn;
	}

	public Long getModiBy() {
		return modiBy;
	}

	public void setModiBy(Long modiBy) {
		this.modiBy = modiBy;
	}

	public Timestamp getModiDate() {
		return modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<TbkRecommendDocView> getTextList() {
		return textList;
	}

	public void setTextList(List<TbkRecommendDocView> textList) {
		this.textList = textList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
