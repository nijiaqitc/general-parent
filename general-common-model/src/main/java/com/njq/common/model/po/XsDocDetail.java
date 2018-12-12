package com.njq.common.model.po;


import com.njq.common.util.date.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;



/**
 * The persistent class for the xs_doc_detail database table.
 * 
 */
@Entity
@Table(name="xs_doc_detail")
@NamedQuery(name="XsDocDetail.findAll", query="SELECT x FROM XsDocDetail x")
public class XsDocDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="doc")
	private String doc;

	@Column(name="finish_status")
	private String finishStatus;

	@Column(name="font_num")
	private Integer fontNum;

	@Column(name="in_turn")
	private Integer inTurn;

	@Column(name="is_show")
	private String isShow;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="thc_id")
	private Long thcId;

	@Column(name="title")
	private String title;

	@Column(name="user_id")
	private Long userId;

	// Constructors

    /** default constructor */
    public XsDocDetail() {
    }

    /** full constructor */
    public XsDocDetail(Long thcId, String title, String doc, Long userId, Integer inTurn, String isShow, Timestamp createDate, Timestamp modiDate) {
        this.thcId = thcId;
        this.title = title;
        this.doc = doc;
        this.userId = userId;
        this.inTurn = inTurn;
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

    public Long getThcId() {
        return this.thcId;
    }

    public void setThcId(Long thcId) {
        this.thcId = thcId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoc() {
        return this.doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getFormatCreateDate(){
    	return DateUtil.toDateString1(this.createDate);
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

	public Integer getInTurn() {
		return inTurn;
	}

	public void setInTurn(Integer inTurn) {
		this.inTurn = inTurn;
	}

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public Integer getFontNum() {
        return fontNum;
    }

    public void setFontNum(Integer fontNum) {
        this.fontNum = fontNum;
    }

}