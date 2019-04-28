package com.njq.basis.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.basis.service.cache.YxlStudyCacheManager;
import com.njq.basis.service.cache.YxlTypeCacheManager;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.dao.YxlStudyTitleJpaRepository;
import com.njq.common.model.po.YxlStudyAnswer;
import com.njq.common.model.po.YxlStudyTitle;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.AnswerVO;
import com.njq.common.model.vo.ExaminationsVO;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;
import com.njq.common.util.string.StringUtil;
import com.njq.common.util.string.StringUtil2;

@SuppressWarnings("unchecked")
@Service
public class YxlStudyService {

	@Autowired
    private DaoCommon<YxlStudyTitle> yxlStudyTitleDao;
	@Autowired
    private DaoCommon<YxlStudyAnswer> yxlStudyAnswerDao;
	@Resource
    private DaoCommon<YxlType> yxlTypeDao;
	@Resource
	private YxlTypeCacheManager yxlTypeCacheManager;
	@Resource
	private YxlStudyCacheManager yxlStudyCacheManager;
	@Resource
	private YxlStudyTitleJpaRepository yxlStudyTitleJpaRepository;
	
	public PageList<YxlStudyTitleVO> queryTitlePage(int page , int size,Long stTypeId,String stTitleType,String searchValue,Integer stSure){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		cc.addEqParam("titleType", stTitleType);
		if(stSure != 0) {
			if(stSure == 1) {
				cc.addEqParam("sure", true);			
			}else {
				cc.addEqParam("sure", false);
			}			
		}
		if(stTypeId > 0 ) {
			cc.addEqParam("typeId", stTypeId);			
		}
		if(!StringUtil.isEmpty(searchValue)) {
			cc.addColumMoreLikeParam("title", searchValue);			
		}
		cc.addSetOrderColum("id", "desc");
		PageList<YxlStudyTitle> titlePage = yxlStudyTitleDao.queryForPage(cc);
		List<YxlStudyTitleVO> voList = titlePage.getList().stream().map(n->{
			YxlStudyTitleVO vo = new YxlStudyTitleVO();
			BeanUtils.copyProperties(n, vo);
			vo.setTypeName(yxlTypeCacheManager.get(n.getTypeId()).getName());
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
	
	
	public void addInfo(String title,String titleType,Long typeId ,
			String answer,String columDesc,Boolean sure,
			String options, String general) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setCreateDate(new Date());
		t.setIsNeedStudy(false);
		t.setTitle(title);
		t.setTitleType(titleType);
		t.setTypeId(typeId);
		t.setSure(sure);
		t.setGeneral(general);
		t.setOptions(options);
		yxlStudyTitleDao.save(t);
		
		YxlStudyAnswer a = new YxlStudyAnswer();
		a.setAnswer(answer);
		a.setColumDesc(columDesc);
		a.setCreateDate(new Date());
		a.setTitleId(t.getId());
		yxlStudyAnswerDao.save(a);
	}
	
	
	public void updateInfo(Long id , String title,String titleType,Long typeId ,
			String answer,String columDesc,Boolean sure,
			String options, String general) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setId(id);
		t.setCreateDate(new Date());
		t.setIsNeedStudy(false);
		t.setTitle(title);
		t.setTitleType(titleType);
		t.setTypeId(typeId);
		t.setSure(sure);
		t.setOptions(options);
		t.setGeneral(general);
		yxlStudyTitleDao.updateByPrimaryKeySelective(t);
		ConditionsCommon cc = new ConditionsCommon();
		cc.addEqParam("titleId", t.getId());
		yxlStudyAnswerDao.deleteBycc(cc);
		
		YxlStudyAnswer a = new YxlStudyAnswer();
		a.setAnswer(answer);
		a.setColumDesc(columDesc);
		a.setCreateDate(new Date());
		a.setTitleId(t.getId());
		yxlStudyAnswerDao.save(a);
	}
	
	
	
	
	
	public List<YxlStudyTitleVO> queryTitleList(String typeId,String titleType){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("typeId", typeId);
		cc.addEqParam("titleType", titleType);
		cc.addSetOrderColum("id", "desc");
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
	
	
	
	public List<YxlStudyVO> queryStudyInfoList(Long typeId,String titleType,Boolean needStudy){
		ConditionsCommon cc=new ConditionsCommon();
		if(typeId != null) {
			cc.addEqParam("typeId", typeId);
		}
		cc.addEqParam("titleType", titleType);
		if(needStudy) {
			cc.addEqParam("isNeedStudy", needStudy);			
		}
		List<YxlStudyTitle> titlePage = yxlStudyTitleDao.queryColumnForList(cc);
		if(CollectionUtils.isEmpty(titlePage)) {
			return Collections.EMPTY_LIST;
		}
		return  this.convert(titlePage);
	}
	
	public void updateToNeedStudy(Long id,Boolean type) {
		YxlStudyTitle t = new YxlStudyTitle();
		t.setId(id);
		t.setIsNeedStudy(type);
		yxlStudyTitleDao.updateByPrimaryKeySelective(t);
	}
	
	public List<YxlStudyVO> queryRandomStudyInfoList(Long typeId,String titleType,Boolean needStudy){
		List<YxlStudyVO> list = this.queryStudyInfoList(typeId, titleType,needStudy);
		Collections.shuffle(list);
		return list;
	}
	
	
	public PageList<YxlStudyVO> queryStudyInfoPage(Long typeId,String titleType,Boolean needStudy,Integer page,Integer size){
		String key = StringUtil2.format("studyPage-{0}-{1}-{2}-{3}-{4}", typeId,titleType,needStudy,page,size);
		PageList<YxlStudyVO> pg = yxlStudyCacheManager.get(key);
		if(pg == null) {
			ConditionsCommon cc=new ConditionsCommon();
			if(typeId != null) {
				cc.addEqParam("typeId", typeId);
			}
			cc.addEqParam("titleType", titleType);
			if(needStudy) {
				cc.addEqParam("isNeedStudy", needStudy);			
			}
			cc.addPageParam(page, size);
			PageList<YxlStudyTitle> pglist = yxlStudyTitleDao.queryForPage(cc);
			pg = new PageList<YxlStudyVO>();
			pg.setList(this.convert(pglist.getList()));
			pg.setTotal(pglist.getTotal());
			yxlStudyCacheManager.update(key, pg);
		}
		return pg;
	}
	
	public PageList<YxlStudyVO> queryRandomStudyInfoPage(Long typeId,String titleType,Boolean needStudy,Integer page,Integer size){
		PageList<YxlStudyVO> pg = this.queryStudyInfoPage(typeId, titleType, needStudy, page, size);
		Collections.shuffle(pg.getList());
		return pg;
	}
	
	private List<YxlStudyVO> convert(List<YxlStudyTitle> titleList){
		return titleList.stream().map(n->{
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
	
	public List<YxlType> queryTypeList(){
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("parentId", 81L);
		return yxlTypeDao.queryColumnForList(condition);
	}
	
	public YxlType getTypeById(Long id) {
		return yxlTypeDao.queryTById(id);
	}
	
	
	
	public void deleteSubject(Long[] ids) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addInParam("id", ids);
		yxlStudyTitleDao.deleteBycc(condition);
		condition = new ConditionsCommon();
		condition.addInParam("titleId", ids);
		yxlStudyAnswerDao.deleteBycc(condition);
	}
	
	public Map<String, Object> getExam(){
		Map<String, Object> examap=new HashMap<>();
		examap.put("selectSub", this.queryExaminations());
		examap.put("questions", this.queryExaminationsQue());
		examap.put("penques", this.queryPenQue());
		return examap;
	}
	
	public List<YxlStudyVO> queryExaminationsQue() {
		List<YxlStudyTitle> titlePage = yxlStudyTitleJpaRepository.queryQues("1", PageRequest.of(0, 30));
		if(CollectionUtils.isEmpty(titlePage)) {
			return Collections.EMPTY_LIST;
		}
		return  this.convert(titlePage);
	}
	
	private List<ExaminationsVO> convertExa(List<YxlStudyTitle> titleList){
		return titleList.stream().map(n->{
			ExaminationsVO vo = new ExaminationsVO();
			BeanUtils.copyProperties(n, vo);
			ConditionsCommon condition=new ConditionsCommon();
			condition.addEqParam("titleId", n.getId());
			List<YxlStudyAnswer> answerList = yxlStudyAnswerDao.queryColumnForList(condition);
			String opstr = n.getOptions();
			vo.setOptionsub(generateSel(opstr));
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
	
	
	private List<String[]> generateSel(String str){
		List<String> lt = Arrays.asList(str.split("\\(\\|\\)"));
		return lt.stream().map(n->{
			if(StringUtil.IsNotEmpty(n)) {
				if(n.indexOf("(br)")>-1) {
					n=n.replaceAll("\\(br\\)", "\\<br\\>");					
				}
				return n.split("\\、");
			}else {
				return null;
			}
		}).filter(n->n!=null).collect(Collectors.toList());
	}
	
	public List<ExaminationsVO> queryExaminations() {
		List<YxlStudyTitle> titlePage = yxlStudyTitleJpaRepository.queryQues("2", PageRequest.of(0, 30));
		return this.convertExa(titlePage);
	}
	
	public YxlStudyVO queryPenQue() {
		List<YxlStudyTitle> titlePage = yxlStudyTitleJpaRepository.queryQues("3", PageRequest.of(0, 1));
		List<YxlStudyVO> lt = this.convert(titlePage);
		return lt.get(0);
	}
}
