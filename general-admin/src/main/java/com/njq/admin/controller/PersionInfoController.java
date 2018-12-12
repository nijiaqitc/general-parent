package com.njq.admin.controller;

/**
 * 个人信息接口
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("persionInfo")
@Controller
public class PersionInfoController {

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "persionInfo", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/persionInfo/persionInfo";
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
