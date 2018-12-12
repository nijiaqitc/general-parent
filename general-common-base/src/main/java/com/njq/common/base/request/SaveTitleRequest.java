package com.njq.common.base.request;

import java.io.Serializable;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.vo.LeftMenu;

public class SaveTitleRequest implements Serializable {
	private static final long serialVersionUID = 5935206907478137202L;
	private Long parentId;
	private LeftMenu menu;
	private Long typeId;
	private String tips;
	private ChannelType channel;
	private Long id;
	
	public SaveTitleRequest() {
	}

	public SaveTitleRequest(SaveTitleRequestBuilder builder) {
		this.parentId = builder.parentId;
		this.menu = builder.menu;
		this.typeId = builder.typeId;
		this.tips = builder.tips;
		this.channel = builder.channel;
		this.id = builder.id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public LeftMenu getMenu() {
		return menu;
	}

	public void setMenu(LeftMenu menu) {
		this.menu = menu;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public ChannelType getChannel() {
		return channel;
	}

	public void setChannel(ChannelType channel) {
		this.channel = channel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
