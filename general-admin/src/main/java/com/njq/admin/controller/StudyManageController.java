package com.njq.admin.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;

@RequestMapping("admin/studyManage")
@Controller
public class StudyManageController {

	@Resource
	private YxlStudyService yxlStudyService;
	
	@RequestMapping
	public String page(Model model) {
		model.addAttribute("typeList", yxlStudyService.queryTypeList());
		return "back/issueDoc/reviseContext";
	}
	
	@ResponseBody
	@RequestMapping("queryTitleList")
	public PageList<YxlStudyTitleVO> queryTitleList(Model model,
			Long stTypeId,String stTitleType,String searchValue,
			@RequestParam(required = false, defaultValue = "0") Integer stSure,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size){
		return yxlStudyService.queryTitlePage(page, size,stTypeId,stTitleType,searchValue,stSure);
	}
	
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public Map<String, Object> addOrUpdate(Long id, String title,String answer,
			String titleType,Long typeId,String columDesc,
			@RequestParam(required = false , defaultValue = "false")  Boolean sure) {
		try {
			if(id == null) {
				yxlStudyService.addInfo(title, titleType, typeId, answer, columDesc,sure);
			}else {
				yxlStudyService.updateInfo(id, title, titleType, typeId, answer, columDesc,sure);
			}
			
		} catch (Exception e) {
			return MessageCommon.getFalseMap(e.getMessage());
		}
		return  MessageCommon.getSuccessMap();
	}
	
	
	@ResponseBody
	@RequestMapping("queryInfo")
	public YxlStudyVO queryInfo(Model model,Long id){
		return yxlStudyService.getStudyInfo(id);
	}
	
	@RequestMapping("loadEditor")
	public String loadEditor() {
		return "back/issueDoc/reviseEditor";
	}
	
	@ResponseBody
	@RequestMapping("delSubject")
	public Map<String, Object> delSubject(Long[] ids){
		if(ids != null && ids.length > 0) {
			yxlStudyService.deleteSubject(ids);			
		}
		return MessageCommon.getSuccessMap();
	}
}
