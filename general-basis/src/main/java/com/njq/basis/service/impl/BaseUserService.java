package com.njq.basis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseCode;
import com.njq.common.model.po.BaseUser;
import com.njq.common.util.encrypt.Md5Util;
import com.njq.common.util.string.StringUtil;

@Service
public class BaseUserService {

	@Resource
	private DaoCommon<BaseUser> userDao;
	@Resource
	private BaseCodeService codeService;
	@Resource
	private BaseLogService logService;
	@Resource
	private BaseRuleService ruleService;
	
	/**
	 * 查询所有用户
	 * @return
	 */
	@Cacheable("departCache")
	public List<BaseUser> queryAllUser() {
		return userDao.queryTByParam(null);
	}

	/**
	 * 查询所有用户信息（分页）
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseUser> queryAllUser(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addNotEqParam("id", 1L);
		if(paramMap.get("value")!=null){
			cc.addMoreColumLikeParam(paramMap.get("value").toString(), "account","userName","tel");
		}
		cc.addSetOrderColum("id","desc");
		return userDao.queryForPage(cc);
	}

	/**
	 * 保存用户信息
	 * @param user
	 * @param userId
	 * @param map
	 */
	public void saveUser(BaseUser user,Long userId,Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("account", user.getAccount());
		//查询数据库，判断当前账户是否已经存在
		List<BaseUser> list =  userDao.queryTByParam(cc);
		if(list.size()==0){
			//保存用户
			userDao.save(user);
			//给用户赋予默认角色
			ruleService.updateSetUserForDefault(user.getId(), map);
			logService.saveLog(userId, "新增", "用户表", "对行"+user.getId()+"进行新增");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "账户已经存在！");
			return;
		}
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 根据id检索出用户
	 * @param id
	 * @return
	 */
	public BaseUser queryUserById(Long id) {
		return userDao.queryTById(id);
	}

	/**
	 * 根据用户id检索出用户
	 * @param userId
	 * @return
	 */
	public BaseUser queryUserByUserId(Long userId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("userId", userId);
		List<BaseUser> list=userDao.queryTByParam(cc);
		return  list.size()==0? null:list.get(0);
	}

	/**
	 * 根据用户id修改用户信息
	 * @param user
	 * @param userId
	 * @param map
	 */
	public void updateUserById(BaseUser user,Long userId,Map<String, Object> map) {
		BaseUser ch = userDao.queryTById(user.getId());
		ch.setAccount(user.getAccount());
		ch.setTel(user.getTel());
		ch.setUserName(user.getUserName());
		ch.setEmail(user.getEmail());
		userDao.update(ch);
		//日志记录
		logService.saveLog(userId, "修改", "用户表", "对行"+user.getId()+"进行修改");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 检查用户账号密码是否正确
	 * @param account
	 * @param pwd
	 * @return
	 */
	public BaseUser queryUserByAccountAndPwd(String account, String pwd) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("account", account);
		cc.addEqParam("pwd", pwd);
		List<BaseUser> list = userDao.queryTByParam(cc);
		return list.size()>0?list.get(0):null;
	}

	/**
	 * 删除用户
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteUserById(Long[] ids,Long userId,Map<String, Object> map) {
		int num = userDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			logService.saveLog(userId, "删除", "用户表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "请检查数据是否正确，再进行操作！");
		}
	}

	/**
	 * 重置用户密码
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void updateReset(Long[] ids,Long userId, Map<String, Object> map) {
		BaseCode code = codeService.queryCodeById(2L);
		if(StringUtils.isNotBlank(code.getValue())){
			ConditionsCommon cc=new ConditionsCommon();
			cc.addInParam("id", ids);
			cc.addsetStringParam("pwd",code.getValue());
			userDao.update(cc);
			//日志记录
			logService.saveLog(userId, "重置密码", "用户表", "对用户ids"+StringUtil.LongToString(ids)+"进行密码重置");
			MessageCommon.getSuccessMap(map);
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "重置密码不能为空!");
		}
	}

	/**
	 * 修改用户密码
	 * @param pwd
	 * @param userId
	 * @param map
	 */
	public void updatePwd(String pwd, Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("id", userId);
		cc.addsetStringParam("pwd",Md5Util.MD5(pwd));
		userDao.update(cc);
		//日志记录
		logService.saveLog(userId, "修改密码", "用户表", "对用户id"+userId+"进行密码修改");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 修改用户手机
	 * @param tel
	 * @param userId
	 * @param map
	 */
	public void updateTel(String tel, Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("id", userId);
		cc.addsetStringParam("tel",tel);
		userDao.update(cc);
		//日志记录
		logService.saveLog(userId, "修改手机号", "用户表", "对用户id"+userId+"进行手机号修改");
		MessageCommon.getSuccessMap(map);
		
	}

	/**
	 * 修改用户邮箱
	 * @param email
	 * @param userId
	 * @param map
	 */
	public void updateEmail(String email, Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("id", userId);
		cc.addsetStringParam("email",email);
		userDao.update(cc);
		//日志记录
		logService.saveLog(userId, "修改邮箱", "用户表", "对用户id"+userId+"进行邮箱修改");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 修改用户头像
	 * @param picPlace
	 * @param userId
	 * @param map
	 */
	public void updatePic(String picPlace, Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("id", userId);
		cc.addsetStringParam("picPlace",picPlace);
		userDao.update(cc);
		//日志记录
		logService.saveLog(userId, "修改邮箱", "用户表", "对用户id"+userId+"进行头像修改");
		MessageCommon.getSuccessMap(map);
	}
	
	
	
	
}
