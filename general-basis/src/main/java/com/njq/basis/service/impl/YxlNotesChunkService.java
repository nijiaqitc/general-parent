package com.njq.basis.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlNotesChunk;

@Service
public class YxlNotesChunkService {

	@Resource
	private DaoCommon<YxlNotesChunk> yxlNotesChunkDao;
	
	public PageList<YxlNotesChunk> queryList(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addSetOrderColum("id", "asc");
		return yxlNotesChunkDao.queryForPage(cc);
	}
	
	public void saveNotes(String name,int index1,int index2) {
		YxlNotesChunk chunk = new YxlNotesChunk();
		chunk.setName(name);
		chunk.setIndex1(index1);
		chunk.setIndex2(index2);
		chunk.setCreateDate(new Date());
		yxlNotesChunkDao.save(chunk);
	}
	
	
	public void updateNotes(int index1,int index2,Long id,String name) {
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
	
}
