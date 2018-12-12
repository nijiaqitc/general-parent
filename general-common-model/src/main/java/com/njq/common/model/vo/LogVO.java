package com.njq.common.model.vo;

import java.util.Date;

import com.njq.common.util.date.DateUtil;

public class LogVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	//用户id
	private Long userId;
	//操作类型(1:增 2：删  3：改)
	private String type;
	//操作表名
	private String operTable;
	//操作条件，任意写
	private String operCon;
	//修改时间
	private Date modiDate;
	//操作用户
	private String userName;
	
	public LogVO(){
	}
	
	public LogVO(Long id){
		this.id=id;
	}
	
	public LogVO(Long id,Long userId,String type ,String operTable,String operCon,Date modiDate,String userName){
		this.id=id;
		this.type=type;
		this.operTable=operTable;
		this.operCon=operCon;
		this.modiDate=modiDate;
		this.userName=userName;
		this.userId=userId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperTable() {
		return operTable;
	}
	public void setOperTable(String operTable) {
		this.operTable = operTable;
	}
	public String getOperCon() {
		return operCon;
	}
	public void setOperCon(String operCon) {
		this.operCon = operCon;
	}
	public Date getModiDate() {
		return modiDate;
	}
	public void setModiDate(Date modiDate) {
		this.modiDate = modiDate;
	}
	public String getUserName() {
//		if(ConstantsCommon.Oper_User.NO_USER==userId){
//			return "系统";
//		}else if(null==userId){
//			return "黑客";
//		}else{
//			return userName;
//		}
		return null;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFormatDate(){
		return DateUtil.toDateString4(this.modiDate);
	}
	
}
