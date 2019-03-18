package com.njq.basis.service.impl;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.model.dao.BaseTypeNumJpaRepository;
import com.njq.common.model.po.BaseType;
import com.njq.common.model.po.BaseTypeNum;
import com.njq.common.model.vo.LabelNameVO;
import com.njq.common.util.string.StringUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BaseTypeService {
    @Autowired
    private DaoCommon<BaseType> baseTypeDao;
    @Autowired
    private DaoCommon<BaseTypeNum> baseTypeNumDao;
    @Resource
    private BaseTypeNumJpaRepository baseTypeNumJpaRepository;
    @Resource
    private JedisLockFactory jedisLockFactory;

    /**
     * 校验和保存
     *
     * @param typeName
     * @return
     */
    public Long checkAndSave(String typeName) {
        if (StringUtil2.isEmpty(typeName)) {
            return null;
        }
        String lockKey = StringUtil2.format("checkAndSave-{0}", typeName);
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("type保存并发获取锁失败！");
            }
            ConditionsCommon condition = new ConditionsCommon();
            condition.addEqParam("name", typeName.trim());
            BaseType type = baseTypeDao.queryTByParamForOne(condition);
            if (type == null) {
                type = new BaseType();
                type.setCreateDate(new Date());
                type.setName(typeName.trim());
                baseTypeDao.save(type);
            }
            return type.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 添加次数
     *
     * @param channel
     * @param typeId
     */
    public void addNum(String channel, Long typeId) {
        String lockKey = StringUtil2.format("typeaddNum-{0}-{1}", typeId, channel);
        try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)) {
            if (!jedisLock.acquire()) {
                throw new BaseKnownException("type添加数量并发获取锁失败！");
            }
            ConditionsCommon condition = new ConditionsCommon();
            condition.addEqParam("typeId", typeId);
            condition.addEqParam("channel", channel);
            BaseTypeNum num = baseTypeNumDao.queryTByParamForOne(condition);
            if (num == null) {
                num = new BaseTypeNum();
                num.setCreateDate(new Date());
                num.setTypeId(typeId);
                num.setNum(1);
                num.setChannel(channel);
                baseTypeNumDao.save(num);
            } else {
                baseTypeNumJpaRepository.updateForAddNum(channel, typeId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public BaseType getTypeById(Long id) {
        return baseTypeDao.queryTById(id);
    }

    public List<LabelNameVO> getAllTypes() {
        return baseTypeNumJpaRepository.queryAllType();
    }
}
