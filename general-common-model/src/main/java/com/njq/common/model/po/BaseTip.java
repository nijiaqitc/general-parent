package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the base_tip database table.
 * 
 */
@Entity
@Table(name="base_tip")
@NamedQuery(name="BaseTip.findAll", query="SELECT b FROM BaseTip b")
public class BaseTip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="tip_name")
	private String tipName;

	public BaseTip() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTipName() {
		return this.tipName;
	}

	public void setTipName(String tipName) {
		this.tipName = tipName;
	}

}