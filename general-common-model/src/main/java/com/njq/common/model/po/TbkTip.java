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
 * The persistent class for the tbk_tip database table.
 * 
 */
@Entity
@Table(name="tbk_tip")
@NamedQuery(name="TbkTip.findAll", query="SELECT t FROM TbkTip t")
public class TbkTip implements Serializable {
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

	@Column(name="inTurn")
	private Long inTurn;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="status")
	private Integer status;

	// Constructors

    /** default constructor */
    public TbkTip() {
    }

    
    public TbkTip(Long id, String name, Long inTurn, Integer status,
			Long createBy, Date createDate) {
		super();
		this.id = id;
		this.name = name;
		this.inTurn = inTurn;
		this.status = status;
		this.createBy = createBy;
		this.createDate = createDate;
	}


	/** minimal constructor */
    public TbkTip(String name, Long inTurn, Timestamp modiDate) {
        this.name = name;
        this.inTurn = inTurn;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public TbkTip(String name, Long inTurn, Integer status, Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate, Date delDate) {
        this.name = name;
        this.inTurn = inTurn;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getInTurn() {
        return this.inTurn;
    }

    public void setInTurn(Long inTurn) {
        this.inTurn = inTurn;
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