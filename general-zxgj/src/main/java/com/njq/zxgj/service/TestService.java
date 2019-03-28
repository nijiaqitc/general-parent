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


		userDao.test();

//		BaseUser user=userDao.queryTById(5L);
//		System.out.println(user.getAccount());
//
//		ConditionsCommon cc=new ConditionsCommon();
//		cc.addEqParam("account", user.getAccount());
//		//查询数据库，判断当前账户是否已经存在
//		List<BaseUser> list1 =  userDao.queryTByParam(cc);


		String str="select t.id,t.tip_name as tipName from yxl_doc_tip_config c left join yxl_tip t on c.tip_id=t.id where c.doc_id=19";
		List<Map<String, Object>>  mapList=userDao.querySqlByParamForMap(str, null);
		System.out.println(mapList);
		List<Map<String, Object>> list=userDao.queryHqlByParamForMap("from com.njq.common.model.po.BaseUser ",new HashMap<>());
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
