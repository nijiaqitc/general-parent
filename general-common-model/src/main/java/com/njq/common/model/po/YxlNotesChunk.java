package com.njq.common.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the yxl_notes_chunk database table.
 * 
 */
@Entity
@Table(name="yxl_notes_chunk")
@NamedQuery(name="YxlNotesChunk.findAll", query="SELECT y FROM YxlNotesChunk y")
public class YxlNotesChunk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private int index1;

	private int index2;

	@Column(name="modi_date")
	private Date modiDate;

	private String name;

	public YxlNotesChunk() {
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

	public int getIndex1() {
		return this.index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public int getIndex2() {
		return this.index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}

	public Date getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Date modiDate) {
		this.modiDate = modiDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}