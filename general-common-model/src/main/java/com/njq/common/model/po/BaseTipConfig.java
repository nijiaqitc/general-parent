package com.njq.common.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the yxl_doc_tip_config database table.
 */
@Entity
@Table(name = "base_tip_config")
@NamedQuery(name = "BaseTipConfig.findAll", query = "SELECT y FROM BaseTipConfig y")
public class BaseTipConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_id")
    private Long titleId;

    @Column(name = "tip_id")
    private Long tipId;

    @Column(name = "source_type")
    private String sourceType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modi_date")
    private Timestamp modiDate;

    /**
     * default constructor
     */
    public BaseTipConfig() {
    }

    public BaseTipConfig(Long titleId, Long tipId, String sourceType, Date createDate, Timestamp modiDate) {
        this.titleId = titleId;
        this.tipId = tipId;
        this.sourceType = sourceType;
        this.createDate = createDate;
        this.modiDate = modiDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Long getTipId() {
        return tipId;
    }

    public void setTipId(Long tipId) {
        this.tipId = tipId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModiDate() {
        return modiDate;
    }

    public void setModiDate(Timestamp modiDate) {
        this.modiDate = modiDate;
    }
}