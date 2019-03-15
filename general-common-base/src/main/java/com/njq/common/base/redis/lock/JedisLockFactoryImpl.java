package com.njq.common.base.redis.lock;
import javax.validation.constraints.NotNull;

import org.springframework.data.redis.connection.RedisConnectionFactory;

public class JedisLockFactoryImpl implements JedisLockFactory {
    private final RedisConnectionFactory redisConnectionFactory;

    public JedisLockFactoryImpl(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public JedisLock getLock(@NotNull String lockKey) {
        JedisLock jedisLock = new JedisLock(this.redisConnectionFactory, lockKey);
        jedisLock.setCustomSleepTime(30, 15);
        return jedisLock;
    }
}