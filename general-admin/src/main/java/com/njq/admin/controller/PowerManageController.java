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
import com.njq.basis.service.impl.BaseChannelService;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseChannel;
import com.njq.common.model.vo.ChannelVO;

@RequestMapping("admin/powerManage")
@Controller
public class PowerManageController {

	@Resource
	private BaseChannelService channelService;

	/**
	 * 跳转到后台主页
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "powerManage", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/powerManage/powerManage";
	}

	/**
	 * 获取栏目列表
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2015-12-7 author njq
	 */
	@RequestMapping(value = "getChannelList", method = RequestMethod.GET)
	public @ResponseBody PageList<BaseChannel> getChannelList(Model model,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取栏目的分页列表
		PageList<BaseChannel> channelList = channelService.queryAllChannel(map, page, size);
		return channelList;
	}

	/**
	 * 获取父级（只有1级）
	 * 
	 * @return 2015-12-15 author njq
	 */
	@RequestMapping(value = "getParentsList", method = RequestMethod.POST)
	public @ResponseBody List<BaseChannel> getParentsList() {
		return channelService.queryParentsChannel();
	}

	/**
	 * 修改栏目
	 * 
	 * @param channel
	 * @param model
	 * @return 2015-12-7 author njq
	 */
	@RequestMapping(value = "updateChannel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateChannel(@Valid BaseChannel channel, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		// 对栏目进行修改
		channelService.updateChannelById(channel, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 保存栏目
	 * 
	 * @param channel
	 * @param model
	 * @return 2015-12-7 author njq
	 */
	@RequestMapping(value = "saveChannel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveChannel(@Valid BaseChannel channel, Errors errors, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			for (FieldError file : errors.getFieldErrors()) {
				map.put(file.getField(), file.getDefaultMessage());
			}
			map.put("state", "0");
			map.put("message", "输入信息有误，请检查后再提交！");
			return map;
		}
		channel.setCreateBy(UserCommon.getUserId());
		channel.setModiBy(UserCommon.getUserId());
		channel.setCreateDate(new Date());
		channel.setStatus(ConstantsCommon.Del_Status.YES);
		channel.setApply(ConstantsCommon.Del_Status.NO);
		// 对栏目进行保存
		channelService.saveChannel(channel, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 删除栏目接口
	 * 
	 * @param ids   删除id的集合
	 * @param model
	 * @return 2015-12-10 author njq
	 */
	@RequestMapping(value = "delChannel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delChannel(@RequestParam Long[] ids, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		channelService.deleteChannelById(ids, UserCommon.getUserId(), map);
		return map;
	}

	/**
	 * 根据用户id，获取用户对应的所拥有的角色和未拥有的角色
	 * 
	 * @param model
	 * @return 2015-12-12 author njq
	 */
	@RequestMapping(value = "getUserChannelList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUserChannelList(@RequestParam Long userId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询拥有的权限
		List<ChannelVO> haveList = channelService.queryUserChannelConfigByUser(userId, map);
		// 查询未拥有的权限
		List<ChannelVO> notHaveList = channelService.queryUserChannelConfigNotByUser(userId, map);
		map.put("haveList", haveList);
		map.put("notHaveList", notHaveList);
		MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 根据角色id查询角色所拥有的角色和未拥有的角色
	 * 
	 * @param ruleId
	 * @param model
	 * @return 2015-12-15 author njq
	 */
	@RequestMapping(value = "getRuleChannelList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRuleChannelList(@RequestParam Long ruleId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询拥有的权限
		List<ChannelVO> haveList = channelService.queryRuleChannelConfigByUser(ruleId, map);
		// 查询未拥有的权限
		List<ChannelVO> notHaveList = channelService.queryRuleChannelConfigNotByUser(ruleId, map);
		map.put("haveList", haveList);
		map.put("notHaveList", notHaveList);
		MessageCommon.getSuccessMap(map);
		return map;
	}

	/**
	 * 为用户重新赋予权限
	 * 
	 * @param userId  用户id
	 * @param ruleIds 角色id集合
	 * @param model
	 * @return 2015-12-14 author njq
	 */
	@RequestMapping(value = "updateUserChannelConfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateUserChannelConfig(@RequestParam Long userId,
			@RequestParam Long[] channelIds, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		channelService.updateUserChannelConfig(channelIds, userId, map);
		return map;
	}

	/**
	 * 为角儿重新赋予权限
	 * 
	 * @param ruleId
	 * @param channelIds
	 * @param model
	 * @return 2015-12-15 author njq
	 */
	@RequestMapping(value = "updateRuleChannelConfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateRuleChannelConfig(@RequestParam Long ruleId,
			@RequestParam Long[] channelIds, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		channelService.updateRuleChannelConfig(channelIds, UserCommon.getUserId(), ruleId, map);
		return map;
	}

	/**
	 * 设置权限为可用或不可用
	 * 
	 * @param channelIds
	 * @param type
	 * @param model
	 * @return 2016-1-4 author njq
	 */
	@RequestMapping(value = "applyChannel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> applyChannel(@RequestParam Long[] channelIds, @RequestParam String type,
			Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		channelService.updateChannel(channelIds, UserCommon.getUserId(), type, map);
		return map;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
