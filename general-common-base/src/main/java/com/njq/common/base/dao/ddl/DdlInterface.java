package com.njq.common.base.dao.ddl;

import java.util.List;
import java.util.Map;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.PageList;

public interface DdlInterface<T> {


	public void test();

	/**
	 * 保存对象
	 * @param object
	 * @return
	 */
	public void save(T object);
	
	/**
	 * 批量保存
	 * @param list
	 */
	public void saveList(List<T> list);
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public T queryTById(Class<T> t,Long id);
	
	/**
	 * 根据sql查询
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> querySqlList(String sql);
	
	/**
	 * 根据hql查询
	 * @param hql
	 * @return
	 */
	public List<T> queryHqlList(String hql);
	
	/**
	 * 根据hql语句进行检索（from tableA a where a.id=:id ）
	 * (select a.* from tableA a left join tableB b where a.id=b.id and a.name=:aName and b.name=:bName )
	 * @param hql 手动写的hql语句
	 * @param paramMap 条件
	 * @return 可以返回任何对象
	 */
	public List<? extends Object> queryHqlByParam(String hql,Map<String, Object> paramMap);
	
	
	/**
	 * 根据hql查询分页的数据
	 * @param hql 
	 * @param condition 条件集合
	 * @param paramMap 填充参数
	 * @return
	 */
	public List<? extends Object> queryHqlByParamForLimit(String hql,ConditionsCommon condition,Map<String, Object> paramMap);
	
	
	/**
	 * 根据hql语句进行检索 以map形式进行返回
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHqlByParamForMap(String hql,Map<String, Object> paramMap);
	
	/**
	 * 根据sql语句进行检索（select * from user where  id=:id ）
	 * @param Sql 执行的sql语句
	 * @param paramMap 绑定的参数
	 * @return
	 */
	public List<Map<String, Object>> querySqlByParamForMap(String sql,Map<String, Object> paramMap);
	
	/**
	 * sql分页返回集合
	 * @param Sql
	 * @param paramMap
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> querySqlByParamForMap(String sql,Map<String, Object> paramMap,ConditionsCommon condition);
	
	/**
	 * 分页查询sql
	 * @param sql
	 * @param paramMap
	 * @param pageNo
	 * @param size
	 * @return
	 */
	public PageList<Map<String, Object>> querySqlForPage(String sql,Map<String, Object> paramMap,int pageNo ,int size);
	
	/**
	 * 根据条件（无分页）查询
	 * @param hql
	 * @return 
	 */
	public List<T> queryTByParam(String hql,ConditionsCommon condition);
	
	/**
	 * 根据条件查询，只返回第一个
	 * @param hql
	 * @return
	 */
	public T queryTByParamForOne(String hql,ConditionsCommon condition);
	
	/**
	 * 分页查询
	 * @param hql
	 * @return
	 */
	public PageList<T> queryForPage(String hql,ConditionsCommon condition);
	
	/**
	 * 分页查询
	 * @param hql
	 * @return
	 */
	public PageList<Map<String, Object>> queryMapForPage(String hql,ConditionsCommon condition);
	
	
	/**
	 * 含分页条件返回集合
	 * @param hql
	 * @return
	 */
	public List<T> queryForListNoPage(String hql,ConditionsCommon condition);
	
	/**
	 * 查询指定字段的集合列表
	 * @param hql
	 * @return
	 */
	public List<T> queryColumnForList(String hql,ConditionsCommon condition);
	
	
	/**
	 * 查询指定字段的集合列表
	 * @param hql
	 * @return
	 */
	public List<Map<String, Object>> queryColumnForListMap(String hql,ConditionsCommon condition);
	
	
	/**
	 * 手动hql转换成分页数据
	 * @param hql
	 * @param condition
	 * @return
	 */
	public PageList<Map<String, Object>> queryCustomHqlForPage(String hql,ConditionsCommon condition);
	
	/**
	 * 分页查询
	 * @param hql
	 * @param condition
	 * @param paramMap
	 * @return
	 */
	public PageList<T> queryHqlByParamForPage(String hql,ConditionsCommon condition,Map<String, Object> paramMap);
	
	
	/**
	 * 查询总记录数
	 * @param hql
	 * @return
	 */
	public int queryForCount(ConditionsCommon condition);
	
	
	/**
	 * 查询总数记录(必须只有1个from)
	 * @param hql
	 * @param condition
	 * @return
	 */
	public int queryHqlForCount(String hql,ConditionsCommon condition);
	
	
	/**
	 * 查询总数记录(必须只有1个from)
	 * @param hql
	 * @param condition
	 * @return
	 */
	public int queryHqlForCount(String hql,Map<String, Object> paramMap);
	
	/**
	 * 修改对象
	 * @param object
	 */
	public void update(T object);
	
	/**
	 * 操作数据库，返回总共操作的行数
	 * @param cc
	 */
	public int update(ConditionsCommon cc);
	
	
	/**
	 * 执行修改的sql语句（update user set userName=:userName where id=1 ）
	 * @param sql 
	 * @param paramMap
	 * @return
	 */
	public int updateBysql(String sql , Map<String, Object> paramMap);
	
	
	/**
	 * 执行hql语句
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public int updateByHql(String hql , Map<String, Object> paramMap);
	
	/**
	 * 删除对象
	 * @param object
	 */
	public void delete(T object);
	
	/**
	 * 批量删除数据
	 * @param ids
	 * @return
	 */
	public int delT(Long[] ids,String className);
	
	
	/**
	 * 真实删除数据
	 * @param id
	 * @return
	 */
	public int delTRealById(Long id,String className);
	
	/**
	 * 根据条件删除
	 * @param hql
	 * @param cc
	 * @return
	 */
	public int deleteBycc(ConditionsCommon cc);
	
	
	/**
	 * 根据某个字段批量删除
	 * @param ids 删除的id集合
	 * @param colum 字段名称
	 * @return
	 */
	public int delT(Long[] ids,String colum,String className);
}
