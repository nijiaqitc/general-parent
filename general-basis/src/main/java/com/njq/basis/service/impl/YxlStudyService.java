package com.njq.basis.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlStudyAnswer;
import com.njq.common.model.po.YxlStudyTitle;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.AnswerVO;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;

@Service
public class YxlStudyService {

	@Autowired
    private DaoCommon<YxlStudyTitle> yxlStudyTitleDao;
	@Autowired
    private DaoCommon<YxlStudyAnswer> yxlStudyAnswerDao;
	@Resource
    private DaoCommon<YxlType> yxlTypeDao;
	
	
	public PageList<YxlStudyTitleVO> queryTitlePage(int page , int size){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		PageList<YxlStudyTitle> titlePage = yxlStudyTitleDao.queryForPage(cc);
		List<YxlStudyTitleVO> voList = titlePage.getList().stream().map(n->{
			YxlStudyTitleVO vo = new YxlStudyTitleVO();
			BeanUtils.copyProperties(n, vo);
			return vo;
		}).collect(Collectors.toList());
		PageList<YxlStudyTitleVO> vpg = new PageList<>();
		vpg.setList(voList);
		vpg.setTotal(titlePage.getTotal());
		return vpg;
	}
	
	
	
	
	
	
	
	
	public YxlStudyVO getStudyInfo(Long id) {
		YxlStudyVO vo = new YxlStudyVO();
		YxlStudyTitle title = yxlStudyTitleDao.queryTById(id);
		if(title == null) {
			return null;
		}
		BeanUtils.copyProperties(title, vo);
		ConditionsCommon condition=new ConditionsCommon();
		condition.addEqParam("titleId", id);
		List<YxlStudyAnswer> answerList =  yxlStudyAnswerDao.queryColumnForList(condition);
		List<AnswerVO> voList = answerList.stream().map(n->{
			 AnswerVO v = new AnswerVO();
			 v.setAnswer(n.getAnswer());
			 v.setColumDesc(n.getColumDesc());
			 return v;
		}).collect(Collectors.toList());
		vo.setAnswerList(voList);
		return vo;
	}
	
	
	public void addInfo(String title,String titleType,Long typeId ,String answer,String columDesc) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setCreateDate(new Date());
		t.setIsNeedStudy(false);
		t.setTitle(title);
		t.setTitleType(titleType);
		t.setTypeId(typeId);
		yxlStudyTitleDao.save(t);
		
		YxlStudyAnswer a = new YxlStudyAnswer();
		a.setAnswer(answer);
		a.setColumDesc(columDesc);
		a.setCreateDate(new Date());
		a.setTitleId(t.getId());
		yxlStudyAnswerDao.save(a);
	}
	
	
	public void updateInfo(Long id , String title,String titleType,Long typeId ,String answer,String columDesc) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setId(id);
		t.setCreateDate(new Date());
		t.setIsNeedStudy(false);
		t.setTitle(title);
		t.setTitleType(titleType);
		t.setTypeId(typeId);
		yxlStudyTitleDao.updateByPrimaryKeySelective(t);
		ConditionsCommon cc = new ConditionsCommon();
		cc.addEqParam("titleId", t.getId());
		yxlStudyAnswerDao.deleteBycc(cc);
		
//		YxlStudyAnswer a = new YxlStudyAnswer();
//		a.setAnswer(answer);
//		a.setColumDesc(columDesc);
//		a.setCreateDate(new Date());
//		a.setTitleId(t.getId());
//		yxlStudyAnswerDao.updateByPrimaryKeySelective(a);
	}
	
	public List<YxlType> queryTypeList(){
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("parentId", 81L);
		return yxlTypeDao.queryColumnForList(condition);
	}
	
	
}
