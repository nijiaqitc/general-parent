package com.njq.common.model.vo.xs;

import java.io.Serializable;
import java.util.Date;

public class XsTitleDetailVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long bookId;

	private String contextDesc;

	private Date createDate;

	private String finishStatus;

	private String isShow;

	private Long docId;

	private Integer orderIndex;

	private Long parentId;

	private String title;

	private Integer titleIndex;

	private Integer type;

	private Long userId;
	
	private Integer maxTitleIndex;
	
	private Integer maxOrderIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getContextDesc() {
		return contextDesc;
	}

	public void setContextDesc(String contextDesc) {
		this.contextDesc = contextDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMaxTitleIndex() {
		return maxTitleIndex;
	}

	public void setMaxTitleIndex(Integer maxTitleIndex) {
		this.maxTitleIndex = maxTitleIndex;
	}

	public Integer getMaxOrderIndex() {
		return maxOrderIndex;
	}

	public void setMaxOrderIndex(Integer maxOrderIndex) {
		this.maxOrderIndex = maxOrderIndex;
	}

	public String getStatusName() {
		if("0".equals(finishStatus)) {
			return "未开始";
		}else if("1".equals(finishStatus)) {
			return "编写中";
		}else if("2".equals(finishStatus)) {
			return "已完成";
		}
		return "待处理";
	}

	public Integer getTitleIndex() {
		return titleIndex;
	}

	public void setTitleIndex(Integer titleIndex) {
		this.titleIndex = titleIndex;
	}
	
}
