package com.njq.common.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.njq.common.util.date.DateUtil;



/**
 * The persistent class for the tbk_recommend_doc_view database table.
 * 
 */
@Entity
@Table(name="tbk_recommend_doc_view")
@NamedQuery(name="TbkRecommendDocView.findAll", query="SELECT t FROM TbkRecommendDocView t")
public class TbkRecommendDocView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="formatText")
	private String formatText;

	@Column(name="general")
	private String general;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="readnums")
	private Integer readnums;

	@Column(name="readtype")
	private int readtype;

	@Column(name="reprint")
	private int reprint;

	@Column(name="tips")
	private String tips;

	@Column(name="title")
	private String title;

	@Column(name="typeId")
	private Long typeId;

	@Column(name="typeName")
	private String typeName;

	@Column(name="url")
	private String url;

	@Column(name="userName")
	private String userName;

	private String[] searchValue;
	// Constructors

	/** default constructor */
	public TbkRecommendDocView() {
	}

	/** minimal constructor */
	public TbkRecommendDocView(String title, String general) {
		this.title = title;
		this.general = general;
	}

	public TbkRecommendDocView(String title, String general, Integer readnums,
			Date createDate, String typeName, String url) {
		this.title = title;
		this.general = general;
		this.readnums = readnums;
		this.createDate = createDate;
		this.typeName = typeName;
		this.url = url;
	}
	
	/** full constructor */
	public TbkRecommendDocView(Long id ,String title, String general, Integer readnums,
			Date createDate, String typeName, String url,String tips,int reprint) {
		this.id=id;
		this.title = title;
		this.general = general;
		this.readnums = readnums;
		this.createDate = createDate;
		this.typeName = typeName;
		this.url = url;
		this.tips=tips;
		this.reprint=reprint;
	}

	public TbkRecommendDocView(Long id ,String title, String general, Integer readnums,
			Date createDate, String typeName, String url,String tips,int reprint,String userName) {
		this.id=id;
		this.title = title;
		this.general = general;
		this.readnums = readnums;
		this.createDate = createDate;
		this.typeName = typeName;
		this.url = url;
		this.tips=tips;
		this.reprint=reprint;
		this.userName=userName;
	}
	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGeneral() {
		return this.general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public Integer getReadnums() {
		return this.readnums;
	}

	public void setReadnums(Integer readnums) {
		this.readnums = readnums;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String[] getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String[] searchValue) {
		this.searchValue = searchValue;
	}
	
	public String getSearchGeneral(){
		if(this.reprint==2){
			return formatRed(getFormatGeneralHref1());
		}
		return formatRed(this.general);
	}
	
	public String getSearchTitle(){
		return formatRed(this.title);
	}
	
	public String getFormatCreatedDate(){
		return DateUtil.toDateString1(this.createDate);
	}
	
	public String getFormatCreatedDate2(){
		return DateUtil.toDateString5(this.createDate);
	}
	
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
	
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String getFormatTitle(){
		String s=this.title;
		if(s.length()>20){
			return s.substring(0, 20).toString()+"...";
		}else{
			return s;
		}
	}
	
	public String getFormatGeneral(){
		String s=check();
		if(s.length()>20){
			return s.substring(0, 20).toString()+"...";
		}else{
			return s+"...";
		}
	}
	
	public String getFormatGeneral50(){
		String s=check();
		if(s.length()>50){
			return s.substring(0, 50)+"...";
		}else{
			return s+"...";
		}
	}

	public String getFormatGeneral100(){
		String s=check();
		if(s.length()>100){
			return s.substring(0, 100)+"...";
		}else{
			return s+"...";
		}
	}
	
	public String[] getTipsList(){
		return formatRed(this.tips).split(",");
	}
	
	public int getReprint() {
		return reprint;
	}

	public void setReprint(int reprint) {
		this.reprint = reprint;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFormatGeneralHref1(){
		if(this.reprint==2){
			String g[]=this.general.split("&&");
			return g[0].toString()+"...";
		}else{
			return this.general;
		}
	}
	
	public String getFormatGeneralHref(){
		if(this.reprint==2){
			String g[]=this.general.split("&&");
			if(g.length>1){
				return g[1].toString()+"...";
			}else{
				return g[0].toString()+"...";
			}
		}else{
			return this.general;
		}
	}
	
	/**
	 * 标记红色部分
	 * @param str
	 * @return
	 */
	private String formatRed(String str){
		if(str==null){
			return "";
		}
		if(this.searchValue==null){
			return str;
		}
		if(this.searchValue.length>0){
			for(String value:searchValue){
				str=str.replaceAll(value,"<em>"+value+"</em>" );
			}
			return str;
		}
		return "";
	}

	public String getFormatText() {
		return formatText;
	}

	public void setFormatText(String formatText) {
		this.formatText = formatText;
	}

	public int getReadtype() {
		return readtype;
	}

	public void setReadtype(int readtype) {
		this.readtype = readtype;
	}
	
	private String check(){
		if(this.general.split("&&").length>1){
			return this.general.split("&&")[0];
		}else{
			return this.general;
		}
	}

}