package com.njq.basis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.util.string.StringUtil;

@Service
public class BaseTitleService {
    @Resource
    private DaoCommon<BaseTitleLoading> baseTitleLoadingDao;
    @Resource
    private DaoCommon<BaseTitle> baseTitleDao;
    private Map<TitleType, SaveTitlePerformer> saveMap;

    @Autowired
    public BaseTitleService(SaveTitlePerformer grabSaveTitlePerformer, SaveTitlePerformer baseSaveTitlePerformer) {
        saveMap = new HashMap<>();
        saveMap.put(TitleType.BASE_TITLE, baseSaveTitlePerformer);
        saveMap.put(TitleType.GRAB_TITLE, grabSaveTitlePerformer);
    }

    /**
     * 获取需要加载的文章
     *
     * @param channel
     * @return
     */
    public List<BaseTitleLoading> getLoadedTitle(String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        if(StringUtil.IsNotEmpty(channel)) {
        	condition.addEqParam("channel", channel);        	
        }
        condition.addEqParam("loaded", "0");
        condition.addPageParam(1, 500);
        List<BaseTitleLoading> loadingList = baseTitleLoadingDao.queryColumnForList(condition);
        return loadingList;
    }

    public BaseTitleLoading getLoadingByDocId(String docId) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("docIdSource", docId);
        BaseTitleLoading loading = baseTitleLoadingDao.queryTByParamForOne(condition);
        return loading;
    }

    public BaseTitleLoading getLoadingByTitleId(Long titleId) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("titleId", titleId);
        BaseTitleLoading loading = baseTitleLoadingDao.queryTByParamForOne(condition);
        return loading;
    }

    public BaseTitleLoading getLoadingByUrl(String url) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("url", url);
        BaseTitleLoading loading = baseTitleLoadingDao.queryTByParamForOne(condition);
        return loading;
    }

    public BaseTitleLoading getLoadingById(Long id) {
        return baseTitleLoadingDao.queryTById(id);
    }

    public BaseTitle saveTitle(SaveTitleRequest request) {
        BaseTitle tt;
        tt = verify(request.getMenu().getValue(), request.getChannel());
        if (tt == null) {
            tt = saveMap.get(request.getTitleType()).saveTitle(request);
            BaseTitleLoading loading = new BaseTitleLoading();
            loading.setTitleId(tt.getId());
            loading.setUrl(request.getMenu().getValue());
            loading.setCreateDate(new Date());
            loading.setChannel(request.getChannel());
            loading.setLoaded(ConstantsCommon.Use_Type.UN_USE);
            loading.setDocIdSource(request.getMenu().getDocId());
            baseTitleLoadingDao.save(loading);
        }
        return tt;
    }

    /**
     * 获取标题列表
     *
     * @param channel
     * @param docId
     * @return
     */
    public List<BaseTitle> getTitleList(ChannelType channel, Long docId) {
        return saveMap.get(TitleType.GRAB_TITLE).getTitleList(docId, channel == null ? null : channel.getValue());
    }

    public BaseTitle getTitleByDocId(Long docId, ChannelType channel) {
        return saveMap.get(TitleType.GRAB_TITLE).getTitleByDocId(docId);
    }

    public BaseTitle getTitleId(Long titleId, ChannelType channel) {
        return saveMap.get(TitleType.GRAB_TITLE).getTitleById(titleId);
    }

    public int childrenCount(Long docId, String channel) {
        return saveMap.get(TitleType.GRAB_TITLE).getChildrenCount(docId, channel);
    }

    public BaseTitle updateTitle(SaveTitleRequest request) {
        return saveMap.get(TitleType.GRAB_TITLE).updateTitle(request);
    }

    /**
     * 校验文章是否已经入库，避免重复入库
     *
     * @param url
     * @param channel
     * @return
     */
    private BaseTitle verify(String url, String channel) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("url", url);
        condition.addEqParam("channel", channel);
        BaseTitleLoading loading = baseTitleLoadingDao.queryTByParamForOne(condition);
        if (loading != null) {
//            if (Use_Type.USED.equals(loading.getLoaded())) {
//                throw new BaseKnownException(ErrorCodeConstant.ALREADY_LOAD_CODE, ErrorCodeConstant.ALREADY_LOAD_MSG);
//            } else {
//                return saveMap.get(TitleType.GRAB_TITLE).getTitleById(loading.getTitleId());
//            }
            return saveMap.get(TitleType.GRAB_TITLE).getTitleById(loading.getTitleId());
        } else {
            return null;
        }
    }

    public void updateLoadSuccess(ChannelType channel, Long docId, Long id) {
        saveMap.get(TitleType.GRAB_TITLE).updateTitleOnLoadSuccess(docId, id);
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("loaded", Use_Type.USED);
        condition.addEqParam("titleId", id);
        baseTitleLoadingDao.update(condition);
    }

    public void updateStarTitle(Long docId, Boolean isStar) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("starTab", isStar);
        condition.addEqParam("id", docId);
        saveMap.get(TitleType.GRAB_TITLE).updateByParam(condition);
    }

    public void updateTips(String tips,Long id) {
    	ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("tips", tips);
        condition.addEqParam("id", id);
        saveMap.get(TitleType.GRAB_TITLE).updateByParam(condition);
    }
    
    public List<BaseTitle> getStarTitleList(){
    	ConditionsCommon conditionsCommon = new  ConditionsCommon();
    	conditionsCommon.addEqParam("starTab", true);
    	return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }
    
    public List<BaseTitle> getNewLoadTitleList(){
    	ConditionsCommon conditionsCommon = new  ConditionsCommon();
    	conditionsCommon.addSetOrderColum("createDate", "desc");
    	conditionsCommon.addPageParam(1, 20);
    	return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }
    
    public List<BaseTitle> getSearchTitleList(String[] str){
    	ConditionsCommon conditionsCommon = new  ConditionsCommon();
    	if(str != null) {
    		conditionsCommon.addColumMoreLikeParam("title", str);    		
    	}
    	return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }
    
}
