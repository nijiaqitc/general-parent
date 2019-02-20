package com.njq.admin.controller;

/**
 * 类型管理接口
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
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.TbkType;
import com.njq.tbk.service.TbkTypeService;

@RequestMapping("typeManage")
@Controller
public class TypeManageController {

	@Resource
	public TbkTypeService tbkTypeService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "typeManage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		model.addAttribute("userId", UserCommon.getUserId());
		model.addAttribute("parentList", tbkTypeService.queryParentTbktype(ConstantsCommon.Org_Id.FIRST_ORG_ID));
		return "back/typeManage/typeManage";
	}

	/**
	 * 获取类型
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "getTypeList", method = RequestMethod.GET)
	public @ResponseBody PageList<TbkType> getChannelList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<TbkType> channelList = tbkTypeService.queryAllTbktype(map, UserCommon.getUserId(), page, size);
		return channelList;
	}

	/**
	 * 修改类型
	 * 
	 * @param channel
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "updateType", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateType(@Valid TbkType type, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		// 对类型进行修改
		tbkTypeService.updateTbktypeById(type, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 保存类型
	 * 
	 * @param channel
	 * @param errors
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "saveType", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveChannel(@Valid TbkType type, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		/*
		 * 如果用户是超级管理员，那么创建的类型只能是顶级类型 如果用户不是超级管理员，但创建了顶级类型则将其该成指定类型下的子类型
		 */
		if (UserCommon.checkUserId()) {
			type.setParentId(ConstantsCommon.Org_Id.FIRST_ORG_ID);
		} else {
			if (type.getParentId().equals(ConstantsCommon.Org_Id.FIRST_ORG_ID)) {
				type.setParentId(ConstantsCommon.Org_Id.SECOND_ORG_ID);
			}
		}
		/*
		 * 如果用户创建的类型未设置父级类型，那么默认使用指定类型作为其父级类型
		 */
		if (type.getParentId() == null) {
			type.setParentId(ConstantsCommon.Org_Id.SECOND_ORG_ID);
		}
		type.setCreateBy(UserCommon.getUserId());
		type.setModiBy(UserCommon.getUserId());
		type.setCreateDate(new Date());
		type.setInTurn(ConstantsCommon.Org_Id.IN_TURN);
		// 对类型进行保存
		tbkTypeService.saveTbktype(type, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 在发表文章里添加类型
	 * 
	 * @param typeName 类型名称
	 * @return
	 */
	@RequestMapping(value = "saveDocType", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveDocType(@RequestParam(required = true) String typeName,
			@RequestParam(required = true) Long parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		TbkType type = new TbkType();
		/*
		 * 如果用户是超级管理员，那么创建的类型只能是顶级类型 如果用户不是超级管理员，但创建了顶级类型则将其该成指定类型下的子类型
		 */
		if (UserCommon.checkUserId()) {
			type.setParentId(ConstantsCommon.Org_Id.FIRST_ORG_ID);
		} else {
			if (parentId.equals(ConstantsCommon.Org_Id.FIRST_ORG_ID)) {
				type.setParentId(ConstantsCommon.Org_Id.SECOND_ORG_ID);
			}
		}
		/*
		 * 如果用户创建的类型未设置父级类型，那么默认使用指定类型作为其父级类型
		 */
		if (parentId == null) {
			type.setParentId(ConstantsCommon.Org_Id.SECOND_ORG_ID);
		} else {
			type.setParentId(parentId);
		}
		type.setName(typeName);
		type.setInTurn(ConstantsCommon.Org_Id.IN_TURN);
		type.setCreateBy(UserCommon.getUserId());
		type.setCreateDate(new Date());
		type.setModiBy(UserCommon.getUserId());
		// 对类型进行保存
		tbkTypeService.saveTbktype(type, UserCommon.getUserId(), map);
		map.put("type", type);
		return map;
	}

	/**
	 * 删除类型
	 * 
	 * @param ids
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "delType", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delType(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		tbkTypeService.deleteTbktypeById(ids, UserCommon.getUserId(), map);
		return map;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
