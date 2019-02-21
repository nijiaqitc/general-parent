package com.njq.basis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseRule;
import com.njq.common.model.po.BaseUserRuleConfig;
import com.njq.common.model.vo.RuleVO;
import com.njq.common.util.string.StringUtil;
@SuppressWarnings("unchecked")
@Service
public class BaseRuleService {
	@Resource
	private DaoCommon<BaseRule> ruleDao;
	@Resource
	private DaoCommon<BaseUserRuleConfig> userRuleConfigDao;
	
	@Resource
	private BaseLogService logService;
	
	/**
	 * 查询所有角色(分页)
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseRule> queryAllRule(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		return ruleDao.queryForPage(cc);
	}

	/**
	 * 查询用户所拥有的角色
	 * @param id
	 * @return
	 */
	public BaseRule queryRuleById(Long id) {
		return ruleDao.queryTById(id);
	}

	/**
	 * 保存角色
	 * @param rule
	 * @param userId
	 * @param map
	 */
	public void saveRule(BaseRule rule,Long userId, Map<String, Object> map) {
		ruleDao.save(rule);
		//日志记录
		logService.saveLog(userId, "新增", "角色表", "对行"+rule.getId()+"进行增加");
		MessageCommon.getSuccessMap(map);
		
	}

	/**
	 * 根据id修改角色
	 * @param rule
	 * @param userId
	 * @param map
	 */
	public void updateRuleById(BaseRule rule,Long userId, Map<String, Object> map) {
		BaseRule ch = ruleDao.queryTById(rule.getId());
		ch.setRuleName(rule.getRuleName());
		ch.setColumDesc(rule.getColumDesc());
		ruleDao.update(ch);
		//日志记录
		logService.saveLog(userId, "修改", "角色表", "对行"+rule.getId()+"进行修改");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 删除角色
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteRuleById(Long[] ids,Long userId, Map<String, Object> map) {
		int num = ruleDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			logService.saveLog(userId, "删除", "角色表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "请检查数据是否正确，再进行操作！");
		}
	}

	
	/**
	 * 查询用户所拥有的角色
	 * @param userId
	 * @param paramMap
	 * @return
	 */
	public List<RuleVO> queryUserRuleConfigByUser(Long userId,
			Map<String, Object> paramMap) {
		paramMap.put("userId", userId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.RuleVO(r.id,r.ruleName) from  BaseUserRuleConfig c , BaseRule r  where c.status=1 and r.status=1 and c.ruleId=r.id  and c.userId=:userId";
		List<RuleVO> ruleList = (List<RuleVO>)ruleDao.queryHqlByParam(hql, paramMap);
		return ruleList;
	}

	/**
	 * 查询用户未拥有的角色
	 * @param userId
	 * @param paramMap
	 * @return
	 */
	public List<RuleVO> queryUserRuleConfigNotByUser(Long userId,
			Map<String, Object> paramMap) {
		paramMap.put("userId", userId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.RuleVO(r.id,r.ruleName) from  BaseRule r  where r.status=1 and r.id not in (select ruleId from BaseUserRuleConfig where status=1 and userId=:userId ) ";
		List<RuleVO> ruleList = (List<RuleVO>)ruleDao.queryHqlByParam(hql, paramMap);
		return ruleList;
	}

	/**
	 * 给用户赋予角色
	 * @param ruleId
	 * @param userId
	 * @param paramMap
	 */
	public void updateUserRuleConfig(Long[] ruleId, Long userId,
			Map<String, Object> paramMap) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addsetStringParam("status", ConstantsCommon.Del_Status.NO+"");
		cc.addEqParam("userId", userId);
		//先删除用户所有的角色
		userRuleConfigDao.update(cc);
		/*
		 * 循环插入数据
		 */
		for(Long id:ruleId){
			BaseUserRuleConfig config=new BaseUserRuleConfig();
			config.setCreateBy(userId);
			config.setCreateDate(new Date());
			config.setModiBy(userId);
			config.setRuleId(id);
			config.setUserId(userId);
			userRuleConfigDao.save(config);
		}
		//日志记录
		logService.saveLog(userId, "新增", "角色表", "对行"+StringUtil.LongToString(ruleId)+"进行新增");
		MessageCommon.getSuccessMap(paramMap);
	}

	/**
	 * 设置默认角色
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void updateSetDefault(Long[] ids,Long userId,Map<String, Object> map) {
		ConditionsCommon cc1=new ConditionsCommon();
		cc1.addsetStringParam("isDefault", "0");
		ruleDao.update(cc1);
		ConditionsCommon cc=new ConditionsCommon();
		cc.addsetStringParam("isDefault", "1");
		cc.addInParam("id", ids);
		ruleDao.update(cc);
		//日志记录
		logService.saveLog(userId, "新增", "角色表", "对行"+StringUtil.LongToString(ids)+"设为默认角色");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 给用户赋予默认角色
	 * @param userId
	 * @param map
	 */
	public void updateSetUserForDefault(Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("isDefault", 1);
		List<BaseRule> list = ruleDao.queryTByParam(cc);
		if(list.size()>0){
			BaseUserRuleConfig config=new BaseUserRuleConfig();
			config.setCreateBy(ConstantsCommon.Oper_User.NO_USER);
			config.setCreateDate(new Date());
			config.setStatus(ConstantsCommon.Del_Status.YES);
			config.setModiBy(ConstantsCommon.Oper_User.NO_USER);
			config.setRuleId(list.get(0).getId());
			config.setUserId(userId);
			userRuleConfigDao.save(config);
			//日志记录
			logService.saveLog(ConstantsCommon.Oper_User.NO_USER, "新增", "角色配置表", "对用户"+userId+"赋予默认角色");
		}
	}
	
}
