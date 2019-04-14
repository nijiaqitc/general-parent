package com.njq.yxl.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.YxlStudyAnswer;
import com.njq.common.model.po.YxlStudyTitle;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.AnswerVO;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;

@Service
public class YxlReviseService {

	@Autowired
    private DaoCommon<YxlStudyTitle> yxlStudyTitleDao;
	@Autowired
    private DaoCommon<YxlStudyAnswer> yxlStudyAnswerDao;
	@Resource
    private DaoCommon<YxlType> yxlTypeDao;
	
	
	public List<YxlStudyTitleVO> queryTitleList(String typeId,String titleType){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("typeId", typeId);
		cc.addEqParam("titleType", titleType);
		List<YxlStudyTitle> titlePage = yxlStudyTitleDao.queryColumnForList(cc);
		if(CollectionUtils.isEmpty(titlePage)) {
			return Collections.EMPTY_LIST;
		}
		return titlePage.stream().map(n->{
			YxlStudyTitleVO vo = new YxlStudyTitleVO();
			BeanUtils.copyProperties(n, vo);
			return vo;
		}).collect(Collectors.toList());
	}
	
	public List<YxlStudyTitleVO> queryRandomTitleList(String typeId,String titleType){
		List<YxlStudyTitleVO> list = this.queryTitleList(typeId, titleType);
		Collections.shuffle(list);
		return list;
	}
	
	public List<YxlStudyVO> queryStudyInfoList(Long typeId,String titleType){
		ConditionsCommon cc=new ConditionsCommon();
		if(typeId != null) {
			cc.addEqParam("typeId", typeId);
		}
		cc.addEqParam("titleType", titleType);
		List<YxlStudyTitle> titlePage = yxlStudyTitleDao.queryColumnForList(cc);
		if(CollectionUtils.isEmpty(titlePage)) {
			return Collections.EMPTY_LIST;
		}
		return titlePage.stream().map(n->{
			YxlStudyVO vo = new YxlStudyVO();
			BeanUtils.copyProperties(n, vo);
			ConditionsCommon condition=new ConditionsCommon();
			condition.addEqParam("titleId", n.getId());
			List<YxlStudyAnswer> answerList = yxlStudyAnswerDao.queryColumnForList(condition);
			vo.setAnswerList(
			answerList.stream().map(m->{
				AnswerVO v = new AnswerVO();
				v.setAnswer(m.getAnswer());
				v.setColumDesc(m.getColumDesc());
				return v;
			}).collect(Collectors.toList()));
			return vo;
		}).collect(Collectors.toList());
	}
	
	public void updateToNeedStudy(Long id,Boolean type) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setId(id);
		t.setIsNeedStudy(type);
		yxlStudyTitleDao.updateByPrimaryKeySelective(t);
	}
	
	public List<YxlStudyVO> queryRandomStudyInfoList(Long typeId,String titleType){
		List<YxlStudyVO> list = this.queryStudyInfoList(typeId, titleType);
		Collections.shuffle(list);
		return list;
	}
	
	
	public List<YxlType> queryTypeList(){
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("parentId", 81L);
		return yxlTypeDao.queryColumnForList(condition);
	}
	
	public YxlType getTypeById(Long id) {
		return yxlTypeDao.queryTById(id);
	}
	
}
