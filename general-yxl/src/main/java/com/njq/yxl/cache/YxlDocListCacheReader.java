package com.njq.yxl.cache;

import com.njq.common.base.redis.CacheNamePrefixEnum;
import com.njq.common.base.redis.GenericValueCacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YxlDocListCacheReader extends GenericValueCacheManager<String, List<String>> {

    @Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.DOCLIST_FOR_QUERY;
    }

    @Override
    protected List<String> createObject(String id) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        return list;
    }

}
