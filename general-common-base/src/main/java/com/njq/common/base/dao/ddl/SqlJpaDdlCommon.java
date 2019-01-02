package com.njq.common.base.dao.ddl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.util.date.DateUtil;

@SuppressWarnings("unchecked")
@Repository
public class SqlJpaDdlCommon<T> implements DdlInterface<T> {

    @Autowired
    @PersistenceContext
    private EntityManager sessionFactory;
    @Autowired
    private SqlJpaRepository repository;

    @Override
    public void test() {
        String hql = "select  id from com.njq.common.model.po.BaseUser";
        Query query = sessionFactory.createQuery(hql, Object.class);
        List<Object[]> list = query.getResultList();

        System.out.print(list);
    }

    @Override
    public void save(T object) {
        sessionFactory.persist(object);


//		T t= sessionFactory.merge(object);
//		
//		sessionFactory.remove(object);
    }

    @Override
    public void saveList(List<T> list) {
        for (T t : list) {
            save(t);
        }
    }

    @Override
    public T queryTById(Class<T> t, Long id) {
        return sessionFactory.find(t, id);
    }


    @Override
    public List<Map<String, Object>> querySqlList(String sql) {
        return getSqlQuery(sql, null).getResultList();
    }

    @Override
    public List<T> queryHqlList(String hql) {
    	Object[] obj =(Object[])getHqlQuery(hql, null).getResultList().get(0);
    	System.out.println(obj[0]);
        return (List<T>) getHqlQuery(hql, null).getResultList();
    }

    @Override
    public List<? extends Object> queryHqlByParam(String hql, Map<String, Object> paramMap) {
        Query query = getHqlQuery(hql, null);
//		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(ChannelVO.class));
        SqlJpaInnerDeal.setParam(query, paramMap);
        return query.getResultList();
    }

    @Override
    public List<? extends Object> queryHqlByParamForLimit(String hql, ConditionsCommon condition,
                                                          Map<String, Object> paramMap) {
        Query query = getHqlQuery(hql, null);
        if (condition != null) {
            query.setFirstResult((condition.getPageMap().get("page") - 1) * condition.getPageMap().get("size"));
            query.setMaxResults(condition.getPageMap().get("size"));
        }
        SqlJpaInnerDeal.setParam(query, paramMap);
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> queryHqlByParamForMap(String hql, Map<String, Object> paramMap) {
        Query query = getHqlQuery(hql, "1");
        SqlJpaInnerDeal.setParam(query, paramMap);
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> querySqlByParamForMap(String sql, Map<String, Object> paramMap) {
        Query query = getSqlQuery(sql, "1");
        SqlJpaInnerDeal.setParam(query, paramMap);
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> querySqlByParamForMap(String sql, Map<String, Object> paramMap,
                                                           ConditionsCommon condition) {
        Query query = getSqlQuery(sql, "1");
        SqlJpaInnerDeal.setParam(query, paramMap);
        /*
         * 判断是否需要分页
         */
        if (condition.getPageMap().get("page") != -1) {
            query.setFirstResult((condition.getPageMap().get("page") - 1) * condition.getPageMap().get("size"));
            query.setMaxResults(condition.getPageMap().get("size"));
        }
        return query.getResultList();
    }

    @Override
    public PageList<Map<String, Object>> querySqlForPage(String sql, Map<String, Object> paramMap, int pageNo,
                                                         int size) {
        Query query = getSqlQuery(sql, "1");
        SqlJpaInnerDeal.setParam(query, paramMap);
        query.setFirstResult((pageNo - 1) * size);
        query.setMaxResults(size);
        PageList<Map<String, Object>> page = new PageList<Map<String, Object>>();
        page.setList(query.getResultList());
        page.setTotal(querySqlCount(sql));
        return page;
    }

    @Override
    public List<T> queryTByParam(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, null);
        if (condition != null) {
            SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        }
        return query.getResultList();
    }

    @Override
    public T queryTByParamForOne(String hql, ConditionsCommon condition) {
        List<T> list = queryTByParam(hql, condition);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public PageList<T> queryForPage(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, null);
        if (condition != null) {
            SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
            /*
             * 判断是否需要分页
             */
            if (condition.getPageMap().get("page") != -1) {
                query.setFirstResult((condition.getPageMap().get("page") - 1) * condition.getPageMap().get("size"));
                query.setMaxResults(condition.getPageMap().get("size"));
            }
        }
        PageList<T> pageList = new PageList<>();
        pageList.setList(query.getResultList());
        pageList.setTotal(queryForCount(condition));
        return pageList;
    }

    @Override
    public PageList<Map<String, Object>> queryMapForPage(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, "1");
        SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        pageCheckAndSet(query, condition);
        PageList<Map<String, Object>> pageList = new PageList<Map<String, Object>>();
        pageList.setList(query.getResultList());
        pageList.setTotal(queryForCount(condition));
        return pageList;
    }

    @Override
    public List<T> queryForListNoPage(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, null);
        SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        pageCheckAndSet(query, condition);
        return conver(query.getResultList(),condition);
    }

