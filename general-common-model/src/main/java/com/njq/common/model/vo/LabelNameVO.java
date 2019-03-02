package com.njq.common.model.vo;

import java.io.Serializable;

public class LabelNameVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "LabelNameVO [name=" + name + ", num=" + num + "]";
	}
	
}
