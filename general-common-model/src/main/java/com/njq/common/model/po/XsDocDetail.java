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
 * The persistent class for the xs_doc_detail database table.
 * 
 */
@Entity
@Table(name="xs_doc_detail")
@NamedQuery(name="XsDocDetail.findAll", query="SELECT x FROM XsDocDetail x")
public class XsDocDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="doc")
	private String doc;

	@Column(name="font_num")
	private Integer fontNum;

	@Column(name="title")
	private String title;

	@Column(name="user_id")
	private Long userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="modi_date")
	private Timestamp modiDate;
	
	// Constructors

    /** default constructor */
    public XsDocDetail() {
    }

	public XsDocDetail(Long id, String doc, Integer fontNum, String title, Long userId, Date createDate,
			Timestamp modiDate) {
		super();
		this.id = id;
		this.doc = doc;
		this.fontNum = fontNum;
		this.title = title;
		this.userId = userId;
		this.createDate = createDate;
		this.modiDate = modiDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public Integer getFontNum() {
		return fontNum;
	}

	public void setFontNum(Integer fontNum) {
		this.fontNum = fontNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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