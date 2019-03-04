package com.njq.common.model.vo;

public class ChannelVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private String channelName;
	
	private Long parentId;
	
	private String url;
	
	private String icon;
	
	private int inTurn;
	
	public ChannelVO() {
		
	}
	public ChannelVO(Long id , String channelName){
		this.id=id;
		this.channelName=channelName;
	}
	
	public ChannelVO(Long id , String channelName,Long parentId){
		this.id=id;
		this.channelName=channelName;
		this.parentId=parentId;
	}
	public ChannelVO(Long id , String channelName,Long parentId,String url , String icon , int inTurn){
		this.id=id;
		this.channelName=channelName;
		this.parentId=parentId;
		this.url=url;
		this.icon=icon;
		this.inTurn=inTurn;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getInTurn() {
		return inTurn;
	}

	public void setInTurn(int inTurn) {
		this.inTurn = inTurn;
	}

	
}
