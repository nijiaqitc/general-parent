package com.njq.basis.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.model.po.BaseTitle;

@Component("baseSaveTitlePerformer")
public class BaseSaveTitlePerformerImpl implements SaveTitlePerformer {
	private static final Logger logger = LoggerFactory.getLogger(BaseSaveTitlePerformerImpl.class);
	@Resource
	private DaoCommon<BaseTitle> baseTitleDao;

	@Override
	public BaseTitle saveTitle(SaveTitleRequest request) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("title", request.getMenu().getName());
		condition.addEqParam("typeId", request.getTypeId());
		condition.addEqParam("channel", request.getChannel().getValue());
		List<BaseTitle> titleList = baseTitleDao.queryColumnForList(condition);
		String suffix = "";
		if (!CollectionUtils.isEmpty(titleList)) {
			// 给名称加后缀，避免出现重名的标题
			suffix = titleList.size() + "";
		}
		BaseTitle title = new BaseTitle();
		try {
			title.setParantId(request.getParentId());
			title.setTitle(request.getMenu().getName() + suffix);
			title.setApply(0);
			title.setCreateDate(new Date());
			title.setChannel(ChannelType.YH_WIKI.getValue());
			title.setCreateBy(ConstantsCommon.Oper_User.ADMIN);
			title.setModiBy(ConstantsCommon.Oper_User.ADMIN);
			title.setTypeId(request.getTypeId());
			title.setTips(request.getTips());
			baseTitleDao.save(title);
		} catch (Exception e) {
			logger.info("加载下载菜单出错", e);
		}
		return title;
	}

	@Override
	public void updateTitleOnLoadSuccess(String docId, Long id) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addsetColumParam("docId", docId);
		condition.addEqParam("id", id);
		baseTitleDao.update(condition);
	}

	@Override
	public BaseTitle getTitleById(Long id) {
		BaseTitle title = baseTitleDao.queryTById(id);
		return title;
	}

	@Override
	public BaseTitle getTitleByDocId(Long docId) {
		return null;
	}

	@Override
	public BaseTitle updateTitle(SaveTitleRequest request) {
		return null;
	}

	@Override
	public List<BaseTitle> getTitleList(Long docId,String channel) {
		return null;
	}
	
	@Override
	public int getChildrenCount(Long docId,String channel) {
		return 0;
	}
}
