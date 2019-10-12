package com.njq.basis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlNotesChunk;

@Service
public class YxlNotesChunkService {

	@Resource
	private DaoCommon<YxlNotesChunk> yxlNotesChunkDao;
	
	public YxlNotesChunk queryById(Long id) {
		return yxlNotesChunkDao.queryTById(id);
	}
	
	public PageList<YxlNotesChunk> queryList(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addSetOrderColum("id", "asc");
		return yxlNotesChunkDao.queryForPage(cc);
	}
	
	public List<YxlNotesChunk> queryAll(){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addSetOrderColum("index1", "asc");
		cc.addSetOrderColum("index2", "asc");
		return yxlNotesChunkDao.queryForListNoPage(cc);
	}
	
	public void saveChunk(String name,int index1,int index2) {
		YxlNotesChunk chunk = new YxlNotesChunk();
		chunk.setName(name);
		chunk.setIndex1(index1);
		chunk.setIndex2(index2);
		chunk.setCreateDate(new Date());
		yxlNotesChunkDao.save(chunk);
	}
	
	
	public void updateChunk(int index1,int index2,Long id,String name) {
		if(id == null) {
			return;
		}
		YxlNotesChunk chunk = new YxlNotesChunk();
		chunk.setId(id);
		chunk.setName(name);
		chunk.setIndex1(index1);
		chunk.setIndex2(index2);
		yxlNotesChunkDao.updateByPrimaryKeySelective(chunk);
	}
	
	public void deleteChunk(Long id) {
		yxlNotesChunkDao.delTRealById(id);
	}
	
	public YxlNotesChunk queryNextChunk(Long id) {
		YxlNotesChunk review = queryById(id);
		ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addGtParam("index1", review.getIndex1());
        conditionsCommon.addSetOrderColum("index1", "asc");
        conditionsCommon.addPageParam(1, 1);
        List<YxlNotesChunk> list = yxlNotesChunkDao.queryColumnForList(conditionsCommon);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public YxlNotesChunk queryBeforeChunk(Long id) {
		YxlNotesChunk review = queryById(id);
		ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addLtParam("index1", review.getIndex1());
        conditionsCommon.addSetOrderColum("index1", "desc");
        conditionsCommon.addPageParam(1, 1);
        List<YxlNotesChunk> list = yxlNotesChunkDao.queryColumnForList(conditionsCommon);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	
	public YxlNotesChunk queryLastChunk() {
		ConditionsCommon conditionsCommon = new ConditionsCommon();
		conditionsCommon.addSetOrderColum("index1", "desc");
		return yxlNotesChunkDao.queryTByParamForOne(conditionsCommon);
	}
}
