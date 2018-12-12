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
 * The persistent class for the yxl_type_name database table.
 * 
 */
@Entity
@Table(name="yxl_type_name")
@NamedQuery(name="YxlTypeName.findAll", query="SELECT y FROM YxlTypeName y")
public class YxlTypeName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="name")
	private String name;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="status")
	private Integer status;

	@Column(name="type")
	private Integer type;

	// Constructors

    /** default constructor */
    public YxlTypeName() {
    }

    
    /** full constructor */
    public YxlTypeName(Long parentId, String name, Integer status, Integer type, Timestamp createDate, Timestamp modiDate) {
        this.parentId = parentId;
        this.name = name;
        this.status = status;
        this.type = type;
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

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getModiDate() {
        return this.modiDate;
    }
    
    public void setModiDate(Timestamp modiDate) {
        this.modiDate = modiDate;
    }


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}