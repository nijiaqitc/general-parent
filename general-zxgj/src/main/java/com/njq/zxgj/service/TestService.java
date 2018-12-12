package com.njq.zxgj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseUser;

@Service
public class TestService {

	@Resource
	private DaoCommon<BaseUser> userDao;
	
	public void queryList() {
		BaseUser user=userDao.queryTById(5L);
		System.out.println(user.getAccount());
		List<Map<String, Object>> list=userDao.queryHqlByParamForMap("select id,account from com.njq.common.model.po.BaseUser",new HashMap<>());
		System.out.println(list.get(1).get("account"));
		
		
		
		
//		List<BizLoanInfoPO> list= docDiscussDao.queryForList();
//		System.out.println(list);
//		for(BizLoanInfoPO po:list){
//			System.out.println(po.getId());
//		}
//		
//		BizLoanInfoPO bb=new BizLoanInfoPO();
////		bb.setId(1l);
//		bb.setName("2222");
//		docDiscussDao.save(bb);
	}
	
	
	public void saveTss() throws Exception {
	}
	
}
