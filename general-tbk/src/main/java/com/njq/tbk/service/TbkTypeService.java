package com.njq.tbk.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkType;
import com.njq.common.model.po.TbkTypeDocConfig;
import com.njq.common.model.vo.TbkTypeVO;
import com.njq.common.util.rely.APIUtil;
import com.njq.common.util.string.StringUtil;

@Service
public class TbkTypeService {

	@Resource
	private DaoCommon<TbkType> tbktypeDao;
	@Resource
	private DaoCommon<TbkRecommendDocView>  tbkRecommendDocViewDao;
	@Resource
	private DaoCommon<TbkTypeDocConfig> tbktypedocconfigDao;
	
	/**
	 * 查询当前用户所拥有的所有类型
	 * @param userId
	 * @return
	 */
	public List<TbkType> queryAllTbktype(Long userId) {
		ConditionsCommon cc=new ConditionsCommon();
		if(!ConstantsCommon.Oper_User.ADMIN.equals(userId)){
			cc.addEqParam("createBy", userId);
		}
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		return tbktypeDao.queryTByParam(cc);
	}
	
	
	/**
	 * 查询当前类型下的所有子类型
	 * @param parentId
	 * @return
	 */
	public List<TbkType> queryParentTbktype(Long parentId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", parentId);
		return tbktypeDao.queryTByParam(cc);
	}

