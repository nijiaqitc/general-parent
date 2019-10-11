package com.njq.common.base.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.njq.common.base.dao.ddl.DdlInterface;
import com.njq.common.base.dao.ddl.SqlJpaInnerDeal;

public class DaoCommon<T> {

	@Autowired
	private DdlInterface<T> ddl;

	private Class<T> classType;

	public DaoCommon(Class<T> t) {
		this.classType = t;
	}

	public void test() {
		ddl.test();
	}

	/**
	 * 保存对象
	 * 
	 * @param object
	 * @return
	 */
	@Transactional
	public void save(T object) {
		ddl.save(object);
	}

	/**
	 * 批量保存对象
	 * 
	 * @param list
	 */
	public void saveList(List<T> list) {
		ddl.saveList(list);
	}

	public T queryTById(Long id) {
		return ddl.queryTById(classType, id);
	}

	public List<Map<String, Object>> querySqlList(String sql) {
		return ddl.querySqlList(sql);
	}

	public List<T> queryHqlList(String hql) {
		return ddl.queryHqlList(hql);
	}

	public List<? extends Object> queryHqlByParam(String hql, Map<String, Object> paramMap) {
		return ddl.queryHqlByParam(hql, paramMap);
	}

	public List<? extends Object> queryHqlByParamForLimit(String hql, ConditionsCommon condition,
			Map<String, Object> paramMap) {
		setClassName(condition);
		return ddl.queryHqlByParamForLimit(hql, condition, paramMap);
	}

	public List<Map<String, Object>> queryHqlByParamForMap(String hql, Map<String, Object> paramMap) {
		return ddl.queryHqlByParamForMap(hql, paramMap);
	}

	public List<Map<String, Object>> querySqlByParamForMap(String sql, Map<String, Object> paramMap) {
		return ddl.querySqlByParamForMap(sql, paramMap);
	}

	public List<Map<String, Object>> querySqlByParamForMap(String sql, Map<String, Object> paramMap,
			ConditionsCommon condition) {
		return ddl.querySqlByParamForMap(sql, paramMap, condition);
	}

	public PageList<Map<String, Object>> querySqlForPage(String sql, Map<String, Object> paramMap, int pageNo,
			int size) {
		return ddl.querySqlForPage(sql, paramMap, pageNo, size);
	}

	public List<T> queryTByParam(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryTByParam(SqlJpaInnerDeal.buildHql(condition), condition);
	}

	public T queryTByParamForOne(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryTByParamForOne(SqlJpaInnerDeal.buildHql(condition), condition);
	}

	public PageList<T> queryForPage(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryForPage(SqlJpaInnerDeal.buildHql(condition), condition);
	}

	public PageList<Map<String, Object>> queryMapForPage(String hql) {
		return ddl.queryMapForPage(hql, null);
	}

	public List<T> queryForListNoPage(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryForListNoPage(SqlJpaInnerDeal.buildHql(condition), condition);
	}

	public List<T> queryColumnForList(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryColumnForList(SqlJpaInnerDeal.buildHql(condition), condition);
	}

	public List<Map<String, Object>> queryColumnForListMap(String hql) {
		return ddl.queryColumnForListMap(hql, null);
	}

	public PageList<Map<String, Object>> queryCustomHqlForPage(String hql, ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryCustomHqlForPage(hql, condition);
	}

	public PageList<T> queryHqlByParamForPage(String hql, ConditionsCommon condition, Map<String, Object> paramMap) {
		setClassName(condition);
		return ddl.queryHqlByParamForPage(hql, condition, paramMap);
	}

	public int queryForCount(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryForCount(condition);
	}

	public int queryHqlForCount(String hql, ConditionsCommon condition) {
		setClassName(condition);
		return ddl.queryHqlForCount(hql, condition);
	}

	public int queryHqlForCount(String hql, Map<String, Object> paramMap) {
		return ddl.queryHqlForCount(hql, paramMap);
	}

	public void update(T object) {
		ddl.update(object);
	}

	public void updateByPrimaryKeySelective(T object) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			ConditionsCommon condition = new ConditionsCommon();
			setClassName(condition);
			for (Field field : fields) {
				field.setAccessible(true);
				//排除掉serialVersionUID 这个字段
				if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
					continue;
				} else {
					if(field.get(object) == null) {
						continue;
					}
					if("id".equals(field.getName())) {
						continue;
					}
					condition.addsetObjectParam(field.getName(), field.get(object));
				}
			}
			Field field =object.getClass().getDeclaredField("id");
			if(field.get(object) == null) {
				return;
			}
			field.setAccessible(true);
			condition.addEqParam("id", field.get(object));
			ddl.update(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int update(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.update(condition);
	}

	public int updateBysql(String sql, Map<String, Object> paramMap) {
		return ddl.updateBysql(sql, paramMap);
	}

	public int updateByHql(String hql, Map<String, Object> paramMap) {
		return ddl.updateByHql(hql, paramMap);
	}

	public void delete(T object) {
		ddl.delete(object);
	}

	public int delT(Long[] ids) {
		return ddl.delT(ids, classType.getName());
	}

	public int delTRealById(Long id) {
		return ddl.delTRealById(id, classType.getName());
	}

	public int deleteBycc(ConditionsCommon condition) {
		setClassName(condition);
		return ddl.deleteBycc(condition);
	}

	public int delT(Long[] ids, String colum) {
		return ddl.delT(ids, classType.getName());
	}

	private void setClassName(ConditionsCommon condition) {
		condition.setClassName(classType.getName());
	}

}
