package com.njq.basis.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.BaseLog;
import com.njq.common.model.vo.LogVO;
import com.njq.common.util.date.DateUtil;
@Service
public class BaseLogService {
	
	@Resource
	private DaoCommon<BaseLog> logDao;
	
	/**
	 * 查询所有日志（分页）
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseLog> queryAllLog(Map<String, Object> paramMap, int page , int size) {
		/*ConditionsCommon cc1=new ConditionsCommon();
		cc1.addBetweenParam("id", 10L, 20L);
		List<Log> list = logDao.queryTByParam(cc1);
		System.out.println("测------试:"+list.size());*/
		
		/*ConditionsCommon cc1=new ConditionsCommon();
		cc1.addPageParam(page, size);
		String hql="select l.id as id ,l.type as type,u.userName as userName from Log l , User u where l.userId=u.id order by l.id desc ";
		PageList<Map<String, Object>> ll=(PageList<Map<String, Object>>)logDao.queryCustomHqlForPage(hql, cc1);
		System.out.println(ll);*/
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, size);
		try {
			if(paramMap.get("start")!=null){
				cc.addGteParam("modiDate", DateUtil.toDate1(paramMap.get("start")+""));
			}
			if(paramMap.get("end")!=null){
				cc.addLteParam("modiDate", DateUtil.toDate1(paramMap.get("end")+""));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cc.addSetOrderColum("id","desc");
		if(paramMap.get("searchValue")!=null){
			cc.addMoreColumLikeParam(paramMap.get("searchValue").toString(), "userId","type","operTable","operCon");
		}
		return logDao.queryForPage(cc);
	}

	/**
	 * 根据id检索出日志
	 * @param id
	 * @return
	 */
	public BaseLog queryLogById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 日志记录
	 * @param userId
	 * @param type
	 * @param operTable
	 * @param operCon
	 */
	public void saveLog(Long userId, String type, String operTable,
			String operCon) {
		BaseLog log=new BaseLog();
		log.setUserId(userId);
		log.setType(type);
		log.setOperCon(operCon);
		log.setOperTable(operTable);
		log.setCreateBy(userId);
		log.setCreateDate(new Date());
		log.setModiBy(userId);
		logDao.save(log);
	}

	/**
	 * 日志修改
	 * @param log
	 */
	public void updateLog(BaseLog log) {
		// TODO Auto-generated method stub
	}

	/**
	 * 删除日志
	 * @param id
	 * @param map
	 */
	public void deleteLogById(Long id, Map<String, Object> map) {
		// TODO Auto-generated method stub
	}

	/**
	 * 导出日志
	 * @param start
	 * @param end
	 * @param searchValue
	 * @return
	 */
	public Workbook downLoadLog(String start, String end, String searchValue) {
		String[] proExcelTitles={"ID","用户","表名","动作","内容","操作时间"};
		int[] proExcelCellsWidth={100,100,100,100,400,200};
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("日志信息");
		//创建第一行
		Row headerRow0 = sheet.createRow(0);
		headerRow0.createCell(0).setCellValue("日志信息");
		//创建第二行
		Row headerRow = sheet.createRow(1);
		CellStyle style= wb.createCellStyle();
		//单元格样式
		style.setAlignment(HorizontalAlignment.CENTER);
		headerRow0.getCell(0).setCellStyle(style);
		for (int i = 0; i < proExcelTitles.length; i++) {
			//创建单元格
			Cell cell = headerRow.createCell(i);
			//设置单元格值
			cell.setCellValue(proExcelTitles[i]);
			//本身宽度是按照字符长度设置的，这里按照35.7*像素，基本和最终设置像素相近
			sheet.setColumnWidth(i, (short) (35.7 * proExcelCellsWidth[i]));
			cell.setCellStyle(style);
		}
		makeHaveUser(start, end, searchValue, wb);
        return wb;
	}
	
	/**
	 * 生成寸在用户的操作记录
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param searchValue 搜索
	 * @param wb
	 * 2016-1-7
	 * author njq
	 */
	@SuppressWarnings({ "unchecked" })
	private void makeHaveUser(String start, String end, String searchValue,Workbook wb){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map.put("start", DateUtil.toDate1(start));
			map.put("end", DateUtil.toDate1(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String sql=" select new com.njq.common.model.vo.LogVO(l.id,l.userId,l.type,l.operTable,l.operCon,l.modiDate,u.userName) " +
				" from BaseLog l , User u where l.userId=u.id and l.modiDate>=:start and l.modiDate<=:end ";
		if(searchValue!=null&&searchValue!=""){
			sql+=" and ( concat(l.userId, '-', l.type, '-', l.operTable, '-', l.operCon ) like :likeParam ) ";
			map.put("likeParam", "%"+searchValue+"%");
		}
		sql+=" order by l.id desc ";
		List<LogVO> voList = (List<LogVO>)logDao.queryHqlByParam(sql, map);
		Sheet sheet=wb.getSheetAt(0);
		CellStyle style=wb.getCellStyleAt((short)0);
         /*
          *  循环遍历消费信息，生成excel文件 
          */
        for(int i=0 ; i<voList.size();i++){
            Row row = sheet.createRow(i+2);
            row.setRowStyle(style);
            row.createCell(0).setCellValue(voList.get(i).getId());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(voList.get(i).getUserName());
            row.getCell(1).setCellStyle(style);
            row.createCell(2).setCellValue(voList.get(i).getOperTable());
            row.getCell(2).setCellStyle(style);
            row.createCell(3).setCellValue(voList.get(i).getType());
            row.getCell(3).setCellStyle(style);
            row.createCell(4).setCellValue(voList.get(i).getOperCon());
            row.getCell(4).setCellStyle(style);
            row.createCell(5).setCellValue(voList.get(i).getFormatDate());
            row.getCell(5).setCellStyle(style);
        }
        //合并excel单元格
        sheet.addMergedRegion(new CellRangeAddress(voList.size()+2, // 起始行
        						voList.size()+2, // 结束行
        		                 0, // 起始列
        		                 5  // 结束列
        		                 ));
        sheet.addMergedRegion(new CellRangeAddress(
				                 0, // 起始行
				                 0, // 结束行
				                 0, // 起始列
				                 5  // 结束列
				                 ));
        makeSystemUser(start, end, searchValue, wb,voList.size()+3);
	}
	
	/**
	 * 系统操作记录
	 * @param start
	 * @param end
	 * @param searchValue
	 * @param wb
	 * @param k
	 * 2016-1-7
	 * author njq
	 */
	private void makeSystemUser(String start, String end, String searchValue,Workbook wb,int k){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("userId", 0L);
		try {
			cc.addGteParam("modiDate", DateUtil.toDate1(start));
			cc.addLteParam("modiDate", DateUtil.toDate1(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(searchValue!=null&&searchValue!=""){
			cc.addMoreColumLikeParam(searchValue, "userId","type","operTable","operCon");
		}
		cc.addSetOrderColum("id","desc");
		List<BaseLog> logList = logDao.queryTByParam(cc);
		Sheet sheet=wb.getSheetAt(0);
		CellStyle style=wb.getCellStyleAt((short)0);
         /*
          *  循环遍历消费信息，生成excel文件 
          */
        for(int i=0 ; i<logList.size();i++){
            Row row = sheet.createRow(i+k);
            row.setRowStyle(style);
            row.createCell(0).setCellValue(logList.get(i).getId());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(logList.get(i).getUserId());
            row.getCell(1).setCellStyle(style);
            row.createCell(2).setCellValue(logList.get(i).getOperTable());
            row.getCell(2).setCellStyle(style);
            row.createCell(3).setCellValue(logList.get(i).getType());
            row.getCell(3).setCellStyle(style);
            row.createCell(4).setCellValue(logList.get(i).getOperCon());
            row.getCell(4).setCellStyle(style);
            row.createCell(5).setCellValue(logList.get(i).getFormatDate());
            row.getCell(5).setCellStyle(style);
        }
        //合并excel单元格
        sheet.addMergedRegion(new CellRangeAddress(logList.size()+k, // 起始行
        						logList.size()+k, // 结束行
        		                 0, // 起始列
        		                 5  // 结束列
        		                 ));
        
        makeNotHaveUser(start, end, searchValue, wb, k+logList.size()+1);
	}
	
	/**
	 * 黑客操作记录
	 * @param start
	 * @param end
	 * @param searchValue
	 * @param wb
	 * @param k
	 * 2016-1-7
	 * author njq
	 */
	private void makeNotHaveUser(String start, String end, String searchValue,Workbook wb,int k){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addIsNullParam("userId");
		try {
			cc.addGteParam("modiDate", DateUtil.toDate1(start));
			cc.addLteParam("modiDate", DateUtil.toDate1(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(searchValue!=null&&searchValue!=""){
			cc.addMoreColumLikeParam(searchValue, "userId","type","operTable","operCon");
		}
		cc.addSetOrderColum("id","desc");
		List<BaseLog> logList = logDao.queryTByParam(cc);
		Sheet sheet=wb.getSheetAt(0);
		CellStyle style=wb.getCellStyleAt((short)0);
         /*
          *  循环遍历消费信息，生成excel文件 
          */
        for(int i=0 ; i<logList.size();i++){
            Row row = sheet.createRow(i+k);
            row.setRowStyle(style);
            row.createCell(0).setCellValue(logList.get(i).getId());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(logList.get(i).getUserId()==null?"黑客":logList.get(i).getUserId()+"");
            row.getCell(1).setCellStyle(style);
            row.createCell(2).setCellValue(logList.get(i).getOperTable());
            row.getCell(2).setCellStyle(style);
            row.createCell(3).setCellValue(logList.get(i).getType());
            row.getCell(3).setCellStyle(style);
            row.createCell(4).setCellValue(logList.get(i).getOperCon());
            row.getCell(4).setCellStyle(style);
            row.createCell(5).setCellValue(logList.get(i).getFormatDate());
            row.getCell(5).setCellStyle(style);
        }
        //合并excel单元格
        sheet.addMergedRegion(new CellRangeAddress(logList.size()+k, // 起始行
        						logList.size()+k, // 结束行
        		                 0, // 起始列
        		                 5  // 结束列
        		                 ));
	}
}
