package com.njq.basis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.model.dao.BaseTitleGrabJpaRepository;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.util.string.StringUtil;

@Service
public class BaseTitleService {
    @Resource
    private DaoCommon<BaseTitleLoading> baseTitleLoadingDao;
    @Resource
    private DaoCommon<BaseTitle> baseTitleDao;
    private Map<TitleType, SaveTitlePerformer> saveMap;
    @Resource
    private BaseTitleGrabJpaRepository baseTitleGrabJpaRepository;
    
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
        if (StringUtil.IsNotEmpty(channel)) {
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

    public BaseTitle getTitleByDocId(Long docId) {
        return saveMap.get(TitleType.GRAB_TITLE).getTitleByDocId(docId);
    }

    public BaseTitle getTitleId(Long titleId) {
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

    public void updateLoadSuccess(Long docId, Long id) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("loaded", Use_Type.USED);
        condition.addEqParam("titleId", id);
        condition.addEqParam("loaded", Use_Type.UN_USE);
        int modiNum = baseTitleLoadingDao.update(condition);
        if (ConstantsCommon.Common_Value.C_ZERO == modiNum) {
            throw new BaseKnownException(ErrorCodeConstant.ALREADY_LOAD_CODE, ErrorCodeConstant.ALREADY_LOAD_MSG);
        }
        saveMap.get(TitleType.GRAB_TITLE).updateTitleOnLoadSuccess(docId, id);
    }

    public void updateStarTitle(Long docId, Boolean isStar) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("starTab", isStar);
        condition.addEqParam("id", docId);
        saveMap.get(TitleType.GRAB_TITLE).updateByParam(condition);
    }

    public List<BaseTitle> getStarTitleList() {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("starTab", true);
        conditionsCommon.addSetOrderColum("createDate", "desc");
        return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }

    public List<BaseTitle> getNewLoadTitleList() {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addSetOrderColum("createDate", "desc");
        conditionsCommon.addPageParam(1, 20);
        return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }

    public List<BaseTitle> getSearchTitleList(String[] str) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        if (str != null) {
            conditionsCommon.addColumMoreLikeParam("title", str);
        }
        conditionsCommon.addSetOrderColum("createDate", "desc");
        List<BaseTitle> llist = saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
        llist.forEach(n->{
        	for (int i = 0; i < str.length; i++) {
        		n.setTitle(searchText(str[i], n.getTitle()));
			}
        });
        return llist;
    }

    public PageList<BaseTitle> searchPage(Integer page,Integer pageSize) {
    	ConditionsCommon cc=new ConditionsCommon();
    	cc.addPageParam(page, pageSize);
    	cc.addSetOrderColum("id", "desc");
    	return saveMap.get(TitleType.GRAB_TITLE).queryPageList(cc);
    }
    
    private String searchText(String searchValue,String text) {
		String lowerValue = text.toLowerCase();
		int index = -1;
		StringBuilder sb = new StringBuilder();
		int start = 0;
		while ((index = lowerValue.indexOf(searchValue)) >= 0) {
			sb.append(text.substring(start, start + index));
			sb.append("<em>");
			sb.append(text.substring(start + index,start + index + searchValue.length()));
			sb.append("</em>");
			start += index + searchValue.length();
			lowerValue = lowerValue.substring(index + searchValue.length() , lowerValue.length() );
		}
		sb.append(text.substring(start,text.length() ));
		return sb.toString();
    }
    
    
    public List<BaseTitle>  getTitleByType(Long typeId,Long parentId){
    	ConditionsCommon conditionsCommon = new ConditionsCommon();
    	conditionsCommon.addEqParam("typeId", typeId);
    	if(parentId != null) {
    		conditionsCommon.addEqParam("parentId", parentId);    		
    	}else {
    		conditionsCommon.addIsNullParam("parentId");
    	}
    	conditionsCommon.addSetOrderColum("starTab", "desc");
    	conditionsCommon.addSetOrderColum("createDate", "desc");
    	return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }
    
    public List<BaseTitle>  getTitleByTip(Long tipId){
    	List<BaseTitleGrab> titleList = baseTitleGrabJpaRepository.queryByTipId(tipId);
    	return titleList.stream().map(n -> {
            BaseTitle returnTitle = new BaseTitle();
            BeanUtils.copyProperties(n, returnTitle);
            return returnTitle;
        }).collect(Collectors.toList());
    }
}
