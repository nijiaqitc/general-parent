package com.njq.basis.service.impl;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("grabSaveTitlePerformer")
public class GrabSaveTitlePerformerImpl implements SaveTitlePerformer {
    private static final Logger logger = LoggerFactory.getLogger(GrabSaveTitlePerformerImpl.class);
    @Resource
    private DaoCommon<BaseTitleGrab> baseTitleGrabDao;

    @Override
    public BaseTitle saveTitle(SaveTitleRequest request) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("title", request.getMenu().getName());
        condition.addEqParam("typeId", request.getTypeId());
        condition.addEqParam("channel", request.getChannel().getValue());
        List<BaseTitleGrab> titleList = baseTitleGrabDao.queryColumnForList(condition);
        String suffix = "";
//		if (!CollectionUtils.isEmpty(titleList)) {
//			// 给名称加后缀，避免出现重名的标题
//			suffix = titleList.size() + "";
//		}
        BaseTitleGrab title = new BaseTitleGrab();
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
            baseTitleGrabDao.save(title);
        } catch (Exception e) {
            logger.info("加载下载菜单出错", e);
        }
        BaseTitle returnTitle = new BaseTitle();
        BeanUtils.copyProperties(title, returnTitle);
        return returnTitle;
    }

    @Override
    public BaseTitle updateTitle(SaveTitleRequest request) {
        BaseTitleGrab title = new BaseTitleGrab();
        title.setTitle(request.getMenu().getName());
        title.setTypeId(request.getTypeId());
        title.setTips(request.getTips());
        title.setId(request.getId());
        baseTitleGrabDao.updateByPrimaryKeySelective(title);
        BaseTitle returnTitle = new BaseTitle();
        BeanUtils.copyProperties(title, returnTitle);
        return returnTitle;
    }

    @Override
    public void updateTitleOnLoadSuccess(Long docId, Long id) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("docId", docId);
        condition.addEqParam("id", id);
        baseTitleGrabDao.update(condition);
    }

    @Override
    public BaseTitle getTitleById(Long id) {
        BaseTitleGrab titleGrab = baseTitleGrabDao.queryTById(id);
        BaseTitle title = new BaseTitle();
        BeanUtils.copyProperties(titleGrab, title);
        return title;
    }

    @Override
    public BaseTitle getTitleByDocId(Long docId) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("docId", docId);
        BaseTitleGrab titleGrab = baseTitleGrabDao.queryTByParamForOne(conditionsCommon);
        if (titleGrab != null) {
            BaseTitle title = new BaseTitle();
            BeanUtils.copyProperties(titleGrab, title);
            return title;
        }
        return null;
    }

    @Override
    public List<BaseTitle> getTitleList(Long docId, String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        if(channel != null){
            condition.addEqParam("channel", channel);
        }
        if (docId != null) {
            condition.addEqParam("parantId", docId);
        } else {
            condition.addIsNullParam("parantId");
        }
        List<BaseTitleGrab> titleList = baseTitleGrabDao.queryTByParam(condition);
        return titleList.stream().map(n -> {
            BaseTitle returnTitle = new BaseTitle();
            BeanUtils.copyProperties(n, returnTitle);
            return returnTitle;
        }).collect(Collectors.toList());
    }

    @Override
    public int getChildrenCount(Long docId, String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("parantId", docId);
        return baseTitleGrabDao.queryForCount(condition);
    }

}
