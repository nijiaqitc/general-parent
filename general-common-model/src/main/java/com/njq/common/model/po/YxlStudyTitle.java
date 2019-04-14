package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the yxl_study_title database table.
 * 
 */
@Entity
@Table(name="yxl_study_title")
@NamedQuery(name="YxlStudyTitle.findAll", query="SELECT y FROM YxlStudyTitle y")
public class YxlStudyTitle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="type_id")
	private Long typeId;
	
	@Column(name="is_need_study")
	private Boolean isNeedStudy;

	@Column(name="parent_id")
	private Long parentId;

	private String title;

	@Column(name="title_type")
	private String titleType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="modi_date")
	private Timestamp modiDate;
	
	public YxlStudyTitle() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Boolean getIsNeedStudy() {
		return isNeedStudy;
	}

	public void setIsNeedStudy(Boolean isNeedStudy) {
		this.isNeedStudy = isNeedStudy;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModiDate() {
		return modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

}