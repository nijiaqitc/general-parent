package com.njq.basis.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseVisitRecord;

@Service
public class BaseVisitRecordService {
	@Resource
	private DaoCommon<BaseVisitRecord> visitRecordDao;
	
	public void addRecord(String ip,String method,String url,String contentType,String paramJson,String param ) {
		BaseVisitRecord record = new BaseVisitRecord();
		record.setIp(ip);
		record.setMethod(method);
		record.setContentType(contentType);
		record.setParam(param);
		record.setParamJson(paramJson);
		record.setUrl(url);
		record.setCreateBy(1L);
		record.setCreateDate(new Date());
		record.setModiBy(1L);
		visitRecordDao.save(record);
	}
	

}
