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
 * The persistent class for the tbk_doc_tip_config database table.
 * 
 */
@Entity
@Table(name="tbk_doc_tip_config")
@NamedQuery(name="TbkDocTipConfig.findAll", query="SELECT t FROM TbkDocTipConfig t")
public class TbkDocTipConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="in_turn")
	private Long inTurn;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="status")
	private Integer status;

	@Column(name="tip_id")
	private Long tipId;

	// Constructors

    /** default constructor */
    public TbkDocTipConfig() {
    }

    /** minimal constructor */
    public TbkDocTipConfig(Long docId, Long tipId, Integer status, Long createBy, Timestamp createDate, Timestamp modiDate) {
        this.docId = docId;
        this.tipId = tipId;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public TbkDocTipConfig(Long docId, Long tipId, Integer status, Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate,
            Date delDate, Long inTurn) {
        this.docId = docId;
        this.tipId = tipId;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modiBy = modiBy;
        this.modiDate = modiDate;
        this.delDate = delDate;
        this.inTurn = inTurn;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getDelDate() {
        return this.delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public Long getInTurn() {
        return this.inTurn;
    }

    public void setInTurn(Long inTurn) {
        this.inTurn = inTurn;
    }

}