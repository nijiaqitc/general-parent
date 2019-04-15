package com.njq.start.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.njq.zxgj.service.TestService;
import com.njq.zxgj.service.TtService;

@RequestMapping("thymeleaf")
@Controller
public class Test2Controller {
	@Resource
	public TestService testService;
	public TtService ttService;

	@RequestMapping("tftf")
	@ResponseBody
	public String testValue(){
		System.out.print(ttService);
		return "";
	}

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
		return "thymeleaf/thytest";
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
	
	
	
	
	@RequestMapping("ffff5")
	public String ffff5(){
		return "test1";
	}
	
	@RequestMapping("ffff6")
	public String ffff6(){
		return "test2";
	}
}
