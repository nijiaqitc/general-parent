package com.njq.grab.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.grab.service.impl.GrabService;
import com.njq.grab.service.impl.GrabUrlInfoService;

@RequestMapping("grab")
@Controller
public class GrabController {
	private static final Logger logger = LoggerFactory.getLogger(GrabController.class);
	@Autowired
	public GrabService grabService;
	@Autowired
	public GrabUrlInfoService grabUrlInfoService;
	@Resource
	private BaseTitleService baseTitleService;


	@RequestMapping("loadMenu")
	public void loadMenu(Model model) {
		grabService.loadMenuJobTask();
	}

	@RequestMapping("config")
	public String grabPage(Model model) {
		model.addAttribute("channelList", ChannelType.getChannelValueList());
		return "/grab/grabUrl";
	}

	@ResponseBody
	@RequestMapping("saveAndGrab")
	public String saveAndGrab(String title, String url, String docId, 
			String channel, String type, String tips,String reload) {
		try {
			grabService.grabOperation(title, url, docId, channel, type, tips);
		} catch (Exception e) {
			logger.info("保存活动出错", e);
			return e.getMessage();
		}
		return "操作成功！";
	}

	@RequestMapping("loadMenuPage")
	public String loadMenuPage() {
		return "/grab/loadMenu";
	}
	
	@ResponseBody
	@RequestMapping(value = "queryMenuConfig", method = RequestMethod.POST)
	public String queryMenuConfig(@RequestParam(value = "pageIndex", required = true) String pageIndex) {
		GrabUrlInfo info = grabUrlInfoService.getUrlInfoByIndexPage(pageIndex);
		if (info == null) {
			return "不存在配置，无法读取";
		} else {
			return info.getMenuUrl();
		}
	}

	@ResponseBody
	@RequestMapping("saveNewMenu")
	public String saveNewMenu(@RequestParam(value = "pageIndex", required = true) String pageIndex,
			@RequestParam(value = "menuUrl", required = true) String menuUrl,
			@RequestParam(value = "typeName", required = true) String typeName) {
		try {
			grabUrlInfoService.saveUrlInfo(pageIndex, menuUrl,typeName);			
		} catch (Exception e) {
			logger.info("保存活动出错", e);
			return e.getMessage();
		}
		return "操作成功！";
	}

}
