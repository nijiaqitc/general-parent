package com.njq.basis.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.njq.basis.service.SaveTitlePerformer;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.enumreg.title.TitleType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.dao.BaseTitleGrabJpaRepository;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.util.string.StringUtil;
import com.njq.common.util.string.StringUtil2;

@Service
public class BaseTitleService {
	private static final Logger logger = LoggerFactory.getLogger(BaseTitleService.class);
    @Resource
    private DaoCommon<BaseTitleLoading> baseTitleLoadingDao;
    private Map<TitleType, SaveTitlePerformer> saveMap;
    @Resource
    private BaseTitleGrabJpaRepository baseTitleGrabJpaRepository;
    @Resource
    private JedisLockFactory jedisLockFactory;

    private Semaphore semaphore;

    @Autowired
    public BaseTitleService(SaveTitlePerformer grabSaveTitlePerformer, SaveTitlePerformer baseSaveTitlePerformer) {
        saveMap = new HashMap<>();
        saveMap.put(TitleType.BASE_TITLE, baseSaveTitlePerformer);
        saveMap.put(TitleType.GRAB_TITLE, grabSaveTitlePerformer);
        semaphore = new Semaphore(10, true);
    }

    /**
     * 获取需要加载的文章
     *
     * @param channel
     * @return
     */
    public List<BaseTitleLoading> getLoadedTitle(String channel,String loaded) {
        ConditionsCommon condition = new ConditionsCommon();
        if (StringUtil.IsNotEmpty(channel)) {
            condition.addEqParam("channel", channel);
        }
        if (StringUtil.IsNotEmpty(loaded)) {
        	condition.addEqParam("loaded", loaded);        	
        }
        condition.addLtParam("tryNum", 6);
        condition.addPageParam(1, 500);
        List<BaseTitleLoading> loadingList = baseTitleLoadingDao.queryColumnForList(condition);
        return loadingList;
    }

    public void updateToReload(String channel,Long docId) {
    	ConditionsCommon condition = new ConditionsCommon();
    	condition.addsetColumParam("loaded", "2");
    	if(!StringUtil2.isEmpty(channel)) {
    		condition.addEqParam("channel", channel);    		
    	}
    	if(docId != null&&docId >0) {
    		BaseTitle title = saveMap.get(TitleType.GRAB_TITLE).getTitleByDocId(docId);    		
    		condition.addEqParam("titleId", title.getId());
    	}
    	condition.addEqParam("loaded", "1");
    	baseTitleLoadingDao.update(condition);
    }
    
    public void updateLoadingSuccess(Long id) {
    	ConditionsCommon condition = new ConditionsCommon();
    	condition.addsetColumParam("loaded", "1");
    	condition.addEqParam("id", id);
    	baseTitleLoadingDao.update(condition);
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
        String lockKey = StringUtil2.format("saveUrl-{0}-saveChannel-{1}-saveName-{2}", request.getMenu().getValue(), request.getChannel(), request.getMenu().getName());
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("并发获取锁失败！");
            }
            return saveData(request);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private BaseTitle saveData(SaveTitleRequest request) {
        try {
            semaphore.acquire();
            System.out.println("信号量并发："+(10-semaphore.availablePermits()));
            BaseTitle tt;
            tt = verify(request.getMenu().getValue(), request.getChannel());
            if (tt == null) {
                tt = saveMap.get(request.getTitleType())
                        .saveTitle(request);
                BaseTitleLoading loading = new BaseTitleLoading();
                loading.setTitleId(tt.getId());
                loading.setUrl(request.getMenu().getValue());
                loading.setCreateDate(new Date());
                loading.setChannel(request.getChannel());
                loading.setLoaded(ConstantsCommon.Use_Type.UN_USE);
                loading.setDocIdSource(request.getMenu().getDocId());
                baseTitleLoadingDao.save(loading);
                logger.info("入库完成"+request.getMenu().getName()+"###"+request.getMenu().getValue());
            }else {
            	logger.info("已入库，重复录入"+request.getMenu().getName()+"###"+request.getMenu().getValue());
            }
            return tt;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
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

    public void updateLoadFailed(Long id) {
    	ConditionsCommon condition = new ConditionsCommon();
        condition.addsetObjectParam("loaded", Use_Type.FAILED);
        condition.addEqParam("titleId", id);
        condition.addEqParam("loaded", Use_Type.UN_USE);
        baseTitleLoadingDao.update(condition);
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
        llist.forEach(n -> {
            for (int i = 0; i < str.length; i++) {
                if (!StringUtils.isEmpty(str[i])) {
                    n.setTitle(searchText(str[i], n.getTitle()));
                }
            }
        });
        return llist;
    }

    public PageList<BaseTitle> searchPage(Integer page, Integer pageSize) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, pageSize);
        cc.addSetOrderColum("id", "desc");
        return saveMap.get(TitleType.GRAB_TITLE).queryPageList(cc);
    }

    private String searchText(String searchValue, String text) {
        String lowerValue = text.toLowerCase();
        int index = -1;
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while ((index = lowerValue.indexOf(searchValue)) >= 0) {
            sb.append(text.substring(start, start + index));
            sb.append("<em>");
            sb.append(text.substring(start + index, start + index + searchValue.length()));
            sb.append("</em>");
            start += index + searchValue.length();
            lowerValue = lowerValue.substring(index + searchValue.length(), lowerValue.length());
        }
        sb.append(text.substring(start, text.length()));
        return sb.toString();
    }


    public List<BaseTitle> getTitleByType(Long typeId, Long parentId) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addEqParam("typeId", typeId);
        if (parentId != null) {
            conditionsCommon.addEqParam("parentId", parentId);
        } else {
            conditionsCommon.addIsNullParam("parentId");
        }
        conditionsCommon.addSetOrderColum("starTab", "desc");
        conditionsCommon.addSetOrderColum("title", "asc");
        return saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
    }

    public List<BaseTitle> getTitleByTip(Long tipId) {
        List<BaseTitleGrab> titleList = baseTitleGrabJpaRepository.queryByTipId(tipId);
        return titleList.stream().map(n -> {
            BaseTitle returnTitle = new BaseTitle();
            BeanUtils.copyProperties(n, returnTitle);
            return returnTitle;
        }).collect(Collectors.toList());
    }

    public List<BaseTitle> getTitleByTipName(String tipName) {
        List<BaseTitleGrab> titleList = baseTitleGrabJpaRepository.queryByTipName(tipName);
        return titleList.stream().map(n -> {
            BaseTitle returnTitle = new BaseTitle();
            BeanUtils.copyProperties(n, returnTitle);
            return returnTitle;
        }).collect(Collectors.toList());
    }


    public Pair<BaseTitle, BaseTitle> getlrTitle(Long titleId) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addLtParam("id", titleId);
        conditionsCommon.addSetOrderColum("id", "desc");
        conditionsCommon.addPageParam(1, 1);
        List<BaseTitle> leftList = saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
        conditionsCommon = new ConditionsCommon();
        conditionsCommon.addGtParam("id", titleId);
        conditionsCommon.addPageParam(1, 1);
        List<BaseTitle> rightList = saveMap.get(TitleType.GRAB_TITLE).getTitleByParam(conditionsCommon);
        return Pair.of(CollectionUtils.isEmpty(leftList) ? null : leftList.get(0), CollectionUtils.isEmpty(rightList) ? null : rightList.get(0));
    }
}