	/**
	 * 查询所有类型信息（分页）
	 * @param paramMap
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<TbkType> queryAllTbktype(Map<String, Object> paramMap,Long userId,
			int page, int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
//		cc.addNotEqParam("id", userId);
		if(!ConstantsCommon.Oper_User.ADMIN.equals(userId)){
			cc.addEqParam("createBy", userId);
		}
		if(paramMap.get("value")!=null){
			cc.addMoreColumLikeParam(paramMap.get("value").toString(), "account","userName","tel");
		}
		cc.addSetOrderColum("id","desc");
		PageList<TbkType> list = tbktypeDao.queryForPage(cc);
		/*
		 * 为类型查询父类型名称，暂时先设定为查询，以后再看是不是利用查询数据库的方法获取父类名称
		 * 一个方案 把顶级类型直接放到redis中
		 * 一个方案每次查数据库 
		 */
//		for(TbkType t:list.getList()){
//			TbkType t1 =queryTbktypeById(t.getParentId());
//			if(t1!=null){
//				t.setParentName(t1.getName());
//			}else{
//				t.setParentName("顶级类型");
//			}
//		}
		return list;
	}
	
	/**
	 * 根据id检索出类型
	 * @param id
	 * @return
	 */
	public TbkType queryTbktypeById(Long id) {
		return tbktypeDao.queryTById(id);
	}
	
	/**
	 * 保存类型
	 * @param type
	 * @param userId
	 * @param map
	 */
	public void saveTbktype(TbkType type, Long userId,
			Map<String, Object> map) {
		if(queryTbktypeByName(type.getName(),userId)==null){
			tbktypeDao.save(type);
			//日志记录
			LogCommon.saveLog(userId, "新增", "类型表", "对行"+type.getId()+"进行增加");
			MessageCommon.getSuccessMap(map);
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "类型名称已经存在！");
		}
	}
	
	/**
	 * 根据类型id修改类型信息
	 * @param type
	 * @param userId
	 * @param map
	 */
	public void updateTbktypeById(TbkType type,Long userId, Map<String, Object> map) {
		if(queryTbktypeByNameAndId(type.getName(),userId,type.getId())<=0){
			TbkType ty= tbktypeDao.queryTById(type.getId());
			ty.setColumDesc(type.getColumDesc());
			ty.setName(type.getName());
			ty.setModiBy(userId);
			ty.setModiDate(new Timestamp(System.currentTimeMillis()));
			tbktypeDao.update(ty);
			//日志记录
			LogCommon.saveLog(userId, "修改", "栏目表", "对行"+type.getId()+"进行修改");
			MessageCommon.getSuccessMap(map);
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "类型名称已经存在！");
		}
	}
	
	/**
	 * 删除类型
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteTbktypeById(Long[] ids,Long userId, Map<String, Object> map) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addInParam("typeId", ids);
		int typeDocNum = tbktypedocconfigDao.queryForCount(cc);
		if(typeDocNum>0){
			MessageCommon.getFalseMap(map,"当前类型下存在文章，不能删除！");
		}else{
			int num = tbktypeDao.delT(ids);
			if(num>0){
				MessageCommon.getSuccessMap(map);
				//日志记录
				LogCommon.saveLog(userId, "删除", "类型表", "对行"+StringUtil.LongToString(ids)+"进行删除");
			}else{
				MessageCommon.getFalseMap(map);
				map.put("message", "请检查数据是否正确，再进行操作！");
			}
		}
	}
	
	/**
	 * 根据名称查询类型(当前用户下是否有此类型，不同用户可以创建相同名称的类型)
	 * @param name
	 * @param userId
	 * @return
	 */
	public TbkType queryTbktypeByName(String name,Long userId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("name", name);
		cc.addEqParam("create_by", userId);
		List<TbkType> list = tbktypeDao.queryTByParam(cc);
		return list.size()==0?null:list.get(0);
	}
	
	
	/**
	 * 根据用户id和类型id查询当前用户创建的类型名称是否重复
	 * @param name
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public int queryTbktypeByNameAndId(String name, Long userId, Long typeId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("name", name);
		cc.addEqParam("create_by", userId);
		int num = tbktypeDao.queryForCount(cc);
		return num;
	}
	
	/**
	 * 根据文章id进行查询
	 * @param docId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TbkType queryTbktypeByDocId(Long docId) {
		return null;
//		String hql="select new com.njq.common.model.po.TbkType(p.id,p.name,p.createBy,p.createDate) " +
//				"from TbkType p ,TbkTypeDocConfig c where p.id=c.typeId and p.status=1 and c.status=1 and c.docId=:docId ";
//		Map<String, Object> paramMap=new HashMap<String, Object>();
//		paramMap.put("docId", docId);
//		List<TbkType> tbktypeList = (List<TbkType>)tbktypeDao.queryHqlByParam(hql, paramMap);
//		return tbktypeList.size()>0?tbktypeList.get(0):null;
	}
	
	/**
	 * 查询首页的小文章
	 * @param page
	 * @param size
	 * @return
	 */
	public List<TbkTypeVO> queryLittleDivText(int page, int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		cc.addSetOrderColum("inTurn", "asc");
		List<TbkType> typeList=tbktypeDao.queryForListNoPage(cc);
		List<TbkTypeVO> voList = new ArrayList<>();
		ConditionsCommon cc1=new ConditionsCommon();
		cc1.addPageParam(1, 5);
		cc1.addSetOrderColum("id", "desc");
		for(TbkType type : typeList){
			ConditionsCommon cc2=new ConditionsCommon();
			cc2.addEqParam("parentId", type.getId());
			cc2.addEqParam("status", ConstantsCommon.Del_Status.YES);
			List<TbkType> typeList1=tbktypeDao.queryForListNoPage(cc2);
			List<TbkRecommendDocView> docList =new ArrayList<TbkRecommendDocView>();
			for(TbkType t1 : typeList1){
				cc1.addEqParam("typeName", t1.getName());
				requestForCheck(cc1);
				docList.addAll((List<TbkRecommendDocView>)tbkRecommendDocViewDao.queryForListNoPage(cc1));
			}
			TbkTypeVO vo = new TbkTypeVO();
			BeanUtils.copyProperties(type, vo);
			if(docList.size()>4){
				vo.setTextList(docList.subList(0, 5));								
			}else{
				vo.setTextList(docList);
			}
			voList.add(vo);
		}
		return voList;
	}
	
	/**
	 * 根据创建者id进行查询
	 * @param userId
	 * @return
	 */
	public List<TbkType> queryTbktypeByCreatedBy(Long userId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("createBy", userId);
		List<TbkType> list = tbktypeDao.queryTByParam(cc);
		return list;
	}
	
	public static void requestForCheck(ConditionsCommon cc){
		if(APIUtil.getUrl()) {
			cc.addEqParam("readtype", ConstantsCommon.Doc_Type.NORMAL);
		}
	}
}