    @Override
    public List<T> queryColumnForList(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, null);
        if (condition != null) {
            SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
            pageCheckAndSet(query, condition);
        }
        return conver(query.getResultList(), condition);
    }
    
    

    @Override
    public List<Map<String, Object>> queryColumnForListMap(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, "1");
        SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        pageCheckAndSet(query, condition);
        return query.getResultList();
    }

    @Override
    public PageList<Map<String, Object>> queryCustomHqlForPage(String hql, ConditionsCommon condition) {
        Query query = getHqlQuery(hql, "1");
        if (condition != null) {
            SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
            pageCheckAndSet(query, condition);
        }
        PageList<Map<String, Object>> pageList = new PageList<>();
        pageList.setList(query.getResultList());
        pageList.setTotal(queryForCount(condition));
        return pageList;
    }

    @Override
    public PageList<T> queryHqlByParamForPage(String hql, ConditionsCommon condition, Map<String, Object> paramMap) {
        Query query = getHqlQuery(hql, null);
        SqlJpaInnerDeal.setParam(query, paramMap);
        pageCheckAndSet(query, condition);
        PageList<T> pageList = new PageList<>();
        pageList.setList(query.getResultList());
        pageList.setTotal(queryForCount(condition));
        return pageList;
    }

    @Override
    public int queryForCount(ConditionsCommon condition) {
        //先重置选择字段的map集合
        condition.resetSeleParam();
        //设置检索字段
        condition.addSeleParam("count(*)", "total");
        Query query = getHqlQuery(SqlJpaInnerDeal.buildHql(condition), null);
        SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        int total = Integer.parseInt(query.getResultList().get(0).toString());
        //查询完数据库后移除检索字段，以为影响其他检索
        condition.removeSeleParam("count(*)");
        return total;
    }

    @Override
    public int queryHqlForCount(String hql, ConditionsCommon condition) {
        String[] str = hql.split("from");
        String hq = " select count(*) from  " + str[1];
        Query query = getHqlQuery(hq, null);
        if (condition != null) {
            SqlJpaInnerDeal.bandParam(query, condition.getParamMap());
        }
        return Integer.parseInt(query.getResultList().get(0).toString());
    }

    @Override
    public int queryHqlForCount(String hql, Map<String, Object> paramMap) {
        String[] str = hql.split("from");
        int length = str[0].length();
        String hq1 = hql.substring(length, hql.length());
        String hq2 = " select count(*) " + hq1;
        Query query = getHqlQuery(hq2, null);
        SqlJpaInnerDeal.setParam(query, paramMap);
        return Integer.parseInt(query.getResultList().get(0).toString());
    }

    @Override
    public void update(T object) {
        sessionFactory.merge(object);
    }

    @Override
    public int update(ConditionsCommon cc) {
        StringBuffer stb = new StringBuffer();
        stb.append(" update " + cc.getClassName() + " set ");
        SqlJpaInnerDeal.buildSet(stb, cc);
        if (cc.getParamMap() != null && cc.getParamMap().size() > 0) {
            SqlJpaInnerDeal.buildWhere(stb, cc.getParamMap());
        }
        Query query = getHqlQuery(stb.toString(), null);
        SqlJpaInnerDeal.bandParam(query, cc.getParamMap());
        return query.executeUpdate();
    }

    @Override
    public int updateBysql(String sql, Map<String, Object> paramMap) {
        Query query = getSqlQuery(sql, null);
        SqlJpaInnerDeal.bandParam(query, paramMap);
        return query.executeUpdate();
    }

    @Override
    public int updateByHql(String hql, Map<String, Object> paramMap) {
        Query query = getHqlQuery(hql, null);
        SqlJpaInnerDeal.bandParam(query, paramMap);
        return query.executeUpdate();
    }

    @Override
    public void delete(T object) {
        T t = sessionFactory.merge(object);
        if (t != null) {
            sessionFactory.remove(object);
        }
    }

    @Override
    public int delT(Long[] ids, String className) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addInParam("id", ids);
        cc.addsetStringParam("status", ConstantsCommon.Del_Status.NO + "");
        cc.addsetStringParam("delDate", DateUtil.getNowTimeForTimestamp().toString());
        cc.setClassName(className);
        return update(cc);
    }

    @Override
    public int delTRealById(Long id, String className) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("id", id);
        cc.setClassName(className);
        return deleteBycc(cc);
    }

    @Override
    public int deleteBycc(ConditionsCommon cc) {
        StringBuffer stb = new StringBuffer();
        stb.append(" delete from " + cc.getClassName());
        if (cc.getParamMap() != null && cc.getParamMap().size() > 0) {
            SqlJpaInnerDeal.buildWhere(stb, cc.getParamMap());
        }
        Query query = getHqlQuery(stb.toString(), cc.getClassName());
        SqlJpaInnerDeal.bandParam(query, cc.getParamMap());
        return query.executeUpdate();
    }

    @Override
    public int delT(Long[] ids, String colum, String className) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addInParam(colum, ids);
        cc.addsetStringParam("status", ConstantsCommon.Del_Status.NO + "");
        cc.addsetStringParam("delDate", DateUtil.getNowTimeForTimestamp().toString());
        cc.setClassName(className);
        return update(cc);
    }

    /**
     * 获取查询hql的query
     *
     * @param hql
     * @param resultType
     * @return
     */
    private Query getHqlQuery(String hql, String resultType) {
        Query query = sessionFactory.createQuery(hql);
        //如果传值说明需要返回map结果集
        if (resultType != null) {
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        return query;
    }

    /**
     * 获取查询sql的query
     *
     * @param sql
     * @param resultType
     * @return
     */
    private Query getSqlQuery(String sql, String resultType) {
        Query query = sessionFactory.createNativeQuery(sql);
        //如果传值说明需要返回map结果集
        if (resultType != null) {
//			query.unwrap(Session.class).createNativeQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        return query;
    }

    /**
     * 获取总数
     *
     * @param sql
     * @return
     */
    private int querySqlCount(String sql) {
        String[] str = sql.split("from");
        String ss = sql.replace(str[0], " select count(*) as total ");
        Query query = getSqlQuery(ss, "1");
        Map<String, Object> resultValue = (Map<String, Object>) query.getResultList().get(0);
        return Integer.parseInt(resultValue.get("total").toString());
    }

    /**
     * 检测是否需要进行分页查询
     *
     * @param query
     * @param condition
     */
    private void pageCheckAndSet(Query query, ConditionsCommon condition) {
        /*
         * 判断是否需要分页
         */
        if (condition.getPageMap().get("page") != -1) {
            query.setFirstResult((condition.getPageMap().get("page") - 1) * condition.getPageMap().get("size"));
            query.setMaxResults(condition.getPageMap().get("size"));
        }
    }
    
    private List<T> conver(List<Object> list,ConditionsCommon condition){
		List<T> ll = new ArrayList<>();
		if(CollectionUtils.isEmpty(list)) {
			return ll;
		}
    	try {
    		Class<T> classType = (Class<T>) Class.forName(condition.getClassName());
    		for(int i =0;i<list.size();i++) {
    			T bean = classType.newInstance();
    			Object[] objs = (Object[])list.get(i);
    			int j = 0;
    			Iterator<Map.Entry<String,String>> it = condition.getSeleMap().entrySet().iterator();
    			while(it.hasNext()) {
    				Map.Entry<String, String> entries = it.next();
    				Field field = classType.getDeclaredField(entries.getKey());
    				String fieldName = field.getName();
    				Method method = classType.getDeclaredMethod("set" 
    						+ fieldName.substring(0, 1).toUpperCase()
    						+ fieldName.substring(1), field.getType());
    				method.invoke(bean, objs[j++]);
    			}
    			ll.add(bean);
    		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return ll;
    }
}
