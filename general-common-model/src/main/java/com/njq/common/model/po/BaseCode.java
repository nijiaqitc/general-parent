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
 * The persistent class for the base_code database table.
 * 
 */
@Entity
@Table(name="base_code")
@NamedQuery(name="BaseCode.findAll", query="SELECT b FROM BaseCode b")
public class BaseCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="colum_desc")
	private String columDesc;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="in_turn")
	private Integer inTurn;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="status")
	private Integer status;

	@Column(name="type")
	private String type;

	@Column(name="value")
	private String value;

	// Constructors

    /** default constructor */
    public BaseCode() {
    }

    /** minimal constructor */
    public BaseCode(Timestamp modiDate) {
        this.modiDate = modiDate;
    }

    /** full constructor */
    public BaseCode(Long parentId, String type, String name, String value, Integer inTurn, String columDesc, Integer status, Long createBy,
            Timestamp createDate, Long modiBy, Timestamp modiDate, Date delDate) {
        this.parentId = parentId;
        this.type = type;
        this.name = name;
        this.value = value;
        this.inTurn = inTurn;
        this.columDesc = columDesc;
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

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getInTurn() {
        return this.inTurn;
    }

    public void setInTurn(Integer inTurn) {
        this.inTurn = inTurn;
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