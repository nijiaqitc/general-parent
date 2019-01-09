package com.njq.grab.cache;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.redis.CacheNamePrefixEnum;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.impl.PerformerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginCacheManager extends GenericValueCacheManager<String, Boolean> {
    private final String CHANNEL_LOGIN_NAME = "channelLoginName-{0}";
    @Resource
    private PerformerService performerService;

    @Override
    protected CacheNamePrefixEnum registerCacheName() {
        return CacheNamePrefixEnum.GRAB_LOGIN_TOKEN;
    }

    public void checkAndLogin(ChannelType channel) {
        Boolean info = this.get(StringUtil2.format(CHANNEL_LOGIN_NAME, channel));
        if (info == null) {
            performerService.getAnalysisPerformer(channel).login();
            this.update(StringUtil2.format(CHANNEL_LOGIN_NAME, channel), true, 5);
        }
    }

    public void reLogin(ChannelType channel) {
        performerService.getAnalysisPerformer(channel).login();
        this.update(StringUtil2.format(CHANNEL_LOGIN_NAME, channel), true, 5);
    }
}
