package com.njq.common.model.vo;

import java.util.List;
import java.util.Map;

public class DocVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	
	private List<Map<String, Object>> tipList;

	public DocVO(){
		
	}
	
	public DocVO(Long id, String title, List<Map<String, Object>> tipList) {
		super();
		this.id = id;
		this.title = title;
		this.tipList = tipList;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		if(this.title.length()>20){
			return this.title.substring(0, 18)+"...";
		}else{
			return title;			
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Map<String, Object>> getTipList() {
		return tipList;
	}

	public void setTipList(List<Map<String, Object>> tipList) {
		this.tipList = tipList;
	}
	
	

	
}
