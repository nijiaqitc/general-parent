/**
 * 公用类，存放通用的方法模板，可以直接拿来用
 */
package com.njq.common.base.other;

import java.util.HashMap;
import java.util.Map;

public class MessageCommon {

	
    /**
     * 设置公共的返回值map
     * @return
     */
    public static Map<String, Object> getSuccessMap(){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("state", "1");
        map.put("message", "操作成功！");
        return map;
    }
    
	/**
	 * 设置公用的成功map
	 * @param map
	 * 2015-12-3
	 * author njq
	 */
	public static void getSuccessMap(Map<String, Object> map){
		map.put("state", "1");
		map.put("message", "操作成功！");
	}
	
	/**
	 * 设置公用的失败map
	 * @param map
	 * 2015-12-3
	 * author njq
	 */
	public static void getFalseMap(Map<String, Object> map){
		map.put("state", "0");
		map.put("message", "操作失败！");
	}
	
	/**
	 * 返回操作失败的消息
	 * @param map
	 * @param msg
	 */
	public static void getFalseMap(Map<String, Object> map,String msg){
		map.put("state", "0");
		map.put("message", msg);
	}
	
	/**
	 * 返回操作失败的消息
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> getFalseMap(String msg){
	    Map<String, Object> map=new HashMap<String, Object>();
	    map.put("state", "0");
	    map.put("message",msg);
	    return map;
	}
}
