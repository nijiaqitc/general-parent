package com.njq.start.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.njq.zxgj.service.TestService;

@RequestMapping("thymeleaf")
@Controller
public class Test2Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Test2Controller.class);
	@Resource
	public TestService testService;
	
	@RequestMapping("tttest")
	public ModelAndView ttt() {
		
		ModelAndView mv = new ModelAndView("thymeleaf/acb");
		return mv;
	}
	
	
	@RequestMapping("test2")
	public String test2(HttpServletRequest request,HttpServletResponse response){
		try {
//			request.getRequestDispatcher("ffff").forward(request, response);
//			testService.saveTss();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "thymeleaf/acb";
	}
	
	
	@RequestMapping("ffff")
	public String ffff(){
		try {
//			testService.saveTss();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "test111";
	}
	
	
	@RequestMapping("ffff1")
	public String ffff1(){
		try {
//			testService.saveTss();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "test";
	}
	
	@RequestMapping("ffff2")
	public String ffff2(){
		try {
//			testService.saveTss();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "thytest";
	}
	
	@RequestMapping("freetest")
	public String freetest(){
		try {
//			testService.saveTss();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "freetest";
	}
}
