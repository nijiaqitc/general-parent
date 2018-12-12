/**
 * 公用的缓存类
 */
package com.njq.common.base.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisCommon {

	public static StringRedisTemplate redisTemplate;
	
	/**
	 * 填充字符串
	 * @param key
	 * @param value
	 */
	public static void setString(String key , String value){
		redisTemplate.opsForValue().set(key, value);
	}
	/**
	 * 获取字符串
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		return redisTemplate.opsForValue().get(key);
		
	}
	/**
	 * 填充含过期时间的字符串
	 * @param key
	 * @param value
	 * @param time 分钟
	 */
	public static void setString(String key , String value,long time){
		redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public static void delObject(String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 对缓存中的数据进行增长
	 * @param key
	 * @param value
	 */
	public static void upIncreValue(String key , long value){
		redisTemplate.opsForValue().increment(key, value);
	}
	
	/**
	 * 修改缓存时间
	 * @param key
	 * @param time
	 */
	public static void upValueTime(String key , long time){
		redisTemplate.expire(key, time, TimeUnit.MINUTES);
	}
	
	/**
	 * 往缓存中填充map
	 * @param key
	 * @param map
	 */
	public static void setHash(String key , Map<String, Object> map){
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	/**
	 * 获取值
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Object getString(String key1 , String key2){
		return redisTemplate.opsForHash().get(key1,key2);
	}
	
	/**
	 * 保存值
	 * @param key1
	 * @param key2
	 * @param key3
	 */
	public static void setHashString(String key1 ,String key2,String value){
		redisTemplate.opsForHash().put(key1, key2, value);
	}
	
	/**
	 * 往缓存中填充单个对象
	 * @param key
	 * @param object
	 */
	public static void setObject(String key , Object object){
		redisTemplate.opsForHash().put("singleObject", key, object);
	}
	
	/**
	 * 获取单个对象
	 * @param key
	 * @return
	 */
	public static Object getObject(String key){
		return redisTemplate.opsForHash().get("singleObject", key);
	}
	
}
