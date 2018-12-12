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
 * The persistent class for the yxl_note database table.
 * 
 */
@Entity
@Table(name="yxl_note")
@NamedQuery(name="YxlNote.findAll", query="SELECT y FROM YxlNote y")
public class YxlNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Lob
	@Column(name="css")
	private String css;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="status")
	private Integer status;

	@Lob
	@Column(name="text")
	private String text;

	@Column(name="title")
	private String title;

	@Column(name="user_id")
	private Long userId;

	// Constructors

	/** default constructor */
	public YxlNote() {
	}

	/** minimal constructor */
	public YxlNote(String title) {
		this.title = title;
	}

	/** full constructor */
	public YxlNote(String title, String text, String css, Integer status,
			Long userId, Timestamp createDate, Timestamp modiDate, Date delDate) {
		this.title = title;
		this.text = text;
		this.css = css;
		this.status = status;
		this.userId = userId;
		this.createDate = createDate;
		this.modiDate = modiDate;
		this.delDate = delDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCss() {
		return this.css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public Date getDelDate() {
		return this.delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

}