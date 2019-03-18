package com.njq.basis.service.impl;

import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.model.dao.BaseTipJpaRepository;
import com.njq.common.model.po.BaseTip;
import com.njq.common.model.po.BaseTipConfig;
import com.njq.common.model.vo.LabelNameVO;
import com.njq.common.util.string.StringUtil2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseTipService {
    @Autowired
    private DaoCommon<BaseTip> baseTipDao;
    @Resource
    private BaseTipJpaRepository baseTipJpaRepository;
    @Resource
    private DaoCommon<BaseTipConfig> baseTipConfigDao;
    @Resource
    private JedisLockFactory jedisLockFactory;

    /**
     * 校验是否存在标签，没有则进行保存
     *
     * @param tipName
     * @return
     */
    public Long checkAndSave(String tipName) {
        String lockKey = StringUtil2.format("tipName-{0}", tipName);
        BaseTip tip;
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("tip保存并发获取锁失败！");
            }
            ConditionsCommon condition = new ConditionsCommon();
            condition.addEqParam("tipName", tipName);
            tip = baseTipDao.queryTByParamForOne(condition);
            if (tip == null) {
                tip = new BaseTip();
                tip.setCreateDate(new Date());
                tip.setTipName(tipName);
                baseTipDao.save(tip);
            }
            return tip.getId();
        } catch (BaseKnownException e1) {
            try {
                Thread.sleep(2000);
                return this.checkAndSave(tipName);
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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
            String lockKey = StringUtil2.format("addtipNum-{0}-{1}", tips[i], titleId);
            try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
                if (!jedisLock.acquire()) {
                    throw new BaseKnownException("tip保存并发获取锁失败！");
                }
                ConditionsCommon con = new ConditionsCommon();
                con.addEqParam("tipId", Long.valueOf(tips[i]));
                con.addEqParam("titleId", titleId);
                List<BaseTipConfig> ll = baseTipConfigDao.queryColumnForList(con);
                if (CollectionUtils.isEmpty(ll)) {
                    BaseTipConfig config = new BaseTipConfig();
                    config.setCreateDate(new Date());
                    config.setTipId(Long.valueOf(tips[i]));
                    config.setSourceType(type.getValue());
                    config.setTitleId(titleId);
                    baseTipConfigDao.save(config);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    public void saveToRepairTip(BaseTipConfig config) {
        String lockKey = StringUtil2.format("repairtipNum-{0}-{1}", config.getTipId(), config.getTitleId());
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("tip修复并发获取锁失败！");
            }
            ConditionsCommon con = new ConditionsCommon();
            con.addEqParam("tipId", config.getTipId());
            con.addEqParam("titleId", config.getTitleId());
            List<BaseTipConfig> ll = baseTipConfigDao.queryColumnForList(con);
            if (CollectionUtils.isEmpty(ll)) {
                baseTipConfigDao.save(config);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public List<LabelNameVO> getAllTips() {
        List<Object[]> listobj = baseTipJpaRepository.queryAllTip();
        if (!CollectionUtils.isEmpty(listobj)) {
            return listobj.stream().map(n -> {
                LabelNameVO vo = new LabelNameVO();
                vo.setId(Long.parseLong(String.valueOf(n[0])));
                vo.setName(String.valueOf(n[1]));
                vo.setNum(Integer.parseInt(String.valueOf(n[2])));
                return vo;
            }).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public List<String> getTipsByTitleId(Long titleId) {
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("titleId", titleId);
        List<BaseTipConfig> configList = baseTipConfigDao.queryColumnForList(condition);
        if (CollectionUtils.isEmpty(configList)) {
            return Collections.EMPTY_LIST;
        }
        return configList.stream().map(n -> {
            BaseTip tip = baseTipDao.queryTById(n.getTipId());
            if (tip != null) {
                return tip.getTipName();
            } else {
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<String> getTopTips() {
        List<String> tipList = baseTipJpaRepository.queryTopTip();
        return tipList;
    }
}
