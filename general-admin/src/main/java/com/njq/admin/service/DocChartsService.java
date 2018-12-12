package com.njq.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.admin.common.UserCommon;
import com.njq.common.util.date.DateUtil;
import com.njq.xs.service.XsTitleDetailService;
import com.njq.yxl.service.YxlDocSearchService;

@Service
public class DocChartsService {

	@Resource
	private YxlDocSearchService yxlDocSearchService;
	@Resource
	private XsTitleDetailService XsTitleDetailService;

	public Map<String, List<String>> queryyxlDocCharts() {
		Date day = new Date();
		List<String> dl = new ArrayList<String>();
		List<String> xl = new ArrayList<String>();
		List<String> time = new ArrayList<String>();
		Map<String, Object> m;
		Date tempDates, tempDatee;
		for (int i = 12; i >= 0; i--) {
			time.add(DateUtil.toDateString7(DateUtil.getNMonthDateSt(day, -i)));
			tempDates = DateUtil.getNMonthDateSt(day, -i);
			tempDatee = DateUtil.getNMonthDateSt(day, 1 - i);
			m = yxlDocSearchService.queryDocAccountMonth(DateUtil.toDateString2(tempDates),
					DateUtil.toDateString2(tempDatee), UserCommon.getUserId());
			dl.add(m.get("uxl").toString());
			xl.add(m.get("xl").toString());
		}
		Map<String, List<String>> ms = new HashMap<String, List<String>>();
		ms.put("time", time);
		ms.put("dl", dl);
		ms.put("xl", xl);
		return ms;
	}

	/**
	 * 查询小说统计
	 * 
	 * @return
	 */
	public Map<String, List<String>> queryXsCharts() {
		Date day = new Date();
		List<String> nums = new ArrayList<String>();
		List<String> time = new ArrayList<String>();
		Map<String, Object> m;
		Date tempDates, tempDatee;
		for (int i = 12; i >= 0; i--) {
			time.add(DateUtil.toDateString7(DateUtil.getNMonthDateSt(day, -i)));
			tempDates = DateUtil.getNMonthDateSt(day, -i);
			tempDatee = DateUtil.getNMonthDateSt(day, 1 - i);
			m = XsTitleDetailService.queryDocAccountMonth(DateUtil.toDateString2(tempDates),
					DateUtil.toDateString2(tempDatee), UserCommon.getUserId());
			nums.add(m.get("num").toString());
		}
		Map<String, List<String>> ms = new HashMap<String, List<String>>();
		ms.put("time", time);
		ms.put("nums", nums);
		return ms;
	}

}
