package com.njq.zxgj.controller;

import com.njq.basis.service.impl.BaseBannerService;
import com.njq.common.base.redis.CacheNamePrefixEnum;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.model.po.BaseBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
}