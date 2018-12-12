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
 * The persistent class for the xs_title_detail database table.
 * 
 */
@Entity
@Table(name="xs_title_detail")
@NamedQuery(name="XsTitleDetail.findAll", query="SELECT x FROM XsTitleDetail x")
public class XsTitleDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="book_id")
	private Long bookId;

	@Column(name="context_desc")
	private String contextDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="finish_status")
	private String finishStatus;

	@Column(name="is_show")
	private String isShow;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="order_index")
	private Integer orderIndex;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="title")
	private String title;

	@Column(name="title_index")
	private String titleIndex;

	@Column(name="type")
	private Integer type;

	// Constructors

    /** default constructor */
    public XsTitleDetail() {
    }

    /** full constructor */
    public XsTitleDetail(Long parentId, Integer type, String title, String contextDesc, String isShow, String titleIndex, Integer orderIndex,
            Timestamp createDate, Timestamp modiDate) {
        this.parentId = parentId;
        this.type = type;
        this.title = title;
        this.contextDesc = contextDesc;
        this.isShow = isShow;
        this.titleIndex = titleIndex;
        this.orderIndex = orderIndex;
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

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContextDesc() {
        return this.contextDesc;
    }

    public void setContextDesc(String contextDesc) {
        this.contextDesc = contextDesc;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getTitleIndex() {
        return this.titleIndex;
    }

    public void setTitleIndex(String titleIndex) {
        this.titleIndex = titleIndex;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

}