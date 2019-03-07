package com.njq.basis.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.dao.BaseTipJpaRepository;
import com.njq.common.model.po.BaseTip;
import com.njq.common.model.po.BaseTipConfig;
import com.njq.common.model.vo.LabelNameVO;

@Service
public class BaseTipService {
    @Autowired
    private DaoCommon<BaseTip> baseTipDao;
    @Resource
    private BaseTipJpaRepository baseTipJpaRepository;
    @Resource
    private DaoCommon<BaseTipConfig> baseTipConfigDao;

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
        return checkAndSaveTips(tips);
    }

    public String checkAndSaveTips(String... tips) {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < tips.length; i++) {
            stb.append(this.checkAndSave(tips[i].trim()) + ",");
        }
        return stb.substring(0, stb.length() - 1);
    }

    public BaseTip getById(Long id) {
    	return baseTipDao.queryTById(id);
    }
    
    public BaseTip getByName(String tipName) {
    	ConditionsCommon condition = new ConditionsCommon();
    	condition.addEqParam("tip_name", tipName);
    	return baseTipDao.queryTByParamForOne(condition);
    }
    
    public List<BaseTip> getTipListByIds(String ids) {
        String[] tipIds = ids.split(",");
        List<BaseTip> list = new ArrayList<>();
        for (String tipId : tipIds) {
            list.add(baseTipDao.queryTById(Long.valueOf(tipId)));
        }
        return list;
    }


    public void addNum(String tipIds, Long titleId, TitleType type) {
        if (StringUtils.isBlank(tipIds)) {
            return;
        }
        String tips[] = tipIds.split(",");
        for (int i = 0; i < tips.length; i++) {
        	ConditionsCommon con=new ConditionsCommon();
        	con.addEqParam("tipId", Long.valueOf(tips[i]));
        	con.addEqParam("titleId", titleId);
        	List<BaseTipConfig> ll = baseTipConfigDao.queryColumnForList(con);
        	if(CollectionUtils.isEmpty(ll)) {
        		BaseTipConfig config = new BaseTipConfig();
        		config.setCreateDate(new Date());
        		config.setTipId(Long.valueOf(tips[i]));
        		config.setSourceType(type.getValue());
        		config.setTitleId(titleId);
        		baseTipConfigDao.save(config);
        	}
        }
    }


    public void saveToRepairTip(BaseTipConfig config) {
    	baseTipConfigDao.save(config);
    }
    
    
    public List<LabelNameVO> getAllTips(){
    	return baseTipJpaRepository.queryAllTip();
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getTipsByTitleId(Long titleId) {
    	ConditionsCommon condition = new ConditionsCommon();
    	condition.addEqParam("titleId", titleId);
    	List<BaseTipConfig> configList = baseTipConfigDao.queryColumnForList(condition);
    	if(CollectionUtils.isEmpty(configList)) {
    		return Collections.EMPTY_LIST;
    	}
    	return configList.stream().map(n->{
    		BaseTip tip = baseTipDao.queryTById(n.getTipId());
    		if(tip!=null) {
    			return tip.getTipName();
    		}else {
    			return null;
    		}
    	}).collect(Collectors.toList());
    }
    
    public List<String> getTopTips(){
    	List<String> tipList = baseTipJpaRepository.queryTopTip();
    	return tipList;
    }
}
