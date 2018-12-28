package com.njq.common.base.dao.ddl;

import java.util.Map;

import javax.persistence.Query;

import com.njq.common.base.dao.ConditionsCommon;

public class SqlJpaInnerDeal {

	
	/**
	 * 生成hql语句
	 * @param condition
	 * @return
	 */
	public static String buildHql(ConditionsCommon condition){
		StringBuffer stb=new StringBuffer();
		if(condition!=null&&condition.getSeleMap().size()!=0){
			buildSelect(stb, condition.getSeleMap());
		}
		stb.append(" from ");
		stb.append(condition.getClassName());
		/*
		 * 判断是否存在检索条件
		 */
		if(condition!=null&&condition.getParamMap().size()>0){
			buildWhere(stb, condition.getParamMap());
		}
		/*
		 * 判断是否存在排序条件
		 */
		if(condition!=null&&condition.getOrderMap().size()>0){
			StringBuffer ss=new StringBuffer();
			ss.append(" order by ");
			for(String columName:condition.getOrderMap().keySet()){
				 ss.append(columName +" "+condition.getOrderMap().get(columName)+" ,");
			}
			stb.append(ss.toString().substring(0, ss.toString().length()-1));
		}
		return stb.toString();
	}



	/**
	 * 编写检索条件
	 * @param stb
	 * @param paramMap
	 * @return
	 * author njq
	 */
	public static String buildSelect(StringBuffer stb,Map<String, String> paramMap){
		stb.append(" select ");
		StringBuffer seleStb=new StringBuffer();
		for(String str:paramMap.keySet()){
			seleStb.append(","+str+" as "+paramMap.get(str)+" ");
		}
		return stb.append(seleStb.toString().substring(1, seleStb.toString().length()-1)).toString();
	}


	/**
	 * 编写hql语句的where
	 * @param stb
	 * @param paramMap
	 * @return
	 * author njq
	 */
	public static String buildWhere(StringBuffer stb,Map<String, Object> paramMap){
		stb.append(" where ");
		StringBuffer conStb=new StringBuffer();
		int i=0;
		for(String columName:paramMap.keySet()){
			String colum=columName.split(",")[0];
			String op=columName.split(",")[1];
			if("like".equals(op)){
				colum=colum.replace("!", ",");
				conStb.append(" and "+colum+" "+op+" :likeParam " );
			}else if("is null".equals(op)||"is not null".equals(op)){
				conStb.append(" and "+colum+" "+op);
			}else if("between".equals(op)){
				conStb.append(" and "+colum+" "+op+" :betA and :betB ");
			}else{
				conStb.append(" and "+colum+" "+op+" :param"+(++i));
			}
		}
		return stb.append(conStb.toString().substring(4, conStb.toString().length())).toString();
	}

	
	/**
	 * 填充变量
	 * @param query
	 * @param paramMap
	 * 2015-12-14
	 * author njq
	 */
	public static void setParam(Query query , Map<String, Object> paramMap){
		if(paramMap!=null){
			for(String colum : paramMap.keySet()){
				query.setParameter(colum, paramMap.get(colum));
			}
		}
	}
	
	/**
	 * 给hql填充变量
	 * @param query
	 * @param paramMap 变量map
	 * 2015-12-4
	 * author njq
	 */
	public static void bandParam(Query query,Map<String, Object> paramMap){
		if(paramMap!=null){
			int i=0;
			for(String colum : paramMap.keySet()){
				String op=colum.split(",")[1];
				if("in".equals(op)){
					query.setParameter("param"+(++i), paramMap.get(colum));
//					query.setParameterList("param"+(++i), (Object[])paramMap.get(colum));
				}else if("like".equals(op)){
					query.setParameter("likeParam", "%"+paramMap.get(colum)+"%");
				}else if("is null".equals(op)||"is not null".equals(op)){
					continue;
				}else if("between".equals(op)){
					Object[] o =(Object[])paramMap.get(colum);
					Object value1=o[0];
					Object value2=o[1];
					query.setParameter("betA", value1);
					query.setParameter("betB", value2);
				}else{
					query.setParameter("param"+(++i), paramMap.get(colum));
				}
			}
		}
	}
	
	
	/**
	 * 编写检索条件
	 * @param stb
	 * @param paramMap
	 * @return
	 * 2015-12-8
	 * author njq
	 */
	public static String buildSet(StringBuffer stb,ConditionsCommon cc){
		StringBuffer setStb=new StringBuffer();
		for(String str:cc.getSetMap().keySet()){
			if(str.contains("&")){
				setStb.append(", "+str.split("&")[0]+"="+cc.getSetMap().get(str)+" ");
			}else{
				setStb.append(", "+str+"='"+cc.getSetMap().get(str)+"' ");
			}
		}
		return stb.append(setStb.toString().substring(1, setStb.toString().length()-1)).toString();
	}
	
	
}
