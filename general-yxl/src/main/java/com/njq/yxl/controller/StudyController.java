package com.njq.yxl.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;

@RequestMapping("study")
@Controller
public class StudyController {

	@Resource
	private YxlStudyService yxlStudyService;
	
	@RequestMapping
	public String index(Model model) {
		List<YxlType>  list = yxlStudyService.queryTypeList();
		model.addAttribute("typeList", list);
		return "zxgj/reviseIndex";
	}
	
	@RequestMapping("subject")
	public String page(Model model,Long typeId,String titleType,
			@RequestParam(required=false,defaultValue="false") Boolean showTitle ,
			@RequestParam(required=false,defaultValue="false")  Boolean needRange,
			@RequestParam(required=false,defaultValue="false")  Boolean needStudy) {
		List<YxlStudyVO>  studyVoList = null;
		if(needRange) {
			studyVoList =  yxlStudyService.queryRandomStudyInfoList(typeId, titleType,needStudy);
		}else {
			studyVoList = yxlStudyService.queryStudyInfoList(typeId, titleType,needStudy);			
		}
		
		if(typeId != null) {
			model.addAttribute("titleTypeInfo", yxlStudyService.getTypeById(typeId));
		}
		model.addAttribute("studyList", studyVoList);
		model.addAttribute("showTitle", showTitle);
		return "zxgj/reviseContext";
	}
	
	@ResponseBody
	@RequestMapping("queryStydyInfo")
	public List<YxlStudyVO> queryStydyInfo(String type,Boolean needFlag){
		
		
		
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("queryStydyTitle")
	public List<YxlStudyTitleVO> queryStydyTitle(String type,Boolean needFlag){
		
		
		
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("queryCatalog")
	public List<YxlType> queryCatalog(){
		
		
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("setUnOrNeedStudy")
	public Map<String, Object> setUnOrNeedStudy(Long id,Boolean type){
		yxlStudyService.updateToNeedStudy(id, type);
		return MessageCommon.getSuccessMap();
	}
	
}
