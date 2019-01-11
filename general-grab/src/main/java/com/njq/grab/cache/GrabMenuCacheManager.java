package com.njq.grab.cache;

import com.njq.common.base.redis.CacheNamePrefixEnum;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.string.StringUtil2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/1/11
 */
@Component
public class GrabMenuCacheManager extends GenericValueCacheManager<String, List<LeftMenu>> {

    private final String SHORT_NAME = "short-{0}";
    @Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.GRAB_MENU;
    }
    @Override
    public void update(String key, List<LeftMenu> list) {
        update(StringUtil2.format(SHORT_NAME, key), list, getCacheExpiry());
    }

}
