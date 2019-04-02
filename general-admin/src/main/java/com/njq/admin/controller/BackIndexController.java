package com.njq.admin.controller;

/**
 * 后台主页接口
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("admin/backIndex")
@Controller
public class BackIndexController {

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "backIndex", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/backIndex/backIndex";
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
