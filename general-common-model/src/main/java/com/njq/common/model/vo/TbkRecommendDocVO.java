package com.njq.common.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.njq.common.util.date.DateUtil;

public class TbkRecommendDocVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date createDate;

	private String formatText;

	private String general;

	private Long id;

	private Integer readnums;

	private int readtype;

	private int reprint;

	private String tips;

	private String title;

	private Long typeId;

	private String typeName;

	private String url;

	private String userName;

	private String[] searchValue;
	
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
