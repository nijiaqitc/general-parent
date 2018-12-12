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
 * The persistent class for the yxl_doc_tip_config database table.
 * 
 */
@Entity
@Table(name="yxl_doc_tip_config")
@NamedQuery(name="YxlDocTipConfig.findAll", query="SELECT y FROM YxlDocTipConfig y")
public class YxlDocTipConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="tip_id")
	private Long tipId;

	// Constructors

    /** default constructor */
    public YxlDocTipConfig() {
    }

    /** full constructor */
    public YxlDocTipConfig(Long docId, Long tipId, Date createDate, Timestamp modiDate) {
        this.docId = docId;
        this.tipId = tipId;
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

    public Long getTipId() {
        return this.tipId;
    }

    public void setTipId(Long tipId) {
        this.tipId = tipId;
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

}