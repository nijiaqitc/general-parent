/**
 * 公用的分页类
 */
package com.njq.common.base.dao;

import java.util.List;

public class PageList<T> {
	
	//总的记录数
	private int total;
	//返回的集合
	private List<T> list;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
