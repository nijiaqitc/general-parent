package com.njq.basis.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.BaseTip;

@Service
public class BaseTipService {
	@Autowired
	private DaoCommon<BaseTip> baseTipDao;

	/**
	 * 校验是否存在标签，没有则进行保存
	 * 
	 * @param tipName
	 * @return
	 */
	public Long checkAndSave(String tipName) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("tipName", tipName);
		BaseTip tip = baseTipDao.queryTByParamForOne(condition);
		if (tip == null) {
			tip = new BaseTip();
			tip.setCreateDate(new Date());
			tip.setTipName(tipName);
			baseTipDao.save(tip);
		}
		return tip.getId();
	}

	/**
	 * 保存批量标签（标签之间用,分割）
	 * 
	 * @param tipNames
	 * @return
	 */
	public String checkAndSaveTips(String tipNames) {
		if (StringUtils.isBlank(tipNames)) {
			return StringUtils.EMPTY;
		}
		String tips[] = tipNames.split(",");
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < tips.length; i++) {
			stb.append(this.checkAndSave(tips[i]) + ",");
		}
		return stb.substring(0, stb.length() - 1);
	}
}
