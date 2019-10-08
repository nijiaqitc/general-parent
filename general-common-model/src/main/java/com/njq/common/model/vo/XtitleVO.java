package com.njq.common.model.vo;

import java.io.Serializable;

import com.njq.common.util.string.StringUtil2;

public class XtitleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long docId;
	private String title;
	
	public Long getDocId() {
		return docId;
	}
	
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	
	public String getTitle() {
		if(StringUtil2.isEmpty(title)) {
			return "";
		}else {
			if(title.length()>20) {
				return title.substring(0, 20)+"...";
			}else {
				return title;
			}
		}
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "XtitleVO [docId=" + docId + ", title=" + title + "]";
	}
	
	
}
