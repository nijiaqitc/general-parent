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
 * The persistent class for the tbk_type_pic_config database table.
 * 
 */
@Entity
@Table(name="tbk_type_pic_config")
@NamedQuery(name="TbkTypePicConfig.findAll", query="SELECT t FROM TbkTypePicConfig t")
public class TbkTypePicConfig implements Serializable {
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

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="pic_id")
	private Long picId;

	@Column(name="status")
	private Integer status;

	@Column(name="type_id")
	private Long typeId;

	// Constructors

    /** default constructor */
    public TbkTypePicConfig() {
    }

    /** minimal constructor */
    public TbkTypePicConfig(Long typeId, Long picId, Integer status, Long createBy, Timestamp createDate, Timestamp modiDate) {
        this.typeId = typeId;
        this.picId = picId;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public TbkTypePicConfig(Long typeId, Long picId, Integer status, Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate,
            Date delDate) {
        this.typeId = typeId;
        this.picId = picId;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modiBy = modiBy;
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

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getPicId() {
        return this.picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
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

}