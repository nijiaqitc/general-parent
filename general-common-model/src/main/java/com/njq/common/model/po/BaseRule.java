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
 * The persistent class for the base_rule database table.
 * 
 */
@Entity
@Table(name="base_rule")
@NamedQuery(name="BaseRule.findAll", query="SELECT b FROM BaseRule b")
public class BaseRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="channel_id")
	private Long channelId;

	@Column(name="colum_desc")
	private String columDesc;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="is_default")
	private Integer isDefault;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="rule_name")
	private String ruleName;

	@Column(name="status")
	private Integer status;

	// Constructors

    /** default constructor */
    public BaseRule() {
    }

    /** minimal constructor */
    public BaseRule(Long channelId, String ruleName, Timestamp modiDate) {
        this.channelId = channelId;
        this.ruleName = ruleName;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public BaseRule(Long channelId, String ruleName, String columDesc, Integer status, Integer isDefault, Long createBy, Timestamp createDate,
            Long modiBy, Timestamp modiDate, Date delDate) {
        this.channelId = channelId;
        this.ruleName = ruleName;
        this.columDesc = columDesc;
        this.status = status;
        this.isDefault = isDefault;
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

    public Long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getColumDesc() {
        return this.columDesc;
    }

    public void setColumDesc(String columDesc) {
        this.columDesc = columDesc;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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