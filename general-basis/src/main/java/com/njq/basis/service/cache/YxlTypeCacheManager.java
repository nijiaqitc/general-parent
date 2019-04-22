package com.njq.basis.service.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.enumreg.cachepre.CacheNamePrefixEnum;
import com.njq.common.model.po.YxlType;

@Component
public class YxlTypeCacheManager extends GenericValueCacheManager<Long, YxlType> {
    
    @Resource
    private DaoCommon<YxlType> yxlTypeDao;
    
    @Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.YXL_TYPE;
    }
    
    @Override
	protected YxlType getObject(Long id) {
    	return yxlTypeDao.queryTById(id);
	}

    
}
