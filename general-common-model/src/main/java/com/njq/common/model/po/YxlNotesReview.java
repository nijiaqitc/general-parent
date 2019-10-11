package com.njq.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the yxl_notes_review database table.
 * 
 */
@Entity
@Table(name="yxl_notes_review")
@NamedQuery(name="YxlNotesReview.findAll", query="SELECT y FROM YxlNotesReview y")
public class YxlNotesReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Lob
	private String doc;

	private int index1;

	@Column(name="modi_date")
	private Timestamp modiDate;

	private Long chunkId;
	
	@Column(name="general")
	private String general;
	
	public YxlNotesReview() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDoc() {
		return this.doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public Long getChunkId() {
		return chunkId;
	}

	public void setChunkId(Long chunkId) {
		this.chunkId = chunkId;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	
}