package com.njq.common.model.vo;

import java.io.Serializable;
import java.util.Date;

public class SysRuleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;

	private String columDesc;

	private Date createDate;

	private Integer isDefault;

	private String ruleName;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumDesc() {
		if(columDesc == null) {
			return "";
		}
		return columDesc;
	}

	public void setColumDesc(String columDesc) {
		this.columDesc = columDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
