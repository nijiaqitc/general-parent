package com.njq.wap.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.vo.YxlStudyVO;

@RequestMapping("wapStudy")
@Controller
public class WapStudyController {
	
	@Resource
	private YxlStudyService yxlStudyService;
	
	@RequestMapping("subject")
	public String page(Model model,Long typeId,String titleType,
			@RequestParam(required=false,defaultValue="1") Integer page,
			@RequestParam(required=false,defaultValue="100") Integer size,
			@RequestParam(required=false,defaultValue="false") Boolean showTitle ,
			@RequestParam(required=false,defaultValue="false")  Boolean needRange,
			@RequestParam(required=false,defaultValue="false")  Boolean needStudy) {
		PageList<YxlStudyVO> pag = null;
		if(needRange) {
			pag = yxlStudyService.queryRandomStudyInfoPage(typeId, titleType, needStudy, page, size);
		}else {
			pag = yxlStudyService.queryStudyInfoPage(typeId, titleType, needStudy, page, size);
		}
		
		if(typeId != null) {
			model.addAttribute("titleTypeInfo", yxlStudyService.getTypeById(typeId));
		}
		model.addAttribute("studyList", pag.getList());
		model.addAttribute("showTitle", showTitle);
		model.addAttribute("page", page);
		model.addAttribute("total", pag.getTotal());
		model.addAttribute("req", "typeId="+typeId+"&titleType="+titleType
				+"&showTitle="+showTitle+"&needRange="+needRange+"&needStudy="+needStudy);
		return "wap/studyDoc";
	}
}
