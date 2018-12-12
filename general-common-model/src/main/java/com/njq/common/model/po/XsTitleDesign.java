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
 * The persistent class for the xs_title_design database table.
 * 
 */
@Entity
@Table(name="xs_title_design")
@NamedQuery(name="XsTitleDesign.findAll", query="SELECT x FROM XsTitleDesign x")
public class XsTitleDesign implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="context_desc")
	private String contextDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="grade")
	private String grade;

	@Column(name="index_one")
	private Integer indexOne;

	@Column(name="index_two")
	private Integer indexTwo;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="title")
	private String title;

	@Column(name="type")
	private Integer type;

	// Constructors

    /** default constructor */
    public XsTitleDesign() {
    }

    /** full constructor */
    public XsTitleDesign(Long parentId, Integer type, String title, String grade, String contextDesc, Integer indexOne, Integer indexTwo,
            Timestamp createDate, Timestamp modiDate) {
        this.parentId = parentId;
        this.type = type;
        this.title = title;
        this.grade = grade;
        this.contextDesc = contextDesc;
        this.indexOne = indexOne;
        this.indexTwo = indexTwo;
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

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContextDesc() {
        return this.contextDesc;
    }

    public void setContextDesc(String contextDesc) {
        this.contextDesc = contextDesc;
    }

    public Integer getIndexOne() {
        return this.indexOne;
    }

    public void setIndexOne(Integer indexOne) {
        this.indexOne = indexOne;
    }

    public Integer getIndexTwo() {
        return this.indexTwo;
    }

    public void setIndexTwo(Integer indexTwo) {
        this.indexTwo = indexTwo;
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