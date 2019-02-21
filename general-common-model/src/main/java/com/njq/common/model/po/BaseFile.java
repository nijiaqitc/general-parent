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
 * The persistent class for the base_file database table.
 */
@Entity
@Table(name = "base_file")
@NamedQuery(name = "BaseFile.findAll", query = "SELECT b FROM BaseFile b")
public class BaseFile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "old_name")
    private String oldName;

    @Column(name = "file_place")
    private String filePlace;

    @Column(name = "old_src")
    private String oldSrc;

    @Column(name = "real_place")
    private String realPlace;

    @Column(name = "channel")
    private String channel;

    @Column(name = "type_id")
    private Long typeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modi_date")
    private Timestamp modiDate;

    @Column(name = "load_flag")
    private Boolean loadFlag;

    /**
     * default constructor
     */
    public BaseFile() {
    }

    public BaseFile(String name, String oldName, String filePlace, String oldSrc, String realPlace, String channel, Long typeId, Date createDate, Timestamp modiDate, Boolean loadFlag) {
        this.name = name;
        this.oldName = oldName;
        this.filePlace = filePlace;
        this.oldSrc = oldSrc;
        this.realPlace = realPlace;
        this.channel = channel;
        this.typeId = typeId;
        this.createDate = createDate;
        this.modiDate = modiDate;
        this.loadFlag = loadFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getfilePlace() {
        return filePlace;
    }

    public void setfilePlace(String filePlace) {
        this.filePlace = filePlace;
    }

    public String getRealPlace() {
        return realPlace;
    }

    public void setRealPlace(String realPlace) {
        this.realPlace = realPlace;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public String getFilePlace() {
        return filePlace;
    }

    public void setFilePlace(String filePlace) {
        this.filePlace = filePlace;
    }

    public String getOldSrc() {
        return oldSrc;
    }

    public void setOldSrc(String oldSrc) {
        this.oldSrc = oldSrc;
    }

    public Boolean getLoadFlag() {
        return loadFlag;
    }

    public void setLoadFlag(Boolean loadFlag) {
        this.loadFlag = loadFlag;
    }
}