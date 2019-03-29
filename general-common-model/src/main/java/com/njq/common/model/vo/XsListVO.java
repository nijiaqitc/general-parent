package com.njq.common.model.vo;

import java.io.Serializable;
import java.util.List;

import com.njq.common.model.po.XsTitleDetail;

public class XsListVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long pId;
	private Integer total;
	private List<XsTitleDetail> list;
	private XsTitleDetail juanDetail;
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<XsTitleDetail> getList() {
		return list;
	}
	public void setList(List<XsTitleDetail> list) {
		this.list = list;
	}
	public XsTitleDetail getJuanDetail() {
		return juanDetail;
	}
	public void setJuanDetail(XsTitleDetail juanDetail) {
		this.juanDetail = juanDetail;
	}
	

}
