package com.njq.yxl.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.enumreg.cachepre.CacheNamePrefixEnum;
import com.njq.common.model.po.BaseBanner;

/**
 * @author: nijiaqi
 * @date: 2018/12/20
 */
@Component
public class BannerCacheReader extends GenericValueCacheManager<String,List<BaseBanner>> {
    @Autowired
    private BaseBannerService baseBannerService;

    @Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.INDEX_BANNER_INFO;
    }

    @Override
    protected List<BaseBanner> createObject(String id) {
        return baseBannerService.queryList();
    }
    
    
    public List<BaseBanner> getNbanner(){
    	return baseBannerService.queryList2();
    }
    
    
    
}
