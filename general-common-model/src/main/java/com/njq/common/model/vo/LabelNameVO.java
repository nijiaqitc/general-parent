package com.njq.common.model.vo;

import java.io.Serializable;

public class LabelNameVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer num;
	private Long totalNum;
	
	public LabelNameVO(Long id,String name, Integer num) {
		this.id = id;
		this.name = name;
		this.num = num;
	}
	
	public LabelNameVO(Long id,String name, Long totalNum) {
		this.id = id;
		this.name = name;
		this.totalNum = totalNum;
	}

	
	
	public LabelNameVO(Long id, Long totalNum) {
		super();
		this.id = id;
		this.totalNum = totalNum;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		if(totalNum != null) {
			return totalNum.intValue();			
		}else {
			return num;
		}
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	
	
}
