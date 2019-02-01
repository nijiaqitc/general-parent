package com.njq.basis.service.impl;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.dao.BaseTipJpaRepository;
import com.njq.common.model.po.BaseTip;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BaseTipService {
    @Autowired
    private DaoCommon<BaseTip> baseTipDao;
    @Resource
    private BaseTipJpaRepository baseTipJpaRepository;

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
            tip.setNum(0);
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
            stb.append(this.checkAndSave(tips[i]) + ",");
        }
        return stb.substring(0, stb.length() - 1);
    }


    public List<BaseTip> getTipListByIds(String ids) {
        String[] tipIds = ids.split(",");
        List<BaseTip> list = new ArrayList<>();
        for (String tipId : tipIds) {
            list.add(baseTipDao.queryTById(Long.valueOf(tipId)));
        }
        return list;
    }


    public void addNum(String tipIds) {
        if (StringUtils.isBlank(tipIds)) {
            return;
        }
        String tips[] = tipIds.split(",");
        for (int i = 0; i < tips.length; i++) {
            baseTipJpaRepository.updateForAddNum(Long.valueOf(tips[i]));
        }
    }
}
