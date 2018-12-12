package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the yxl_tip database table.
 * 
 */
@Entity
@Table(name="yxl_tip")
@NamedQuery(name="YxlTip.findAll", query="SELECT y FROM YxlTip y")
public class YxlTip implements Serializable {
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

	// Constructors

    /** default constructor */
    public YxlTip() {
    }

    /** full constructor */
    public YxlTip(String tipName, Date createDate, Timestamp modiDate) {
        this.tipName = tipName;
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

    public String getTipName() {
        return this.tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
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