package com.njq.basis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.enumreg.title.TitleType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;

@Component("grabSaveTitlePerformer")
public class GrabSaveTitlePerformerImpl implements SaveTitlePerformer {
    private static final Logger logger = LoggerFactory.getLogger(GrabSaveTitlePerformerImpl.class);
    @Resource
    private DaoCommon<BaseTitleGrab> baseTitleGrabDao;
    @Resource
    private BaseTypeService baseTypeService;
    @Resource
    private BaseTipService baseTipService;
    @Override
    public BaseTitle saveTitle(SaveTitleRequest request) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("title", request.getMenu().getName());
        condition.addEqParam("typeId", request.getTypeId());
        condition.addEqParam("channel", request.getChannel());
//        List<BaseTitleGrab> titleList = baseTitleGrabDao.queryColumnForList(condition);
        String suffix = "";
//		if (!CollectionUtils.isEmpty(titleList)) {
//			// 给名称加后缀，避免出现重名的标题
//			suffix = titleList.size() + "";
//		}
        BaseTitleGrab title = new BaseTitleGrab();
        try {
            title.setParentId(request.getParentId());
            title.setTitle(request.getMenu().getName() + suffix);
            if (title.getTitle().length() > 100) {
                title.setTitle(title.getTitle().substring(0, 100) + "...");
            }
            title.setApply(0);
            title.setCreateDate(new Date());
            title.setChannel(request.getChannel());
            title.setCreateBy(ConstantsCommon.Oper_User.ADMIN);
            title.setModiBy(ConstantsCommon.Oper_User.ADMIN);
            title.setTypeId(request.getTypeId());
            baseTitleGrabDao.save(title);
            baseTypeService.addNum(request.getChannel(), request.getTypeId());
            baseTipService.addNum(request.getTips(),title.getId(),TitleType.GRAB_TITLE);
            /**
             * 标记父标签
             */
            if(request.getParentId() != null) {
            	BaseTitleGrab pt= new BaseTitleGrab();
            	pt.setId(request.getParentId());
            	pt.setIsParent(true);
            	baseTitleGrabDao.updateByPrimaryKeySelective(pt);
            }
        } catch (Exception e) {
            logger.info("加载下载菜单出错", e);
            throw new BaseKnownException("加载下载菜单出错");
        }
        return convertTitle(title);
    }

    @Override
    public BaseTitle updateTitle(SaveTitleRequest request) {
        BaseTitleGrab title = new BaseTitleGrab();
        title.setTitle(request.getMenu() == null? null:request.getMenu().getName());
        title.setTypeId(request.getTypeId());
//        title.setTips(request.getTips());
        title.setId(request.getId());
        baseTitleGrabDao.updateByPrimaryKeySelective(title);
        return convertTitle(baseTitleGrabDao.queryTById(request.getId()));
    }

    @Override
    public void updateTitleOnLoadSuccess(Long docId, Long id) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("docId", docId);
        condition.addsetObjectParam("apply", 1);
        condition.addEqParam("id", id);
        baseTitleGrabDao.update(condition);
    }

    @Override
    public BaseTitle getTitleById(Long id) {
        BaseTitleGrab titleGrab = baseTitleGrabDao.queryTById(id);
        return convertTitle(titleGrab);
    }

    @Override
    public BaseTitle getTitleByDocId(Long docId) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("docId", docId);
        BaseTitleGrab titleGrab = baseTitleGrabDao.queryTByParamForOne(conditionsCommon);
        if (titleGrab != null) {
            return convertTitle(titleGrab);
        }
        return null;
    }

    @Override
    public List<BaseTitle> getTitleList(Long docId, String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        if (channel != null) {
            condition.addEqParam("channel", channel);
        }
        if (docId != null) {
            condition.addEqParam("parentId", docId);
        } else {
            condition.addIsNullParam("parentId");
        }
        List<BaseTitleGrab> titleList = baseTitleGrabDao.queryTByParam(condition);
        return titleList.stream().map(n -> {
            return convertTitle(n);
        }).collect(Collectors.toList());
    }

    @Override
    public int getChildrenCount(Long docId, String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("parentId", docId);
        return baseTitleGrabDao.queryForCount(condition);
    }

    @Override
    public void updateByParam(ConditionsCommon conditionsCommon) {
        baseTitleGrabDao.update(conditionsCommon);
    }

    @Override
    public List<BaseTitle> getTitleByParam(ConditionsCommon conditionsCommon) {
        List<BaseTitleGrab> titleList = baseTitleGrabDao.queryForListNoPage(conditionsCommon);
        return titleList.stream().map(n -> {
        	return convertTitle(n);
        }).collect(Collectors.toList());
    }

	@Override
	public PageList<BaseTitle> queryPageList(ConditionsCommon conditionsCommon) {
		PageList<BaseTitleGrab> grabPage = baseTitleGrabDao.queryForPage(conditionsCommon); 
		PageList<BaseTitle> pageList = new PageList<BaseTitle>();
		pageList.setTotal(grabPage.getTotal());
		if(!CollectionUtils.isEmpty(grabPage.getList())) {
			List<BaseTitle> titleList = grabPage.getList().stream().map(n->{
				return convertTitle(n);
			}).collect(Collectors.toList());
			pageList.setList(titleList);
		}
		return pageList;
	}
    
    private BaseTitle convertTitle(BaseTitleGrab grabTitle) {
    	if(grabTitle == null) {
    		return null;
    	}
    	BaseTitle returnTitle = new BaseTitle();
        BeanUtils.copyProperties(grabTitle, returnTitle);
        return returnTitle;
    }
}
