package com.njq.start.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njq.yxl.cache.YxlDocListReader;

@RequestMapping("freemarker")
@Controller
public class Test3Controller {
	private static final Logger logger = LoggerFactory.getLogger(Test3Controller.class);
	@Autowired
    protected RedisTemplate redisTemplate;
	
	@Autowired
	private YxlDocListReader yxlReader;
	@RequestMapping("fafafa")
	public String test() {
		System.out.println(redisTemplate);
		System.out.println("122");
		return "";
	}
	
	@RequestMapping("fse")
	public String ttf() {
		yxlReader.get("ss");
		logger.info("222222222222222222222");
		return "";
	}
}
