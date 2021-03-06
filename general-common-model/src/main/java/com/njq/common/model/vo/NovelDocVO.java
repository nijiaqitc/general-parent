package com.njq.common.model.vo;

import java.util.Date;

public class NovelDocVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long docId;
	
	private String title;
	
	private String text;
	
	private int good;
	
	private int down;
	
	private int view;
	
	private Date createDate;

	private Long beforeMenuId;
	private Long afterMenuId;
	
    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getTitle() {
    	if(title == null) {
    		return "";
    	}
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
    	if(text == null) {
    		return "";
    	}
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Long getBeforeMenuId() {
		return beforeMenuId;
	}

	public void setBeforeMenuId(Long beforeMenuId) {
		this.beforeMenuId = beforeMenuId;
	}

	public Long getAfterMenuId() {
		return afterMenuId;
	}

	public void setAfterMenuId(Long afterMenuId) {
		this.afterMenuId = afterMenuId;
	}

	
}
