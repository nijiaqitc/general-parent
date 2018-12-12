package com.njq.common.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.njq.common.base.redis.RedisCommon;

@Configuration
public class RedisInitConfig {
	@Autowired
	private StringRedisTemplate redisTemplate;
//	@Autowired
//	private RedisTemplate redis;
	public RedisInitConfig() {
		RedisCommon.redisTemplate = redisTemplate;
	}
}
