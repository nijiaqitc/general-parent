package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the base_title_loading database table.
 * 
 */
@Entity
@Table(name="base_title_loading")
@NamedQuery(name="BaseTitleLoading.findAll", query="SELECT b FROM BaseTitleLoading b")
public class BaseTitleLoading implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String channel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private String loaded;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="title_id")
	private Long titleId;

	private String url;
	
	@Column(name="doc_id_source")
	private String docIdSource;
	
	@Column(name = "try_num")
    private Integer tryNum;
	
	public BaseTitleLoading() {
	}

	public BaseTitleLoading(Long id, String channel, Date createDate, String loaded, Timestamp modiDate, Long titleId,
			String url, String docIdSource, Integer tryNum) {
		super();
		this.id = id;
		this.channel = channel;
		this.createDate = createDate;
		this.loaded = loaded;
		this.modiDate = modiDate;
		this.titleId = titleId;
		this.url = url;
		this.docIdSource = docIdSource;
		this.tryNum = tryNum;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLoaded() {
		return this.loaded;
	}

	public void setLoaded(String loaded) {
		this.loaded = loaded;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public Long getTitleId() {
		return this.titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocIdSource() {
		return docIdSource;
	}

	public void setDocIdSource(String docIdSource) {
		this.docIdSource = docIdSource;
	}

	public Integer getTryNum() {
		return tryNum;
	}

	public void setTryNum(Integer tryNum) {
		this.tryNum = tryNum;
	}

	
}