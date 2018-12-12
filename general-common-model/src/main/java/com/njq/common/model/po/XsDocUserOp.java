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
 * The persistent class for the xs_doc_user_op database table.
 * 
 */
@Entity
@Table(name="xs_doc_user_op")
@NamedQuery(name="XsDocUserOp.findAll", query="SELECT x FROM XsDocUserOp x")
public class XsDocUserOp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="app_id")
	private String appId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="op")
	private String op;

	@Column(name="title_id")
	private Long titleId;

	@Column(name="user_id")
	private Long userId;

	@Column(name="user_ip")
	private String userIp;

	// Constructors

    /** default constructor */
    public XsDocUserOp() {
    }

    /** full constructor */
    public XsDocUserOp(Long userId, String userIp, String op, Long titleId, Long docId, Timestamp createDate, Timestamp modiDate) {
        this.userId = userId;
        this.userIp = userIp;
        this.op = op;
        this.titleId = titleId;
        this.docId = docId;
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

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getOp() {
        return this.op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Long getTitleId() {
        return this.titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Long getDocId() {
        return this.docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}