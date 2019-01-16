package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the base_title database table.
 * 
 */
@Entity
@Table(name="base_title")
@NamedQuery(name="BaseTitle.findAll", query="SELECT b FROM BaseTitle b")
public class BaseTitle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private int apply;

	private String channel;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="parant_id")
	private Long parantId;

	private String tips;

	private String title;

	@Column(name="type_id")
	private Long typeId;

	@Column(name="star_tab")
	private Boolean starTab;

	public BaseTitle() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getApply() {
		return this.apply;
	}

	public void setApply(int apply) {
		this.apply = apply;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getDocId() {
		return this.docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getModiBy() {
		return this.modiBy;
	}

	public void setModiBy(Long modiBy) {
		this.modiBy = modiBy;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public Long getParantId() {
		return this.parantId;
	}

	public void setParantId(Long parantId) {
		this.parantId = parantId;
	}

	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Boolean getStarTab() {
		return starTab;
	}

	public void setStarTab(Boolean starTab) {
		this.starTab = starTab;
	}
}