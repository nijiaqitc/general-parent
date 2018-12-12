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
 * The persistent class for the tool_feeling database table.
 * 
 */
@Entity
@Table(name="tool_feeling")
@NamedQuery(name="ToolFeeling.findAll", query="SELECT t FROM ToolFeeling t")
public class ToolFeeling implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="feel_type")
	private String feelType;

	@Column(name="is_lock")
	private String isLock;

	@Column(name="place")
	private String place;

	@Column(name="text")
	private String text;

	@Column(name="text_type")
	private String textType;

	@Column(name="user_id")
	private Long userId;

	// Constructors

    /** default constructor */
    public ToolFeeling() {
    }

    /** full constructor */
    public ToolFeeling(Long userId, String feelType, String textType, String place, String text, String isLock, Timestamp createTime) {
        this.userId = userId;
        this.feelType = feelType;
        this.textType = textType;
        this.place = place;
        this.text = text;
        this.isLock = isLock;
        this.createTime = createTime;
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

    public String getFeelType() {
        return this.feelType;
    }

    public void setFeelType(String feelType) {
        this.feelType = feelType;
    }

    public String getTextType() {
        return this.textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsLock() {
        return this.isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}