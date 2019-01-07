package com.njq.grab.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.model.po.GrabUrlInfo;

@Service
public class GrabUrlInfoService {
	@Autowired
	private DaoCommon<GrabUrlInfo> grabUrlInfoDao;
	
	public List<GrabUrlInfo> getInfoList(){
		ConditionsCommon condition = new ConditionsCommon();
		return grabUrlInfoDao.queryColumnForList(condition);
	}
	
	public GrabUrlInfo getUrlInfoByIndexPage(String indexPage) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("pageIndex", indexPage);
		GrabUrlInfo info =grabUrlInfoDao.queryTByParamForOne(condition);
		return info;
	}
	
	public GrabUrlInfo getUrlInfoByChannel(String channel) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("channel", channel);
		GrabUrlInfo info =grabUrlInfoDao.queryTByParamForOne(condition);
		return info;
	}
	
	public Long saveUrlInfo(String indexPage,String menuUrl,String typeName) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("pageIndex", indexPage);
		List<GrabUrlInfo> infoList =grabUrlInfoDao.queryColumnForList(condition);
		if(CollectionUtils.isEmpty(infoList)) {
			throw new BaseKnownException(ErrorCodeConstant.UN_BUILD_CODE,ErrorCodeConstant.UN_BUILD_MSG);
		}
		GrabUrlInfo info = infoList
				.stream()
				.filter(n->n.getMenuUrl().equals(menuUrl))
				.findAny()
				.orElse(null);
		if(info != null) {
			throw new BaseKnownException(ErrorCodeConstant.ALREADY_LOAD_CODE,ErrorCodeConstant.ALREADY_LOAD_MSG);
		}
		info = infoList.get(0);
		GrabUrlInfo urlInfo = new GrabUrlInfo();
		urlInfo.setCreateDate(new Date());
		urlInfo.setChannel(info.getChannel());
		urlInfo.setLoginUrl(info.getLoginUrl());
		urlInfo.setMenuUrl(menuUrl);
		urlInfo.setPageIndex(indexPage);
		urlInfo.setPwd(info.getPwd());
		urlInfo.setShortName(info.getShortName());
		urlInfo.setUserName(info.getUserName());
		urlInfo.setDataJson(info.getDataJson());
		urlInfo.setTypeName(typeName);
		grabUrlInfoDao.save(urlInfo);
		return urlInfo.getId();
	}
}
