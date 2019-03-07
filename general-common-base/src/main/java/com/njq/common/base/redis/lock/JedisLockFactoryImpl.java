package com.njq.common.base.redis.lock;
import javax.validation.constraints.NotNull;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class JedisLockFactoryImpl implements JedisLockFactory {
    private final JedisConnectionFactory jedisConnectionFactory;

    public JedisLockFactoryImpl(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    public JedisLock getLock(@NotNull String lockKey) {
        JedisLock jedisLock = new JedisLock(this.jedisConnectionFactory, lockKey);
        jedisLock.setCustomSleepTime(30, 15);
        return jedisLock;
    }
}