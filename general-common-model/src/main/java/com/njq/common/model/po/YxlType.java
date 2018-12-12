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
 * The persistent class for the yxl_type database table.
 * 
 */
@Entity
@Table(name="yxl_type")
@NamedQuery(name="YxlType.findAll", query="SELECT y FROM YxlType y")
public class YxlType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="colum_desc")
	private String columDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="status")
	private Integer status;

	// Constructors

    /** default constructor */
    public YxlType() {
    }

    /** minimal constructor */
    public YxlType(String name, Timestamp modiDate) {
        this.name = name;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public YxlType(String name, String columDesc, Integer status, Date createDate, Timestamp modiDate, Date delDate) {
        this.name = name;
        this.columDesc = columDesc;
        this.status = status;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumDesc() {
        return this.columDesc;
    }

    public void setColumDesc(String columDesc) {
        this.columDesc = columDesc;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}