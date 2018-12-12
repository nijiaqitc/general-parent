package com.njq.basis.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseChannel;
import com.njq.common.model.po.BaseRuleChannelConfig;
import com.njq.common.model.po.BaseUserChannelConfig;
import com.njq.common.model.po.BaseUserRuleConfig;
import com.njq.common.model.vo.ChannelVO;
import com.njq.common.util.string.StringUtil;
@SuppressWarnings("unchecked")
@Service
public class BaseChannelService  {

	@Resource
	private DaoCommon<BaseChannel> channelDao;
	@Resource
	private DaoCommon<BaseUserChannelConfig> userChannelConfigDao;
	@Resource
	private DaoCommon<BaseRuleChannelConfig> ruleChannelConfigDao;
	@Resource
	private DaoCommon<BaseUserRuleConfig> userRuleConfigDao;
	
	@Resource
	private BaseLogService logService;
	
	
	/**
	 * 查询所有栏目(分页)
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseChannel> queryAllChannel(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		return channelDao.queryForPage(cc);
	}

	
	/**
	 * 查询父级
	 * @return
	 */
	public List<BaseChannel> queryParantsChannel() {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parantId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return channelDao.queryTByParam(cc);
	}


	/**
	 * 根据id检索出栏目
	 * @param id
	 * @return
	 */
	public BaseChannel queryChannelById(Long id) {
		return channelDao.queryTById(id);
	}

