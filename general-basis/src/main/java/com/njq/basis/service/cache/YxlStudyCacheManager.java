package com.njq.basis.service.cache;

import org.springframework.stereotype.Component;

import com.njq.common.base.dao.PageList;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.enumreg.cachepre.CacheNamePrefixEnum;
import com.njq.common.model.vo.YxlStudyVO;
@Component
public class YxlStudyCacheManager extends GenericValueCacheManager<String, PageList<YxlStudyVO>>{

	@Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.YXL_TYPE;
    }
	
	
	
}
