package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the base_channel database table.
 * 
 */
@Entity
@Table(name="base_channel")
@NamedQuery(name="BaseChannel.findAll", query="SELECT b FROM BaseChannel b")
public class BaseChannel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="apply")
	private Integer apply;

	@Column(name="channel_name")
	private String channelName;

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

	@Column(name="icon")
	private String icon;

	@Column(name="in_turn")
	private Integer inTurn;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="parant_id")
	private Long parantId;

	@Column(name="status")
	private Integer status;

	@Column(name="url")
	private String url;

	/** default constructor */
    public BaseChannel() {
    }

    /** minimal constructor */
    public BaseChannel(Long parantId, String channelName, Timestamp modiDate) {
        this.parantId = parantId;
        this.channelName = channelName;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public BaseChannel(Long parantId, String channelName, String url, String icon, Integer apply, Integer inTurn, String columDesc, Integer status,
            Long createBy, Timestamp createDate, Long modiBy, Timestamp modiDate, Date delDate) {
        this.parantId = parantId;
        this.channelName = channelName;
        this.url = url;
        this.icon = icon;
        this.apply = apply;
        this.inTurn = inTurn;
        this.columDesc = columDesc;
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

    public Long getParantId() {
        return this.parantId;
    }

    public void setParantId(Long parantId) {
        this.parantId = parantId;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getApply() {
        return this.apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }

    public Integer getInTurn() {
        return this.inTurn;
    }

    public void setInTurn(Integer inTurn) {
        this.inTurn = inTurn;
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