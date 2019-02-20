package com.njq.admin.controller;

/**
 * 数据字典接口
 */
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseCodeService;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.BaseCode;

@RequestMapping("dataCode")
@Controller
public class DataCodeController {

	@Resource
	private BaseCodeService codeService;

	/**
	 * 跳转到数据字典
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "dataCode", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/dataCode/dataCode";
	}

	/**
	 * 跳转到图标库
	 * 
	 * @param model
	 * @return 2016-1-5 author njq
	 */
	@RequestMapping(value = "icons", method = RequestMethod.GET)
	public String jumpToIcon(Model model) {
		return "back/sysManage/dataCode/icons";
	}

	/**
	 * 获取数据字典分页
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "getDataList", method = RequestMethod.GET)
	public @ResponseBody PageList<BaseCode> getDataList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<BaseCode> codeList = codeService.queryAllCode(map, page, size);
		return codeList;
	}

	/**
	 * 获取父级下的子集
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @param id    父级id
	 * @return 2015-12-21 author njq
	 */
	@RequestMapping(value = "getCodeList", method = RequestMethod.POST)
	public @ResponseBody PageList<BaseCode> getCodeList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size, @RequestParam Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<BaseCode> codeList = codeService.queryChildrenCodeList(map, id, page, size);
		return codeList;
	}

	/**
	 * 修改字典数据
	 * 
	 * @param code
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "updateData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateData(BaseCode code, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 对数据字典进行修改
		codeService.updateCodeById(code, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 保存字典
	 * 
	 * @param code
	 * @param errors
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveData(@Valid BaseCode code, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		code.setCreateBy(UserCommon.getUserId());
		code.setModiBy(UserCommon.getUserId());
		code.setCreateDate(new Date());
		// 对栏目进行保存
		codeService.saveCode(code, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 删除数据字典
	 * 
	 * @param ids
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "delData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delData(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		codeService.deleteCodeById(ids, UserCommon.getUserId(), map);
		return map;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
