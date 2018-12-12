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

import com.njq.common.util.date.DateUtil;



/**
 * The persistent class for the base_log database table.
 * 
 */
@Entity
@Table(name="base_log")
@NamedQuery(name="BaseLog.findAll", query="SELECT b FROM BaseLog b")
public class BaseLog implements Serializable {
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

	@Column(name="oper_con")
	private String operCon;

	@Column(name="oper_table")
	private String operTable;

	@Column(name="status")
	private Integer status;

	@Column(name="type")
	private String type;

	@Column(name="user_id")
	private Long userId;

	// Constructors

    /** default constructor */
    public BaseLog() {
    }

    /** minimal constructor */
    public BaseLog(String type, String operTable, Timestamp modiDate) {
        this.type = type;
        this.operTable = operTable;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public BaseLog(Long userId, String type, String operTable, String operCon, Integer status, Long createBy, Timestamp createDate, Long modiBy,
            Timestamp modiDate, Date delDate) {
        this.userId = userId;
        this.type = type;
        this.operTable = operTable;
        this.operCon = operCon;
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

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperTable() {
        return this.operTable;
    }

    public void setOperTable(String operTable) {
        this.operTable = operTable;
    }

    public String getOperCon() {
        return this.operCon;
    }

    public void setOperCon(String operCon) {
        this.operCon = operCon;
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

    public String getFormatDate(){
        return DateUtil.toDateString4(this.createDate);
    }

}