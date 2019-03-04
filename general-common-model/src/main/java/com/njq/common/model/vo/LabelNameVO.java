package com.njq.common.model.vo;

import java.io.Serializable;

public class LabelNameVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer num;
	private Long totalNum;
	
	public LabelNameVO(String name, Integer num) {
		this.name = name;
		this.num = num;
	}
	
	public LabelNameVO(String name, Long totalNum) {
		this.name = name;
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

	@Override
	public String toString() {
		return "LabelNameVO [name=" + name + ", num=" + num + ", totalNum=" + totalNum + "]";
	}
	
	
	
}
