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
 * The persistent class for the yxl_note_general database table.
 * 
 */
@Entity
@Table(name="yxl_note_general")
@NamedQuery(name="YxlNoteGeneral.findAll", query="SELECT y FROM YxlNoteGeneral y")
public class YxlNoteGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="folder_id")
	private Long folderId;

	@Column(name="general")
	private String general;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="status")
	private Integer status;

	@Column(name="title")
	private String title;

	@Column(name="user_id")
	private Long userId;

	// Constructors

	/** default constructor */
	public YxlNoteGeneral() {
	}

	/** full constructor */
	public YxlNoteGeneral(Long docId, String title, String general,
			Long userId, Timestamp createDate, Timestamp modiDate) {
		this.docId = docId;
		this.title = title;
		this.general = general;
		this.userId = userId;
		this.createDate = createDate;
		this.modiDate = modiDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDocId() {
		return this.docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGeneral() {
		return this.general;
	}

	public void setGeneral(String general) {
		this.general = general;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

}