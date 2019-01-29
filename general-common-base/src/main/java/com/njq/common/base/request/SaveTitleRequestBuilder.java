package com.njq.common.base.request;

import java.io.Serializable;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.model.vo.LeftMenu;

public class SaveTitleRequestBuilder implements Serializable {
	private static final long serialVersionUID = -3501622473326856193L;
	protected Long parentId;
	protected LeftMenu menu;
	protected Long typeId;
	protected String tips;
	protected ChannelType channel;
	protected TitleType titleType;
	protected Long id;
	
	public SaveTitleRequestBuilder ofParentId(Long parentId) {
		this.parentId = parentId;
		return this;
	}

	public SaveTitleRequestBuilder onMenu(LeftMenu menu) {
		this.menu = menu;
		return this;
	}

	public SaveTitleRequestBuilder ofTypeId(Long typeId) {
		this.typeId = typeId;
		return this;
	}

	public SaveTitleRequestBuilder ofTips(String tips) {
		this.tips = tips;
		return this;
	}

	public SaveTitleRequestBuilder ofChannel(ChannelType channel) {
		this.channel = channel;
		return this;
	}

	public SaveTitleRequestBuilder ofId(Long id) {
		this.id = id;
		return this;
	}
	
	public SaveTitleRequestBuilder ofTitleType(TitleType type) {
		this.titleType = type;
		return this;
	}
	
	public SaveTitleRequest build() {
		return new SaveTitleRequest(this);
	}

}
