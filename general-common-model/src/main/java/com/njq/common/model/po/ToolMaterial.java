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


/**
 * The persistent class for the tool_material database table.
 * 
 */
@Entity
@Table(name="tool_material")
@NamedQuery(name="ToolMaterial.findAll", query="SELECT t FROM ToolMaterial t")
public class ToolMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="create_date")
	private Date createDate;

	@Column(name="info")
	private String info;

	@Column(name="last_name")
	private String lastName;

	@Column(name="name")
	private String name;

	@Column(name="superclass")
	private String superclass;

	@Column(name="type")
	private String type;

	// Constructors

    /** default constructor */
    public ToolMaterial() {
    }

    /** minimal constructor */
    public ToolMaterial(Timestamp createDate) {
        this.createDate = createDate;
    }

    /** full constructor */
    public ToolMaterial(String name, String lastName, String type, String superclass, String info, Timestamp createDate) {
        this.name = name;
        this.lastName = lastName;
        this.type = type;
        this.superclass = superclass;
        this.info = info;
        this.createDate = createDate;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuperclass() {
        return this.superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}