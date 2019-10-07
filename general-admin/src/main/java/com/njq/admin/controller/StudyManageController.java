package com.njq.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.excel.ExcelReadHelper;
import com.njq.common.base.excel.model.ReadExcelRequest;
import com.njq.common.base.excel.model.ReadExcelRequestBuilder;
import com.njq.common.base.excel.model.SheetRowMode;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.vo.StudyExcelVO;
import com.njq.common.model.vo.YxlStudyTitleVO;
import com.njq.common.model.vo.YxlStudyVO;
import com.njq.common.util.string.StringUtil2;

@RequestMapping("admin/studyManage")
@Controller
public class StudyManageController {

	@Resource
	private YxlStudyService yxlStudyService;
	@Resource
	private ExcelReadHelper excelReadHelper;
	
	@RequestMapping("")
	public String page(Model model) {
		model.addAttribute("typeList", yxlStudyService.queryTypeList());
		return "back/issueDoc/reviseContext";
	}
	
	@RequestMapping("issueRevise")
	public String issueRevise(Model model,Long id) {
		model.addAttribute("typeList", yxlStudyService.queryTypeList());
		if(id != null) {
			model.addAttribute("studyInfo", yxlStudyService.getStudyInfo(id));			
		}
		return "back/issueDoc/issueRevise";
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
			String titleType,Long typeId,String columDesc,String general,String options,
			@RequestParam(required = false , defaultValue = "false")  Boolean sure) {
		try {
			if(id == null) {
				yxlStudyService.addInfo(title, titleType, typeId, answer, columDesc,sure,options,general);
			}else {
				yxlStudyService.updateInfo(id, title, titleType, typeId, answer, columDesc,sure,options,general);
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
	
	
	@ResponseBody
	@RequestMapping("upstudyexcel")
	public Map<String, Object> upstudyexcel(Model model, @RequestParam(value = "studyFiles") MultipartFile studyFiles){
		ReadExcelRequest<StudyExcelVO> readExcelRequest = new ReadExcelRequestBuilder<StudyExcelVO>()
                .ofExcel(studyFiles)
                .useMode(SheetRowMode.getDefaultMode())
                .ofReader(this::readRowData)
                .build();
		List<StudyExcelVO> lists = this.excelReadHelper.readExcel(readExcelRequest);
		yxlStudyService.upStudyTitle(lists);
		return MessageCommon.getSuccessMap();
	}
	
	private StudyExcelVO readRowData(Row row) {
		StudyExcelVO vo = new StudyExcelVO();
        Cell cell0 = row.getCell(0);
        cell0.setCellType(CellType.STRING);
        if(StringUtil2.isEmpty(cell0.getStringCellValue())) {
        	return vo;
        }
        vo.setTitle(cell0.getStringCellValue().trim());
        
        Cell cell3 = row.getCell(1);
        cell3.setCellType(CellType.STRING);
        if(StringUtil2.IsNotEmpty(cell3.getStringCellValue())) {
        	vo.setTypeId(Long.parseLong(cell3.getStringCellValue().trim()));        	
        }
        
        Cell cell1 = row.getCell(2);
        cell1.setCellType(CellType.STRING);
        if(StringUtil2.IsNotEmpty(cell1.getStringCellValue())) {
        	vo.setAnswer(cell1.getStringCellValue().trim());
        }
        
        Cell cell2 = row.getCell(3);
        cell2.setCellType(CellType.STRING);
        if(StringUtil2.IsNotEmpty(cell2.getStringCellValue())) {
        	vo.setGeneral(cell2.getStringCellValue().trim());        	
        }
        
        return vo;
    }
}
