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
 * The persistent class for the tool_resourceshare database table.
 * 
 */
@Entity
@Table(name="tool_resourceshare")
@NamedQuery(name="ToolResourceshare.findAll", query="SELECT t FROM ToolResourceshare t")
public class ToolResourceshare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="code_id_a")
	private Long codeIdA;

	@Column(name="code_id_b")
	private Long codeIdB;

	@Column(name="code_id_c")
	private String codeIdC;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="is_checked")
	private String isChecked;

	@Column(name="is_losted")
	private String isLosted;

	@Column(name="is_stoped")
	private String isStoped;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="pic_url_a")
	private String picUrlA;

	@Column(name="pic_url_b")
	private String picUrlB;

	@Column(name="pic_url_base")
	private String picUrlBase;

	@Column(name="pic_url_c")
	private String picUrlC;

	@Column(name="place")
	private String place;

	@Column(name="pwd")
	private String pwd;

	@Column(name="resource_desc")
	private String resourceDesc;

	// Constructors

    /** default constructor */
    public ToolResourceshare() {
    }

    /** minimal constructor */
    public ToolResourceshare(String place, String isChecked, String isLosted, String isStoped) {
        this.place = place;
        this.isChecked = isChecked;
        this.isLosted = isLosted;
        this.isStoped = isStoped;
    }

    /** full constructor */
    public ToolResourceshare(String resourceDesc, Long codeIdA, Long codeIdB, String codeIdC, String place, String pwd, String isChecked,
            String picUrlBase, String picUrlA, String picUrlB, String picUrlC, String isLosted, String isStoped, Long createBy, Timestamp createDate,
            Long modiBy, Timestamp modiDate, Date delDate) {
        this.resourceDesc = resourceDesc;
        this.codeIdA = codeIdA;
        this.codeIdB = codeIdB;
        this.codeIdC = codeIdC;
        this.place = place;
        this.pwd = pwd;
        this.isChecked = isChecked;
        this.picUrlBase = picUrlBase;
        this.picUrlA = picUrlA;
        this.picUrlB = picUrlB;
        this.picUrlC = picUrlC;
        this.isLosted = isLosted;
        this.isStoped = isStoped;
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

    public String getResourceDesc() {
        return this.resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public Long getCodeIdA() {
        return this.codeIdA;
    }

    public void setCodeIdA(Long codeIdA) {
        this.codeIdA = codeIdA;
    }

    public Long getCodeIdB() {
        return this.codeIdB;
    }

    public void setCodeIdB(Long codeIdB) {
        this.codeIdB = codeIdB;
    }

    public String getCodeIdC() {
        return this.codeIdC;
    }

    public void setCodeIdC(String codeIdC) {
        this.codeIdC = codeIdC;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getPicUrlBase() {
        return this.picUrlBase;
    }

    public void setPicUrlBase(String picUrlBase) {
        this.picUrlBase = picUrlBase;
    }

    public String getPicUrlA() {
        return this.picUrlA;
    }

    public void setPicUrlA(String picUrlA) {
        this.picUrlA = picUrlA;
    }

    public String getPicUrlB() {
        return this.picUrlB;
    }

    public void setPicUrlB(String picUrlB) {
        this.picUrlB = picUrlB;
    }

    public String getPicUrlC() {
        return this.picUrlC;
    }

    public void setPicUrlC(String picUrlC) {
        this.picUrlC = picUrlC;
    }

    public String getIsLosted() {
        return this.isLosted;
    }

    public void setIsLosted(String isLosted) {
        this.isLosted = isLosted;
    }

    public String getIsStoped() {
        return this.isStoped;
    }

    public void setIsStoped(String isStoped) {
        this.isStoped = isStoped;
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

}