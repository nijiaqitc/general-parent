package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the base_banner database table.
 * 
 */
@Entity
@Table(name="base_banner")
@NamedQuery(name="BaseBanner.findAll", query="SELECT b FROM BaseBanner b")
public class BaseBanner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="is_use")
	private String isUse;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="pic_place")
	private String picPlace;

	/** default constructor */
    public BaseBanner() {
    }

    /** full constructor */
    public BaseBanner(String name, String isUse, String picPlace, Timestamp createDate, Timestamp modiDate) {
        this.name = name;
        this.isUse = isUse;
        this.picPlace = picPlace;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsUse() {
        return this.isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getPicPlace() {
        return this.picPlace;
    }

    public void setPicPlace(String picPlace) {
        this.picPlace = picPlace;
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