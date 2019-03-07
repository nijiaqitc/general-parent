package com.njq.common.base.config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.base.redis.lock.JedisLockFactoryImpl;

@Configuration
public class JedisLockConfiguration {
    public JedisLockConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public JedisLockFactory jedisLockFactory(JedisConnectionFactory jedisConnectionFactory) {
        return new JedisLockFactoryImpl(jedisConnectionFactory);
    }
}