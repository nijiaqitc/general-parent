/**
 * 条件类，存放各种条件
 */
package com.njq.common.base.dao;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;

public class ConditionsCommon {
	
	//where条件
	private Map<String, Object> paramMap=new HashMap<String, Object>();
	//分页条件
	private Map<String, Integer> pageMap=new HashMap<String, Integer>();
	//检索内容
	private Map<String, String> seleMap=new HashMap<String, String>();
	//修改的set内容
	private Map<String, Object> setMap=new HashMap<String, Object>();
	//设置排序内容
	private Map<String, Object> orderMap=new HashMap<String, Object>();
	//处理的类
	private String className;
	
	public ConditionsCommon(){
		pageMap.put("page", -1);
		pageMap.put("size", -1);
	}
	
	/**
	 * 添加等于条件
	 * @param columName 实体属性
	 * @param value 实体值
	 * 2015-12-4
	 * author njq
	 */
	public void addEqParam(String columName,Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.EQ, value);
	}
	
	/**
	 * 添加不等于条件
	 * @param columName 实体属性
	 * @param value 实体值
	 * 2015-12-29
	 * author njq
	 */
	public void addNotEqParam(String columName,Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.NOTEQ, value);
	}
	
	/**
	 * 添加in条件
	 * @param columName
	 * @param value
	 * 2015-12-10
	 * author njq
	 */
	public void addInParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.IN, value);
	}
	
	/**
	 * 添加not in 条件
	 * @param columName
	 * @param value
	 * 2016-1-6
	 * author njq
	 */
	public void addNotInParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.NOTIN, value);
	}
	
	/**
	 * 添加>条件
	 * @param columName
	 * @param value
	 * 2016-1-6
	 * author njq
	 */
	public void addGtParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.GT, value);
	}
	
	/**
	 * 添加>=条件
	 * @param columName
	 * @param value
	 * 2016-1-6
	 * author njq
	 */
	public void addGteParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.GTE, value);
	}
	
	/**
	 * 添加<条件
	 * @param columName
	 * @param value
	 * 2016-1-6
	 * author njq
	 */
	public void addLtParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.LT, value);
	}
	
	/**
	 * 添加<=条件
	 * @param columName
	 * @param value
	 * 2016-1-6
	 * author njq
	 */
	public void addLteParam(String columName, Object value){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.LTE, value);
	}
	
	/**
	 * 添加为null的条件
	 * @param columName
	 * 2016-1-6
	 * author njq
	 */
	public void addIsNullParam(String columName){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.ISNULL," ");
	}
	
	/**
	 * 添加不为null的条件
	 * @param columName
	 * 2016-1-6
	 * author njq
	 */
	public void addIsNotNullParam(String columName){
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.ISNOTNULL,"");
	}
	
	/**
	 * 添加between条件
	 * @param columName
	 * @param value1
	 * @param value2
	 * 2016-1-6
	 * author njq
	 */
	public void addBetweenParam(String columName,Object value1, Object value2){
		Object[] o={value1,value2};
		paramMap.put(columName+","+ConstantsCommon.Sql_Sign.BETWEEN,o);
	}
	
	/**
	 * 添加like模糊条件
	 * @param value
	 * @param columName
	 * 2016-1-6
	 * author njq
	 */
	public void addMoreColumLikeParam(String value,String...columName ){
		if(columName.length>1){
			String columString="CONCAT(";
			for(String name : columName){
				columString+=name+"!'-'!";
			}
			columString=columString.substring(0, columString.length()-5);
			columString+=")";
			paramMap.put(columString+","+ConstantsCommon.Sql_Sign.LIKE, value);
		}else{
			paramMap.put(columName[0]+","+ConstantsCommon.Sql_Sign.LIKE, value);
		}
	}
	
	public void addColumMoreLikeParam(String colum,String...values) {
		if(values.length>1) {
			String columString="";
			for(String value:values) {
				if(StringUtils.isEmpty(value)) {
					continue;
				}
				columString +=value+"^";
			}
			paramMap.put(colum+","+ConstantsCommon.Sql_Sign.LIKE,columString);
		}else {
			paramMap.put(colum+","+ConstantsCommon.Sql_Sign.LIKE,values[0]);
		}
	}
	/**
	 * 添加检索的字段
	 * @param columName 检索字段名称
	 * @param asName 别名
	 * 2015-12-8
	 * author njq
	 */
	public void addSeleParam(String columName , String asName){
		seleMap.put(columName, asName);
	}
	
	/**
	 * 添加set内容(内容中不包含字段名称)
	 * @param columName 字段名称
	 * @param value 字段内容
	 * 2015-12-10
	 * author njq
	 */
	public void addsetStringParam(String columName , String value){
		setMap.put(columName, value);
	}
	
	public void addsetObjectParam(String columName , Object value){
		setMap.put(columName, value);
	}
	
	public void addsetColumParam(String columName , String value){
		setMap.put(columName+"&", value);
	}
	/**
	 * 设置排序
	 * @param columName 排序字段
	 * @param asc （asc：升序   desc：降序）
	 * 2016-1-5
	 * author njq
	 */
	public void addSetOrderColum(String columName , String asc){
		orderMap.put(columName, asc);
	}
	
	/**
	 * 设置页数
	 * @param page
	 * @param size
	 * 2015-12-8
	 * author njq
	 */
	public void addPageParam(int page , int size ){
		pageMap.put("page", page);
		pageMap.put("size", size);
	}

	/**
	 * 移除条件
	 * @param key
	 * 2015-12-8
	 * author njq
	 */
	public void removeSeleParam(String key ){
		seleMap.remove(key);
	}
	
	/**
	 * 清空map
	 */
	public void resetSeleParam(){
	    seleMap.clear(); 
	}
	
	
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public Map<String, Integer> getPageMap() {
		return pageMap;
	}

	public Map<String, String> getSeleMap() {
		return seleMap;
	}

	public Map<String, Object> getSetMap() {
		return setMap;
	}

	public Map<String, Object> getOrderMap() {
		return orderMap;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}
