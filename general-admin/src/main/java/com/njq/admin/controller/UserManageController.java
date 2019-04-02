package com.njq.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.admin.common.UserCommon;
import com.njq.basis.service.impl.BaseUserService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.BaseUser;
import com.njq.common.util.encrypt.Md5Util;

@RequestMapping("userManage")
@Controller
public class UserManageController {

	@Resource
	private BaseUserService userService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "userManage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/userManage/userManage";
	}

	@RequestMapping(value = "testList", method = RequestMethod.GET)
	public @ResponseBody List<BaseUser> testList() {
//		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户的分页列表
		List<BaseUser> userList = userService.queryAllUser();
		return userList;
	}

	/**
	 * 获取用户
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "getUserList", method = RequestMethod.POST)
	public @ResponseBody PageList<BaseUser> getUserList(Model model, String searchValue,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(searchValue)) {
			map.put("value", searchValue);
		}
		// 获取用户的分页列表
		PageList<BaseUser> userList = userService.queryAllUser(map, page, size);
		return userList;
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateUser(@Valid BaseUser user, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		// 对用户进行修改
		userService.updateUserById(user, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param errors
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveUser(@Valid BaseUser user, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		if (StringUtils.isEmpty(user.getPwd())) {
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		user.setPwd(Md5Util.getMD5Password(user.getPwd()));
		user.setCreateBy(UserCommon.getUserId());
		user.setModiBy(UserCommon.getUserId());
		user.setCreateDate(new Date());
		user.setStatus(ConstantsCommon.Del_Status.YES);
		// 对用户进行保存
		userService.saveUser(user, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "delUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delUser(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		userService.deleteUserById(ids, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param ids
	 * @param model
	 * @return 2015-12-25 author njq
	 */
	@RequestMapping(value = "resetPwd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> resetPwd(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		userService.updateReset(ids, UserCommon.getUserId(), map);
		return map;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
