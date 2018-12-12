package com.njq.common.model.vo;

import java.util.List;

public class LeftMenu {
	private int type;
	private String name;
	private String value;
	private String docId;
	private List<LeftMenu> menuList;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<LeftMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<LeftMenu> menuList) {
		this.menuList = menuList;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	

}
