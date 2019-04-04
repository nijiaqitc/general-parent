package com.njq.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.njq.common.util.date.DateUtil;



/**
 * The persistent class for the tbk_doc database table.
 * 
 */
@Entity
@Table(name="tbk_doc")
@NamedQuery(name="TbkDoc.findAll", query="SELECT t FROM TbkDoc t")
public class TbkDoc implements Serializable {
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

	@Column(name="general")
	private String general;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="readnums")
	private Integer readnums;

	@Column(name="readtype")
	private Integer readtype;

	@Column(name="reprint")
	private Integer reprint;
	
	@Column(name="status")
	private Integer status;

	@Lob
	@Column(name="text")
	private String text;

	@Column(name="title")
	private String title;

	@Column(name="user_id")
	private Long userId;

	// Constructors

    /** default constructor */
    public TbkDoc() {
    }

    /** minimal constructor */
    public TbkDoc(String title, String general, Timestamp modiDate) {
        this.title = title;
        this.general = general;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public TbkDoc(String title, String general, String text, Integer readnums, Long userId, Integer reprint, Integer readtype, Integer status,
            Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate, Date delDate) {
        this.title = title;
        this.general = general;
        this.text = text;
        this.readnums = readnums;
        this.userId = userId;
        this.reprint = reprint;
        this.readtype = readtype;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneral() {
        return this.general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getReadnums() {
        return this.readnums;
    }

    public void setReadnums(Integer readnums) {
        this.readnums = readnums;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getReprint() {
        return this.reprint;
    }

    public void setReprint(Integer reprint) {
        this.reprint = reprint;
    }

    public Integer getReadtype() {
        return this.readtype;
    }

    public void setReadtype(Integer readtype) {
        this.readtype = readtype;
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
    
    public String getFormatCreatedDate(){
    	return DateUtil.toDateString2(createDate);
    }
    
    public String getFormatGeneral(){
    	if(general.length()>30){
    		return general.substring(0, 20)+"...";
    	}else{
    		return general;
    	}
    }
    
    public String getFormatGeneralHref(){
    	return "";
    }
    
    public String getFormatTitle(){
    	if(title.length()>10){
    		return title.substring(0, 10)+"...";
    	}else{
    		return title;
    	}
    }

}