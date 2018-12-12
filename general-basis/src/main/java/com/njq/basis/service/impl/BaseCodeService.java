package com.njq.basis.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseCode;
import com.njq.common.util.string.StringUtil;

@Service
public class BaseCodeService {

	@Resource
	private DaoCommon<BaseCode> codeDao;
	@Resource
	private BaseLogService logService;
	
	/**
	 * 查询所有字典(分页)
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	@Cacheable(value="code")//查询并存入缓存
	public PageList<BaseCode> queryAllCode(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return codeDao.queryForPage(cc);
	}
	
	/**
	 * 测试encache
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
//	@CachePut(value="code")//更新缓存
//	@CacheEvict(value="code",allEntries=true)//这是清除缓存
	public PageList<BaseCode> queryTest(Map<String, Object> paramMap, int page,
			int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return codeDao.queryForPage(cc);
	}

	/**
	 * 查询父级下的子集(分页)
	 * @param paramMap
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseCode> queryChildrenCodeList(Map<String, Object> paramMap,
			Long id, int page, int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", id);
		return codeDao.queryForPage(cc);
	}

	/**
	 * 查询子类型（不分页）
	 * @param paramMap
	 * @param id
	 * @return
	 */
	public List<BaseCode> queryChildrenCodeList(Map<String, Object> paramMap,Long id) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", id);
		return codeDao.queryForListNoPage(cc);
	}

	/**
	 * 根据id检索出字典
	 * @param id
	 * @return
	 */
	public BaseCode queryCodeById(Long id) {
		return codeDao.queryTById(id);
	}

	/**
	 * 保存字典
	 * @param code
	 * @param userId
	 * @param map
	 */
	public void saveCode(BaseCode code,Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("type", code.getType());
		List<BaseCode> codeList = codeDao.queryTByParam(cc);
		/*
		 * 首先判断是否存在字典类型
		 * 如果存在，那么直接以当前类型为父级
		 * 如果不存在，那么先插入一个父级，再作为父级
		 */
		if(codeList.size()>0){
			//提取父级id
			code.setParentId(codeList.get(0).getId());
			//保存子集
			codeDao.save(code);
		}else{
			BaseCode c=new BaseCode();
			c.setCreateBy(userId);
			c.setCreateDate(new Timestamp(System.currentTimeMillis()));
			c.setValue("");
			c.setColumDesc("父级");
			c.setInTurn(0);
			c.setModiBy(userId);
			c.setParentId(ConstantsCommon.Org_Id.FIRST_ORG_ID);
			c.setType(code.getType());
			c.setName(code.getType());
			//先保存父级
			codeDao.save(c);
			//再保存子集
			code.setParentId(c.getId());
			codeDao.save(code);
		}
		//日志记录
		logService.saveLog(userId, "新增", "字典表", "对行"+code.getId()+"进行增加");
		MessageCommon.getSuccessMap(map);
	}

	/**
	 * 根据id修改字典
	 * @param code
	 * @param userId
	 * @param map
	 */
	public void updateCodeById(BaseCode code,Long userId, Map<String, Object> map) {
		BaseCode ch = codeDao.queryTById(code.getId());
		ch.setName(code.getName());
		ch.setColumDesc(code.getColumDesc());
		ch.setValue(code.getValue());
		codeDao.update(ch);
		//日志记录
		logService.saveLog(userId, "修改", "字典表", "对行"+code.getId()+"进行修改");
		MessageCommon.getSuccessMap(map);

	}

	/**
	 * 删除字典
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteCodeById(Long[] ids,Long userId, Map<String, Object> map) {
		int num = codeDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			logService.saveLog(userId, "删除", "字典表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "请检查数据是否正确，再进行操作！");
		}
	}

	/**
	 * 查询所有的子字典
	 * @return
	 */
	public List<BaseCode> queryAllChildren() {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addNotEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return codeDao.queryForListNoPage(cc);
	}

	
	
}
