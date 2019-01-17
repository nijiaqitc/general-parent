package com.njq.admin.controller;

/**
 * 用户操作日志接口
 */
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.BaseLogService;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.BaseLog;
import com.njq.common.util.date.DateUtil;

@RequestMapping("operLog")
@Controller
public class OperLogController {

	@Resource
	public BaseLogService logService;

	/**
	 * 跳转到后台用户日志
	 * 
	 * @return 2015-11-2 author njq
	 */
	@RequestMapping(value = "operLog", method = RequestMethod.GET)
	public String jumpToPage(Model model) {
		return "back/sysManage/dataCode/userLog";
	}

	/**
	 * 对日志信息进行分页查询
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @return 2016-1-5 author njq
	 */
	@RequestMapping(value = "getLogList", method = RequestMethod.POST)
	public @ResponseBody PageList<BaseLog> getRuleList(Model model, String searchValue, String start, String end,
			@RequestParam(required = false, defaultValue = "-1") int page,
			@RequestParam(required = false, defaultValue = "-1") int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(searchValue)) {
			map.put("searchValue", searchValue);
		}
		if (StringUtils.isNotBlank(start)) {
			map.put("start", start);
		}
		if (StringUtils.isNotBlank(end)) {
			map.put("end", end);
		}
		// 获取日志的分页列表
		PageList<BaseLog> logList = logService.queryAllLog(map, page, size);
		return logList;
	}

	/**
	 * 下载日志
	 * 
	 * @param model
	 * @param start
	 * @param end
	 * @param searchValue
	 * @return 2016-1-6 author njq
	 */
	@RequestMapping(value = "downLoadLog", method = RequestMethod.POST)
	public void downLoadLog(Model model, String start, String end, String searchValue, HttpServletRequest req,
			HttpServletResponse resp) {
		/*
		 * 如果开始日期不为空
		 */
		if (StringUtils.isEmpty(start)) {
			start = DateUtil.toDateString2(new Date());
		}
		if (StringUtils.isEmpty(end)) {
			end = DateUtil.toDateString2(DateUtil.getTromowDate());
		}
		Workbook wb = logService.downLoadLog(start, end, searchValue);
		try {
			resp.reset();
			String fileName = "log-" + System.currentTimeMillis() + ".xlsx";
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			resp.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = resp.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping("demo")
	public String test() {
		return "back/demo";
	}
}
