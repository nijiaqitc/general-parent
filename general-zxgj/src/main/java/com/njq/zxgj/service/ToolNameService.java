package com.njq.zxgj.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseUser;
@Service
public class ToolNameService {

	@Resource
	private DaoCommon<BaseUser> userDao;
	
	/**
     * 获取名字(人名)
     * @param type1 1:单 2:双
     * @param type2 1:单 2:双
     * @return
     */
	public String queryName(String type1, String type2,String namea,String nameb) {
		String name1="";
		String name2="";
		if(StringUtils.isEmpty(namea)){
			String sql1="select  name  from  tool_name_x where ";
			if("1".equals(type1)){
				sql1+=" type=1 and ";
			}else if("2".equals(type1)){
				sql1+=" type=2 and ";
			}
			sql1+="isNoNeed!=2 and del_date is null order by rand() limit 1";
			List<Map<String, Object>> list1=userDao.querySqlByParamForMap(sql1, null);
			name1=list1.get(0).get("name").toString();			
		}else{
			name1=namea;
		}
		
		if(StringUtils.isEmpty(nameb)){
			String sql2="";
			if("0".equals(type2)){
				type2=((int)(Math.random()*2+1))+"";
			}
			if("1".equals(type2)){
				sql2="select  name  from  tool_name_m where isNoNeed!=2 and del_date is null order by rand() limit 1";
				List<Map<String, Object>> list2=userDao.querySqlByParamForMap(sql2, null);
				name2=list2.get(0).get("name").toString();
			}else if("2".equals(type2)){
				sql2="select a.name aName , b.name bName from"+
						"(select  name  from  tool_name_m where isNoNeed!=2 and del_date is null order by rand() limit 1) as a"+
						",(select  name  from  tool_name_m where isNoNeed!=2 and del_date is null order by rand() limit 1) as b";
				List<Map<String, Object>> list2=userDao.querySqlByParamForMap(sql2, null);
				name2=list2.get(0).get("aName").toString()+list2.get(0).get("bName").toString();
			}
		}else{
			name2=nameb;
		}
		return name1+name2;
	}

	/**
	 * 删除名字
	 * @param name
	 * @return
	 */
	public String updateName(String name) {
		String sql="update tool_name_m set isNoNeed=2 where name='"+name+"'";
		return userDao.updateBysql(sql, null)+"";
	}

	/**
     * 获取名字
     * @param type 总类别
     * @param type2 子类别
     * @return
     */
	public String queryOtherName(String type, String type2,String check) {
		String str=" select name,last_name lastName from tool_material where  ";
		if("1".equals(check)){
			str+=" superclass='"+type+"' order by rand() limit 1";
		}else{
			str+=" type='"+type2+"' and superclass='"+type+"' order by rand() limit 1";
		}
		List<Map<String, Object>> list1=userDao.querySqlByParamForMap(str, null);
		if(list1.size()>0){
			String name=list1.get(0).get("name").toString();
			String lastName=list1.get(0).get("lastName")==null?"":list1.get(0).get("lastName").toString();
			if("1".equals(check)){
				return name;			
			}else{
				return name+lastName;
			}
		}else{
			return "暂无数据";
		}
	}

	
	
}
