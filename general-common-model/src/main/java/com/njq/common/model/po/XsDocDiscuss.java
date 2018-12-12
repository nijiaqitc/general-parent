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
 * The persistent class for the xs_doc_discuss database table.
 * 
 */
@Entity
@Table(name="xs_doc_discuss")
@NamedQuery(name="XsDocDiscuss.findAll", query="SELECT x FROM XsDocDiscuss x")
public class XsDocDiscuss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="discuss")
	private String discuss;

	@Column(name="doc_id")
	private Long docId;

	@Column(name="is_show")
	private String isShow;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="user_ip")
	private String userIp;

	// Constructors

    /** default constructor */
    public XsDocDiscuss() {
    }

    /** full constructor */
    public XsDocDiscuss(String userIp, Long docId, String discuss, String isShow, Timestamp createDate, Timestamp modiDate) {
        this.userIp = userIp;
        this.docId = docId;
        this.discuss = discuss;
        this.isShow = isShow;
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

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Long getDocId() {
        return this.docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDiscuss() {
        return this.discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
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