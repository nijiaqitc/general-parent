package com.njq.tbk.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.TbkTip;

@Service
public class TbkTipService {

	@Resource
	private DaoCommon<TbkTip> tbktipDao;
	
	/**
	 * 查询所有标签
	 * @return
	 */
	public List<TbkTip> queryAllTbktip() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询所有标签信息（分页）
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<TbkTip> queryAllTbktip(Map<String, Object> paramMap,
			int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据id检索出标签
	 * @param id
	 * @return
	 */
	public TbkTip queryTbktipById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存标签信息
	 * @param user
	 * @param loginTbktip
	 * @param map
	 */
	public void saveTbktip(TbkTip user, TbkTip loginTbktip,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 根据标签id修改标签信息
	 * @param user
	 * @param loginTbktip
	 * @param map
	 */
	public void updateTbktipById(TbkTip user, TbkTip loginTbktip,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 删除标签
	 * @param ids
	 * @param loginTbktip
	 * @param map
	 */
	public void deleteTbktipById(Long[] ids, TbkTip loginTbktip,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 根据docId查询出所关联的所有标签
	 * @param docId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbkTip> queryTbktipByDocId(Long docId) {
		String hql="select new com.njq.model.TbkTip(p.id,p.name,p.inTurn,p.status,p.createBy,p.createDate)" +
				" from TbkTip p ,TbkDocTipConfig c where p.id=c.tipId and p.status=1 and c.status=1 and c.docId=:docId ";
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("docId", docId);
		List<TbkTip> tbkTipList = (List<TbkTip>)tbktipDao.queryHqlByParam(hql, paramMap);
		return tbkTipList;
	}

	
	/**
	 * 根据标签名字查询标签
	 * @param tipName
	 * @return
	 */
	public TbkTip queryTbktipByName(String tipName) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("name", tipName);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		List<TbkTip> tbkTipList = (List<TbkTip>)tbktipDao.queryForListNoPage(cc);
		if(tbkTipList.size()>0){
			return tbkTipList.get(0);
		}
		return null;
	}

	
	public void test() {
		String fileContent = ""; 
		try {  
			File f = new File("test.txt"); 
		    if(f.isFile()&&f.exists()){
			FileInputStream fs=new FileInputStream(f);
		    InputStreamReader read = new InputStreamReader(fs,"UTF-8");
		    BufferedReader reader=new BufferedReader(read); 
		    String line; 
		    while ((line = reader.readLine()) != null) { 
		    	fileContent += line; 
		    }
		    fs.close();
		    read.close();
		    reader.close();
		   } 
		  } catch (Exception e) { 
		   System.out.println("读取文件内容操作出错"); 
		   	e.printStackTrace(); 
		  } 
		
		String[] aaa=fileContent.split("、");
		for(int i=0;i<aaa.length;i++){
			 String fff="insert into tool_material(name) values ('"+aaa[i]+"');";
			 System.out.println(fff);
		}
	}
	
	
	
}
