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
 * The persistent class for the base_rule_channel_config database table.
 * 
 */
@Entity
@Table(name="base_rule_channel_config")
@NamedQuery(name="BaseRuleChannelConfig.findAll", query="SELECT b FROM BaseRuleChannelConfig b")
public class BaseRuleChannelConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="channel_id")
	private Long channelId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_by")
	private Long createBy;

	@Temporal(TemporalType.DATE)
	@Column(name="del_date")
	private Date delDate;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="rule_id")
	private Long ruleId;

	@Column(name="status")
	private Integer status;

	 // Constructors

    /** default constructor */
    public BaseRuleChannelConfig() {
    }

    /** minimal constructor */
    public BaseRuleChannelConfig(Long ruleId, Long channelId, Integer status, Long createBy, Timestamp createDate, Timestamp modiDate) {
        this.ruleId = ruleId;
        this.channelId = channelId;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public BaseRuleChannelConfig(Long ruleId, Long channelId, Integer status, Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate,
            Date delDate) {
        this.ruleId = ruleId;
        this.channelId = channelId;
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

    public Long getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
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

}