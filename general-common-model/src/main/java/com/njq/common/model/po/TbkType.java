package com.njq.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the tbk_type database table.
 * 
 */
@Entity
@Table(name="tbk_type")
@NamedQuery(name="TbkType.findAll", query="SELECT t FROM TbkType t")
public class TbkType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

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

	@Column(name="in_turn")
	private Integer inTurn;

	@Column(name="modi_by")
	private Long modiBy;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="status")
	private Integer status;

//	private List<TbkRecommendDocView> textList;
	
//	private String parentName;
	
	// Constructors

    /** default constructor */
    public TbkType() {
    }

    
    public TbkType(Long id, String name, Long createBy, Date createDate) {
		super();
		this.id = id;
		this.name = name;
		this.createBy = createBy;
		this.createDate = createDate;
	}


	/** minimal constructor */
    public TbkType(Long parentId, String name, Timestamp modiDate) {
        this.parentId = parentId;
        this.name = name;
        this.modiDate = modiDate;
    }

    /** full constructor */
    public TbkType(Long parentId, String name, Integer inTurn, String columDesc, Integer status, Long createBy, Timestamp createDate, Long modiBy,
            Timestamp modiDate, Date delDate) {
        this.parentId = parentId;
        this.name = name;
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

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public List<TbkRecommendDocView> getTextList() {
//        return textList;
//    }
//
//    public void setTextList(List<TbkRecommendDocView> textList) {
//        this.textList = textList;
//    }




}