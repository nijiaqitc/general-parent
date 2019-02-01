package com.njq.basis.service.impl;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.dao.BaseTypeNumJpaRepository;
import com.njq.common.model.po.BaseType;
import com.njq.common.model.po.BaseTypeNum;
import com.njq.common.util.string.StringUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BaseTypeService {
    @Autowired
    private DaoCommon<BaseType> baseTypeDao;
    @Autowired
    private DaoCommon<BaseTypeNum> baseTypeNumDao;
    @Resource
    private BaseTypeNumJpaRepository baseTypeNumJpaRepository;

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
        ConditionsCommon condition = new ConditionsCommon();
        condition.addEqParam("name", typeName);
        BaseType type = baseTypeDao.queryTByParamForOne(condition);
        if (type == null) {
            type = new BaseType();
            type.setCreateDate(new Date());
            type.setName(typeName);
            baseTypeDao.save(type);
        }
        return type.getId();
    }

    /**
     * 添加次数
     *
     * @param channel
     * @param typeId
     */
    public void addNum(String channel, Long typeId) {
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
    }

    public BaseType getTypeById(Long id) {
        return baseTypeDao.queryTById(id);
    }
}
