package com.njq.common.model.po;

import java.io.Serializable;
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
	private Long parentId;

	private String type;

	private String href;
	
	private Integer loaded;
	
	@Column(name="channel")
	private String channel;
	
	@Column(name="load_times")
	private Integer loadTimes;
	
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getLoadTimes() {
		return loadTimes;
	}

	public void setLoadTimes(Integer loadTimes) {
		this.loadTimes = loadTimes;
	}

}