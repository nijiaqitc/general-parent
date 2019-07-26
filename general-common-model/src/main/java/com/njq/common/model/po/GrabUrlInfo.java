package com.njq.common.model.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the grab_url_info database table.
 * 
 */
@Entity
@Table(name="grab_url_info")
@NamedQuery(name="GrabUrlInfo.findAll", query="SELECT g FROM GrabUrlInfo g")
public class GrabUrlInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String channel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="data_json")
	private String dataJson;

	@Column(name="login_url")
	private String loginUrl;

	@Column(name="menu_url")
	private String menuUrl;

	@Column(name="modi_date")
	private Timestamp modiDate;

	@Column(name="page_index")
	private String pageIndex;

	private String pwd;

	@Column(name="short_name")
	private String shortName;

	@Column(name="user_name")
	private String userName;

	@Column(name="load_btn")
	private Boolean loadBtn;

	@Column(name="type_name")
	private String typeName;
	
	@Column(name="url_type")
	private String urlType;
	
	public GrabUrlInfo() {
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

	public String getDataJson() {
		return this.dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public String getLoginUrl() {
		return this.loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Timestamp getModiDate() {
		return this.modiDate;
	}

	public void setModiDate(Timestamp modiDate) {
		this.modiDate = modiDate;
	}

	public String getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getLoadBtn() {
		return loadBtn;
	}

	public void setLoadBtn(Boolean loadBtn) {
		this.loadBtn = loadBtn;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	
}