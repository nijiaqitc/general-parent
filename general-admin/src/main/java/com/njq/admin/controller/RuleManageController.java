package com.njq.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.njq.basis.service.impl.BaseRuleService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseRule;
import com.njq.common.model.vo.RuleVO;

@RequestMapping("ruleManage")
@Controller
public class RuleManageController {

	@Resource
	private BaseRuleService ruleService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "ruleManage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/ruleManage/ruleManage";
	}

	/**
	 * 获取角色
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "getRuleList", method = RequestMethod.GET)
	public @ResponseBody PageList<BaseRule> getRuleList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取角色的分页列表
		PageList<BaseRule> ruleList = ruleService.queryAllRule(map, page, size);
		return ruleList;
	}

	/**
	 * 根据用户id，获取用户对应的所拥有的角色和未拥有的角色
	 * 
	 * @param model
	 * @return 2015-12-12 author njq
	 */
	@RequestMapping(value = "getUserRuleList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUserRuleList(@RequestParam Long userId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询拥有的角色
		List<RuleVO> haveList = ruleService.queryUserRuleConfigByUser(userId, map);
		// 查询未拥有的角色
		List<RuleVO> notHaveList = ruleService.queryUserRuleConfigNotByUser(userId, map);
		map.put("haveList", haveList);
		map.put("notHaveList", notHaveList);
		MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 为用户重新赋予角色
	 * 
	 * @param userId  用户id
	 * @param ruleIds 角色id集合
	 * @param model
	 * @return 2015-12-14 author njq
	 */
	@RequestMapping(value = "updateUserRuleConfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateUserRuleConfig(@RequestParam Long userId,
			@RequestParam Long[] ruleIds, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		ruleService.updateUserRuleConfig(ruleIds, userId, map);
		return map;
	}

	/**
	 * 修改角色
	 * 
	 * @param rule
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "updateRule", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateRule(@Valid BaseRule rule, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		// 对角色进行修改
		ruleService.updateRuleById(rule, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 保存角色
	 * 
	 * @param rule
	 * @param errors
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "saveRule", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRule(@Valid BaseRule rule, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		rule.setChannelId(UserCommon.getUserId());
		rule.setCreateBy(UserCommon.getUserId());
		rule.setModiBy(UserCommon.getUserId());
		rule.setCreateDate(new Date());
		// 对栏目进行保存
		ruleService.saveRule(rule, UserCommon.getUserId(), map);
		return map;
	}

	@RequestMapping(value = "delRule", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delRule(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		ruleService.deleteRuleById(ids, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 设置默认角色
	 * 
	 * @param ids
	 * @param model
	 * @return 2015-12-11 author njq
	 */
	@RequestMapping(value = "setDefault", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setDefault(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (ids.length > 1) {
			MessageCommon.getFalseMap(map);
			map.put("message", "只能设置一个角色为默认角色！");
			return map;
		}
		ruleService.updateSetDefault(ids, UserCommon.getUserId(), map);
		return map;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
