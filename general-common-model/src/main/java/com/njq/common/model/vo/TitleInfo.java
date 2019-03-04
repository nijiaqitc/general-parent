package com.njq.common.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class TitleInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;

	private int apply;

	private String channel;

	private Long createBy;

	private Date createDate;

	private Long docId;

	private Long parentId;

	private String tips;

	private String title;

	private Long typeId;

}
