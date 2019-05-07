package com.njq.wx.cache;

import com.njq.common.base.redis.GenericValueCacheManager;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenManager extends GenericValueCacheManager<String,AccessToken > {
    public AccessTokenManager() {
        this.cacheExpiry = 120;
    }

    @Override
    protected boolean isPersistentCache() {
        return true;
    }
}