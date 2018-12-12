package com.njq.common.model.vo;


public class ResourceshareVO implements java.io.Serializable {

	private static final long serialVersionUID = -1187644157336365607L;
	private Long id;
	private String resourceDesc;
	private Long codeIdA;
	private Long codeIdB;
	private Long codeIdC;
	private String place;
	private String pwd;
	private String picUrlBase;
	private String picUrlA;
	private String picUrlB;
	private String picUrlC;
	
	public ResourceshareVO(){
		
	}
	
	public ResourceshareVO(Long id, String resourceDesc, Long codeIdA,
			Long codeIdB, Long codeIdC, String place, String pwd,
			String picUrlA, String picUrlB, String picUrlC) {
		super();
		this.id = id;
		this.resourceDesc = resourceDesc;
		this.codeIdA = codeIdA;
		this.codeIdB = codeIdB;
		this.codeIdC = codeIdC;
		this.place = place;
		this.pwd = pwd;
		this.picUrlA = picUrlA;
		this.picUrlB = picUrlB;
		this.picUrlC = picUrlC;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResourceDesc() {
		return resourceDesc;
	}
	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	public Long getCodeIdA() {
		return codeIdA;
	}
	public void setCodeIdA(Long codeIdA) {
		this.codeIdA = codeIdA;
	}
	public Long getCodeIdB() {
		return codeIdB;
	}
	public void setCodeIdB(Long codeIdB) {
		this.codeIdB = codeIdB;
	}
	public Long getCodeIdC() {
		return codeIdC;
	}
	public void setCodeIdC(Long codeIdC) {
		this.codeIdC = codeIdC;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPicUrlA() {
		return picUrlA;
	}
	public void setPicUrlA(String picUrlA) {
		this.picUrlA = picUrlA;
	}
	public String getPicUrlB() {
		return picUrlB;
	}
	public void setPicUrlB(String picUrlB) {
		this.picUrlB = picUrlB;
	}
	public String getPicUrlC() {
		return picUrlC;
	}
	public void setPicUrlC(String picUrlC) {
		this.picUrlC = picUrlC;
	}

	public String getPicUrlBase() {
		return picUrlBase;
	}

	public void setPicUrlBase(String picUrlBase) {
		this.picUrlBase = picUrlBase;
	}

}