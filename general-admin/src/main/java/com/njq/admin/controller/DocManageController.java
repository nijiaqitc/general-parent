package com.njq.admin.controller;

/**
 * 文章管理接口
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.TbkDoc;
import com.njq.tbk.service.TbkDocService;

@RequestMapping("admin/docManage")
@Controller
public class DocManageController {

	@Resource
	public TbkDocService tbkDocService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "docManage", method = RequestMethod.GET)
	public String docTypeManage(Model model) {
		return "back/docManage/docTypeManage";
	}

	@RequestMapping(value = "tbkDocManage", method = RequestMethod.GET)
	public String tbkDocManage(Model model) {
		return "back/docManage/docManage";
	}

	@RequestMapping(value = "yxlDocManage", method = RequestMethod.GET)
	public String yxlDocManage(Model model) {
		return "back/docManage/yxlDocManage";
	}

	/**
	 * 文章预览
	 * 
	 * @param model
	 * @param text
	 * @param general
	 * @param tips
	 * @param title
	 * @param session
	 * @return 2016-3-29 author njq
	 */
	@RequestMapping(value = "docView", method = RequestMethod.GET)
	public String docView(Model model) {
		return "back/docManage/docView";
	}

	/**
	 * 查询文章列表（分页）
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2016-4-8 author njq
	 */
	@RequestMapping(value = "getDocList", method = RequestMethod.POST)
	public @ResponseBody PageList<TbkDoc> getDocList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<TbkDoc> docList = tbkDocService.queryAllDoc(map, UserCommon.getUserId(), page, size);
		return docList;
	}

	/**
	 * 根据id删除文章
	 * 
	 * @param ids
	 * @param model
	 * @return 2016-4-8 author njq
	 */
	@RequestMapping(value = "delDoc", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delDoc(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		tbkDocService.deleteDocById(ids, UserCommon.getUserId(), map);
		return map;
	}

}
