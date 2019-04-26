package com.njq.yxl.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.IpUtil;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlType;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;
import com.njq.common.util.string.StringUtil2;
import com.njq.yxl.cache.ExaminationsCacheReader;

@RequestMapping("study")
@Controller
public class StudyController {

	@Resource
	private YxlStudyService yxlStudyService;
	@Resource
	private ExaminationsCacheReader examinationsCacheReader;
	
	@RequestMapping
	public String index(Model model) {
		List<YxlType>  list = yxlStudyService.queryTypeList();
		model.addAttribute("typeList", list);
		return "zxgj/reviseIndex";
	}
	
	@RequestMapping("subject")
	public String page(Model model,Long typeId,String titleType,
			@RequestParam(required=false,defaultValue="1") Integer page,
			@RequestParam(required=false,defaultValue="10") Integer size,
			@RequestParam(required=false,defaultValue="false") Boolean showTitle ,
			@RequestParam(required=false,defaultValue="false")  Boolean needRange,
			@RequestParam(required=false,defaultValue="false")  Boolean needStudy) {
		PageList<YxlStudyVO> pg = null;
		if(needRange) {
			pg = yxlStudyService.queryStudyInfoPage(typeId, titleType, needStudy, page, size);
		}else {
			pg = yxlStudyService.queryRandomStudyInfoPage(typeId, titleType, needStudy, page, size);
		}
		
		if(typeId != null) {
			model.addAttribute("titleTypeInfo", yxlStudyService.getTypeById(typeId));
		}
		model.addAttribute("studyList", pg.getList());
		model.addAttribute("showTitle", showTitle);
		model.addAttribute("page", page);
		model.addAttribute("total", pg.getTotal());
		model.addAttribute("req", "typeId="+typeId+"&titleType="+titleType
				+"&page="+page+"&size="+size+"&showTitle="+showTitle
				+"&needRange="+needRange+"&needStudy="+needStudy);
		return "zxgj/reviseContext";
	}
	
	
	@RequestMapping("examinations")
	public String examinations(Model model,
			@RequestParam(required=false,defaultValue="false") Boolean change,HttpServletRequest request) {
		String key = StringUtil2.format("examinations-ip-{0}", IpUtil.getIpAddr(request));
		Map<String, Object> examap;
		if(change) {
			examap = examinationsCacheReader.getOrsetData(key);
		}else {
			examap = examinationsCacheReader.get(key);
		}
		model.addAttribute("examap", examap);
		return "zxgj/examinations";
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