	/**
	 * 保存栏目
	 * @param channel
	 * @param userId
	 * @param map
	 */
	public void saveChannel(BaseChannel channel,Long userId, Map<String, Object> map) {
		channelDao.save(channel);
		//日志记录
		logService.saveLog(userId, "新增", "栏目表", "对行"+channel.getId()+"进行增加");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 根据id修改栏目
	 * @param channel
	 * @param userId
	 * @param map
	 */
	public void updateChannelById(BaseChannel channel,Long userId, Map<String, Object> map) {
		BaseChannel ch = channelDao.queryTById(channel.getId());
		ch.setChannelName(channel.getChannelName());
		ch.setParantId(channel.getParantId());
		ch.setIcon(channel.getIcon());
		ch.setInTurn(channel.getInTurn());
		ch.setUrl(channel.getUrl());
		ch.setColumDesc(channel.getColumDesc());
		channelDao.update(ch);
		//日志记录
		logService.saveLog(userId, "修改", "栏目表", "对行"+channel.getId()+"进行修改");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 删除栏目
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteChannelById(Long[] ids,Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addInParam("channelId", ids);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		//检查是否存在被用户使用的权限
		int checkNum1 = userChannelConfigDao.queryForCount(cc);
		//检查是否存在被角色使用的权限
		int checkNum2 = ruleChannelConfigDao.queryForCount(cc);
		/*
		 * 判断是否有权限被使用
		 */
		if(checkNum1>0 || checkNum2>0){
			MessageCommon.getFalseMap(map);
			map.put("message", "有权限正在被使用，请解除权限后再进行操作！");
		}else{
			int num = channelDao.delT(ids);
			if(num>0){
				MessageCommon.getSuccessMap(map);
				//日志记录
				logService.saveLog(userId, "删除", "栏目表", "对行"+StringUtil.LongToString(ids)+"进行删除");
			}else{
				MessageCommon.getFalseMap(map);
				map.put("message", "请检查数据是否正确，再进行操作！");
			}
		}
	}

	/**
	 * 查询用户所拥有的权限
	 * @param userId
	 * @param paramMap
	 * @return
	 */
	public List<ChannelVO> queryUserChannelConfigByUser(Long userId,
			Map<String, Object> paramMap) {
		paramMap.put("userId", userId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.ChannelVO(r.id,r.channelName,r.parantId,r.url,r.icon,r.inTurn ) from  BaseUserChannelConfig c , BaseChannel r  where r.apply=1 and c.status=1 and r.status=1 and c.channelId=r.id  and c.userId=:userId order by r.parantId asc , r.inTurn asc";
		List<ChannelVO> channelList = (List<ChannelVO>)channelDao.queryHqlByParam(hql, paramMap);
		return channelList;
	}

	/**
	 * 查询用户未拥有的权限
	 * @param userId
	 * @param paramMap
	 * @return
	 */
	public List<ChannelVO> queryUserChannelConfigNotByUser(Long userId,
			Map<String, Object> paramMap) {
		paramMap.put("userId", userId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.ChannelVO(r.id,r.channelName) from  BaseChannel r  where r.status=1 and r.apply=1 and  r.id not in (select channelId from BaseUserChannelConfig where status=1 and userId=:userId ) ";
		List<ChannelVO> channelList = (List<ChannelVO>)channelDao.queryHqlByParam(hql, paramMap);
		return channelList;
	}
	
	
	/**
	 * 查询用户所拥有的权限
	 * @param ruleId
	 * @param paramMap
	 * @return
	 */
	public List<ChannelVO> queryRuleChannelConfigByUser(Long ruleId,
			Map<String, Object> paramMap) {
		paramMap.put("ruleId", ruleId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.ChannelVO(r.id,r.channelName,r.parantId,r.url,r.icon,r.inTurn ) from  BaseRuleChannelConfig c , BaseChannel r  where c.status=1 and r.status=1 and r.apply=1 and c.channelId=r.id  and c.ruleId=:ruleId order by r.id asc";
		List<ChannelVO> channelList = (List<ChannelVO>)channelDao.queryHqlByParam(hql, paramMap);
		return channelList;
	}

	/**
	 * 查询角色未拥有的权限
	 * @param ruleId
	 * @param paramMap
	 * @return
	 */
	public List<ChannelVO> queryRuleChannelConfigNotByUser(Long ruleId,
			Map<String, Object> paramMap) {
		paramMap.put("ruleId", ruleId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.ChannelVO(r.id,r.channelName) from  BaseChannel r  where r.apply=1 and  r.status=1 and r.id not in (select channelId from BaseRuleChannelConfig where status=1 and ruleId=:ruleId ) ";
		List<ChannelVO> channelList = (List<ChannelVO>)channelDao.queryHqlByParam(hql, paramMap);
		return channelList;
	}

	/**
	 * 给用户赋予权限
	 * @param channelId
	 * @param userId
	 * @param paramMap
	 */
	public void updateUserChannelConfig(Long[] channelId, Long userId,
			Map<String, Object> paramMap) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addsetStringParam("status", ConstantsCommon.Del_Status.NO+"");
		cc.addEqParam("userId", userId);
		//先删除用户所有的角色
		userChannelConfigDao.update(cc);
		/*
		 * 循环插入数据
		 */
		for(Long id:channelId){
			BaseUserChannelConfig config=new BaseUserChannelConfig();
			config.setCreateBy(userId);
			config.setCreateDate(new Timestamp(System.currentTimeMillis()));
			config.setModiBy(userId);
			config.setChannelId(id);
			config.setUserId(userId);
			config.setStatus(ConstantsCommon.Del_Status.YES);
			userChannelConfigDao.save(config);
		}
		//日志记录
		logService.saveLog(userId, "新增", "用户权限配置表", "对行"+StringUtil.LongToString(channelId)+"进行新增");
		MessageCommon.getSuccessMap(paramMap);
	}

	/**
	 * 为角色赋予权限
	 * @param channelId
	 * @param userId
	 * @param ruleId
	 * @param paramMap
	 */
	public void updateRuleChannelConfig(Long[] channelId,Long userId, Long ruleId,
			Map<String, Object> paramMap) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addsetStringParam("status", ConstantsCommon.Del_Status.NO+"");
		cc.addEqParam("ruleId", ruleId);
		//先删除角色所有的权限
		ruleChannelConfigDao.update(cc);
		/*
		 * 循环插入数据
		 */
		for(Long id:channelId){
			BaseRuleChannelConfig config=new BaseRuleChannelConfig();
			config.setCreateBy(userId);
			config.setCreateDate(new Timestamp(System.currentTimeMillis()));
			config.setDelDate(null);
			config.setModiBy(userId);
			config.setChannelId(id);
			config.setRuleId(ruleId);
			ruleChannelConfigDao.save(config);
		}
		//日志记录
		logService.saveLog(userId, "新增", "角色权限配置表", "对行"+StringUtil.LongToString(channelId)+"进行新增");
		MessageCommon.getSuccessMap(paramMap);
	}


	/**
	 * 用户登录时检索出用户所拥有的权限，并进行排列
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryUserChannel(Long userId) {
		Map<String, Object> map=new HashMap<String, Object>();
		//用于去重权限过渡
		Map<Long, ChannelVO> m=new HashMap<Long, ChannelVO>();
		//存放<url,对象>权限
		Map<String, ChannelVO> m2=new HashMap<String, ChannelVO>();
		List<ChannelVO> channelList3=new ArrayList<ChannelVO>();
		/*
		 * 如果当前用户是管理员，那么直接所拥有所有权限
		 */
		if(userId==1L){
			String hql="select new com.njq.common.model.vo.ChannelVO(ch.id,ch.channelName,ch.parantId,ch.url,ch.icon,ch.inTurn ) from BaseChannel ch where ch.apply=1 and ch.status=1";
			channelList3 =(List<ChannelVO>)channelDao.queryHqlByParam(hql, null);
		}else{
			//查询用户所拥有的权限
			List<ChannelVO> channelList = queryUserChannelConfigByUser(userId, map);
			//查询用户所拥有的角色
			List<ChannelVO> channelList2=queryRuleChannelConfig(userId, map);
			//合并权限和角色
			channelList.addAll(channelList2);
			for(ChannelVO v:channelList){
				m.put(v.getId(), v);
				m2.put(v.getUrl().split("/")[0], v);
			}
			/*
			 * 去除重复
			 */
			for(Long l : m.keySet()){
				channelList3.add(m.get(l));
			}
		}
		Map<Long, Map<String, Object>> mm=new HashMap<Long, Map<String,Object>>();
		Map<String, Object> treeMap1 = new HashMap<String, Object>();
		treeMap1.put("id", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		treeMap1.put("name", "总栏目");
		treeMap1.put("children", new LinkedList<Map<String, Object>>());
		mm.put(ConstantsCommon.Org_Id.FIRST_ORG_ID, treeMap1);
		Map<Long, Boolean> powerMap=new HashMap<Long, Boolean>();
		for(ChannelVO vo:channelList3){
		    powerMap.put(vo.getId(), true);
		}
		parantSort(mm, channelList3);
		Map<String, Object> m3=mm.get(ConstantsCommon.Org_Id.FIRST_ORG_ID);
		//将权限放入map中
		m3.put("power",m2);
		m3.put("powerMap", powerMap);
		return m3;
	}
	
	/**
	 * 设置栏目权限为可应用或不可以用
	 * @param channelId
	 * @param userId
	 * @param type
	 * @param paramMap
	 */
	public void updateChannel(Long[] channelId, Long userId, String type,Map<String, Object> paramMap) {		
		ConditionsCommon cc=new ConditionsCommon();
		cc.addInParam("channelId", channelId);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		//检查是否存在被用户使用的权限
		int checkNum1 = userChannelConfigDao.queryForCount(cc);
		//检查是否存在被角色使用的权限
		int checkNum2 = ruleChannelConfigDao.queryForCount(cc);
		/*
		 * 判断是否有权限被使用
		 */
		if(checkNum1>0 || checkNum2>0){
			MessageCommon.getFalseMap(paramMap);
			paramMap.put("message", "有权限正在被使用，请解除权限后再进行操作！");
		}else{
			ConditionsCommon cc1=new ConditionsCommon();
			cc1.addsetStringParam("apply", type);
			cc1.addInParam("id", channelId);
			channelDao.update(cc1);
			//日志记录
			logService.saveLog(userId, "修改", "权限表", "对行"+StringUtil.LongToString(channelId)+"进行修改");
			MessageCommon.getSuccessMap(paramMap);
		}
	}

	/**
	 * 先取出一级栏目并进行排序 
	 * 
	 * 2015-12-16
	 * author njq
	 */
	private void parantSort(Map<Long, Map<String, Object>> mm ,List<ChannelVO> channelList){
		LinkedList<ChannelVO> list=new LinkedList<ChannelVO>();
		LinkedList<ChannelVO> list1=new LinkedList<ChannelVO>();
		/**
		 * 先提取出一级菜单
		 */
		for(int i=0;i<channelList.size();i++){
			if(channelList.get(i).getParantId()==0){
				list.add(channelList.get(i));
			}else{
				list1.add(channelList.get(i));
			}
		}
		//进行排序
		sort(list);
		//对一级菜单进行集成
		treeInteg(mm, list);
		//对二级菜单进行集成
		treeInteg(mm, list1);
		//对二级菜单进行排序
		treeCheck(mm);
	}
	
	/**
	 * 对LinkedList进行排序
	 * @param list
	 * 2015-12-17
	 * author njq
	 */
	private void sort(LinkedList<ChannelVO> list){
		ChannelVO vo;
		/**
		 * 对一级菜单进行排序
		 */
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				if(list.get(i).getInTurn()>list.get(j).getInTurn()){
					vo=list.get(i);
					list.set(i,list.get(j));
					list.set(j, vo);
				}
			}
		}
	}
	
	/**
	 * 循环遍历一级菜单，如果有二级菜单那么进行排序
	 * @param mm
	 * 2015-12-17
	 * author njq
	 */
	private void treeCheck(Map<Long, Map<String, Object>> mm){
		for(Long key:mm.keySet()){
			LinkedList<Map<String, Object>> list=(LinkedList<Map<String, Object>>)mm.get(key).get("children");
			//判断是否存在二级菜单
			if(list.size()>0){
				//对二级菜单进行排序
				secondSort(list);
			}
		}
	}
	
	/**
	 * 对二级菜单进行排序
	 * @param list
	 * 2015-12-17
	 * author njq
	 */
	private void secondSort(LinkedList<Map<String, Object>> list){
		Map<String, Object> map;
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				if(Integer.parseInt(list.get(i).get("inTurn")+"")>Integer.parseInt(list.get(j).get("inTurn")+"")){
					map=list.get(i);
					list.set(i, list.get(j));
					list.set(j , map);
				}
			}
		}
	}
	/**
	 * 栏目树的集成
	 * @param mm
	 * @param channelList
	 * 2015-12-16
	 * author njq
	 */
	private void treeInteg(Map<Long, Map<String, Object>> mm , List<ChannelVO> channelList){
		for(ChannelVO vo : channelList){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id", vo.getId());
			treeMap.put("name", vo.getChannelName());
			treeMap.put("url", vo.getUrl());
			treeMap.put("inTurn", vo.getInTurn());
			treeMap.put("icon", vo.getIcon());
			treeMap.put("children", new LinkedList<Map<String, Object>>());
			mm.put(vo.getId(), treeMap);
			if(mm.get(vo.getParantId())!=null){
				((List<Map<String, Object>>)mm.get(vo.getParantId()).get("children")).add(treeMap);
			}
		}
	}
	/**
	 * 查询角色对应的权限
	 * @param userId
	 * @param paramMap
	 * @return
	 * 2015-12-16
	 * author njq
	 */
	private List<ChannelVO> queryRuleChannelConfig(Long userId,Map<String, Object> paramMap){
		paramMap.put("userId", userId);
		//将查询出的对象转换为vo类，展示在页面中
		String hql=" select new com.njq.common.model.vo.ChannelVO(ch.id,ch.channelName,ch.parantId,ch.url,ch.icon,ch.inTurn ) from BaseChannel ch where ch.apply=1 and ch.id in ( SELECT ru.channelId from BaseRuleChannelConfig ru where ru.ruleId in ( select c.ruleId from BaseRule r , BaseUserRuleConfig c where r.id=c.ruleId and r.status=1 and c.status=1 and c.userId=:userId ) and ru.status=1)  order by ch.parantId asc , ch.inTurn asc";
		List<ChannelVO> channelList = (List<ChannelVO>)channelDao.queryHqlByParam(hql, paramMap);
		return channelList;
	}
	
	
	
}
