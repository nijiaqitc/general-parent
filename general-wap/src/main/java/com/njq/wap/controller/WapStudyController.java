package com.njq.wap.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.model.vo.YxlStudyVO;

@RequestMapping("wapStudy")
@Controller
public class WapStudyController {
	
	@Resource
	private YxlStudyService yxlStudyService;
	
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
		return "wap/studyDoc";
	}
	
}
