package com.njq.zxgj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njq.zxgj.service.TestService;

@Controller
public class TestA {

	@Autowired
	private TestService testService;
	
	@RequestMapping("tsst")
	public String testB(){
		testService.queryList();
		System.out.println("BBBBBBBBBBBB"); 
		return "test";
	}
}
