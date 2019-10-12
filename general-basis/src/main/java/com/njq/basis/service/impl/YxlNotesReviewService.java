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
import com.njq.common.model.po.YxlNotesReview;
import com.njq.common.util.string.StringUtil2;

@Service
public class YxlNotesReviewService {
	@Resource
	private DaoCommon<YxlNotesReview> yxlNotesReviewDao;
	
	public YxlNotesReview queryById(Long id) {
		return yxlNotesReviewDao.queryTById(id);
	}
	
	public PageList<YxlNotesReview> queryList(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addSetOrderColum("index1", "asc");
		return yxlNotesReviewDao.queryForPage(cc);
	}
	
	public void saveNotes(String doc,Integer index,Long chunkId,String general) {
		YxlNotesReview review = new YxlNotesReview();
		review.setChunkId(chunkId);
		review.setDoc(doc);
		review.setIndex1(index);
		review.setGeneral(general);
		review.setCreateDate(new Date());
		yxlNotesReviewDao.save(review);
	}
	
	
	public void updateNotes(Long id,String doc,int index,Long chunkId,String general) {
		if(id == null) {
			return;
		}
		YxlNotesReview review = new YxlNotesReview();
		review.setId(id);
		if(StringUtil2.IsNotEmpty(doc)) {
			review.setDoc(doc);
		}
		review.setIndex1(index);
		review.setChunkId(chunkId);
		review.setGeneral(general);
		yxlNotesReviewDao.updateByPrimaryKeySelective(review);
	}
	
	public void deleteNotes(Long id) {
		yxlNotesReviewDao.delTRealById(id);
	}
	
	public YxlNotesReview queryFirstReview(Long chunkId) {
		ConditionsCommon conditionsCommon = new ConditionsCommon();
		conditionsCommon.addEqParam("chunkId", chunkId);
		conditionsCommon.addSetOrderColum("index1", "asc");
		return yxlNotesReviewDao.queryTByParamForOne(conditionsCommon);
	}

	public YxlNotesReview queryLastReview(Long chunkId) {
		ConditionsCommon conditionsCommon = new ConditionsCommon();
		conditionsCommon.addEqParam("chunkId", chunkId);
		conditionsCommon.addSetOrderColum("index1", "desc");
		return yxlNotesReviewDao.queryTByParamForOne(conditionsCommon);
	}
	
	public YxlNotesReview queryNextReview(Long id) {
		YxlNotesReview review = queryById(id);
		ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addGtParam("index1", review.getIndex1());
        conditionsCommon.addEqParam("chunkId", review.getChunkId());
        conditionsCommon.addSetOrderColum("index1", "asc");
        conditionsCommon.addPageParam(1, 1);
        List<YxlNotesReview> list = yxlNotesReviewDao.queryColumnForList(conditionsCommon);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public YxlNotesReview queryBeforeReview(Long id) {
		YxlNotesReview review = queryById(id);
		ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addLtParam("index1", review.getIndex1());
        conditionsCommon.addEqParam("chunkId", review.getChunkId());
        conditionsCommon.addSetOrderColum("index1", "desc");
        conditionsCommon.addPageParam(1, 1);
        List<YxlNotesReview> list = yxlNotesReviewDao.queryColumnForList(conditionsCommon);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
