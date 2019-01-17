package com.njq.tbk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.TbkPic;
import com.njq.common.util.string.StringUtil;

@Service
public class TbkPicService {

	@Resource
	private DaoCommon<TbkPic> tbkpicDao;
	
	/**
	 * 查询所有图片
	 * @return
	 */
	public List<TbkPic> queryAllTbkpic() {
		return tbkpicDao.queryTByParam(null);
	}

	/**
	 * 查询所有图片信息（分页）
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<TbkPic> queryAllTbkpic(Map<String, Object> paramMap,
			int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据id检索出图片
	 * @param id
	 * @return
	 */
	public TbkPic queryTbkpicById(Long id) {
		return tbkpicDao.queryTById(id);
	}

	/**
	 * 保存图片信息
	 * @param tbkpic
	 * @param map
	 * @return
	 */
	public Long saveTbkpic(TbkPic tbkpic, Map<String, Object> map) {
		tbkpicDao.save(tbkpic);
		return tbkpic.getId();
	}

	/**
	 * 删除图片
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteTbkpicById(Long[] ids,Long userId, Map<String, Object> map) {
		int num = tbkpicDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			LogCommon.saveLog(userId, "删除", "tbk图片表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "请检查数据是否正确，再进行操作！");
		}
	}

	/**
	 * 根据文章id查询出文章的代表图片
	 * @param docId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TbkPic queryTbkpicByDocId(Long docId) {
		String hql="select new com.njq.common.model.po.TbkPic(p.id,p.name,p.url,p.inTurn,p.status,p.createBy,p.createDate) " +
				"from TbkPic p ,TbkDocPicConfig c where p.id=c.picId and p.status=1 and c.status=1 and c.docId=:docId ";
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("docId", docId);
		List<TbkPic> tbkPicList = (List<TbkPic>)tbkpicDao.queryHqlByParam(hql, paramMap);
		return tbkPicList.size()>0?tbkPicList.get(0):null;
	}

	/**
	 * 随机从转载图片堆中抽取一张图片
	 * @return
	 */
	public Long queryZhuanPicForRandom() {
		String sql="select * from TbkPic where ptype=2  order by rand() limit 1";
		List<Map<String, Object>> pic = tbkpicDao.querySqlByParamForMap(sql, null);
		return Long.parseLong(pic.get(0).get("id").toString()) ;
	}

	/**
	 * 随机从原创图片堆中抽取一张图片
	 * @return
	 */
	public Long queryYuanPicForRandom() {
		String sql="select * from TbkPic where ptype=1  order by rand() limit 1";
		List<Map<String, Object>> pic = tbkpicDao.querySqlByParamForMap(sql, null);
		return Long.parseLong(pic.get(0).get("id").toString()) ;
	}
	
	
}
