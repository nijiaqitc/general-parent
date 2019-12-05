package com.njq.basis.service.cache;

import java.util.List;

import org.springframework.stereotype.Component;

import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.enumreg.cachepre.CacheNamePrefixEnum;
import com.njq.common.model.po.BaseTitle;
@Component
public class BaseTitleCacheManager extends GenericValueCacheManager<String, List<BaseTitle>>{
	@Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.GRAB_MENU;
    }

	
	
}
