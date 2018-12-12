package com.njq.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the yxl_column_define database table.
 * 
 */
@Entity
@Table(name="yxl_column_define")
@NamedQuery(name="YxlColumnDefine.findAll", query="SELECT y FROM YxlColumnDefine y")
public class YxlColumnDefine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="define_name")
	private String defineName;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="record_type")
	private Long recordType;

	@Column(name="store_name")
	private String storeName;

	// Constructors

	/** default constructor */
	public YxlColumnDefine() {
	}

	/** full constructor */
	public YxlColumnDefine(Timestamp createDate, Timestamp modiDate,
			String defineName, String storeName, Long recordType) {
		this.createDate = createDate;
		this.modiDate = modiDate;
		this.defineName = defineName;
		this.storeName = storeName;
		this.recordType = recordType;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public String getDefineName() {
		return this.defineName;
	}

	public void setDefineName(String defineName) {
		this.defineName = defineName;
	}

	public String getStoreName() {
		return this.storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Long recordType) {
		this.recordType = recordType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}