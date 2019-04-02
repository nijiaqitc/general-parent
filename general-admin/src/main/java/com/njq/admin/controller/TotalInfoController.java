package com.njq.admin.controller;

/**
 * 信息总览接口
 */
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.service.DocChartsService;

@RequestMapping("admin/totalInfo")
@Controller
public class TotalInfoController {

	@Resource
	private DocChartsService baseChartsService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "backIndex", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/totalInfo/totalInfo";
	}

	@RequestMapping(value = "jcrop", method = RequestMethod.GET)
	public String jcrop(Model model) {
		return "back/totalInfo/jcrop";
	}

	@RequestMapping(value = "upPic", method = RequestMethod.GET)
	public String jumpToUpPicPage(Model model) {
		return "back/totalInfo/upPic";
	}

	/**
	 * 发表系列文章图表
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryXlCharts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<String>> queryXlCharts() {
		Map<String, List<String>> m = baseChartsService.queryyxlDocCharts();
		return m;
	}

	/**
	 * 发表小说统计图
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryXsCharts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<String>> queryXsCharts() {
		Map<String, List<String>> m = baseChartsService.queryXsCharts();
		return m;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo2";
	}
}
