package com.njq.common.model.vo.grab;

import java.io.Serializable;
import java.util.Date;

public class GrabTitleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long parentId;	
	
	private String tips;

	private String title;
	
	private Long typeId;
	
	private int childrenCount;

	private String channel;

	private Boolean starTab;

	private Long docId;
	
	private Date createDate;
	
	private Boolean isParent;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Boolean getStarTab() {
		return starTab;
	}

	public void setStarTab(Boolean starTab) {
		this.starTab = starTab;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
}
