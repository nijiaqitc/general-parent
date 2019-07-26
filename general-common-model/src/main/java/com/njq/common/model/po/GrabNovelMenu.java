package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the grab_novel_menu database table.
 * 
 */
@Entity
@Table(name="grab_novel_menu")
@NamedQuery(name="GrabNovelMenu.findAll", query="SELECT g FROM GrabNovelMenu g")
public class GrabNovelMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private String name;

	@Column(name="parent_id")
	private BigInteger parentId;

	private String type;

	private String href;
	
	private Integer loaded;
	
	public GrabNovelMenu() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getParentId() {
		return this.parentId;
	}

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getLoaded() {
		return loaded;
	}

	public void setLoaded(Integer loaded) {
		this.loaded = loaded;
	}
	
}