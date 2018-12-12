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
 * The persistent class for the xs_doc_general_info database table.
 * 
 */
@Entity
@Table(name="xs_doc_general_info")
@NamedQuery(name="XsDocGeneralInfo.findAll", query="SELECT x FROM XsDocGeneralInfo x")
public class XsDocGeneralInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="font_num")
	private Integer fontNum;

	@Column(name="good_num")
	private Integer goodNum;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="title_id")
	private Long titleId;

	@Column(name="view_num")
	private Integer viewNum;

	// Constructors

    /** default constructor */
    public XsDocGeneralInfo() {
    }

    /** full constructor */
    public XsDocGeneralInfo(Long titleId, Integer viewNum, Integer goodNum, Timestamp createDate, Timestamp modiDate) {
        this.titleId = titleId;
        this.viewNum = viewNum;
        this.goodNum = goodNum;
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

    public Long getTitleId() {
        return this.titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Integer getViewNum() {
        return this.viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
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

    public Integer getFontNum() {
        return fontNum;
    }

    public void setFontNum(Integer fontNum) {
        this.fontNum = fontNum;
    }

}