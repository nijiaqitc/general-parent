package com.njq.common.base.redis.lock;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.validation.constraints.NotNull;

public class JedisLockFactoryImpl implements JedisLockFactory {
    private final RedisConnectionFactory redisConnectionFactory;

    public JedisLockFactoryImpl(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public JedisLock getLock(@NotNull String lockKey) {
        JedisLock jedisLock = new JedisLock(this.redisConnectionFactory, lockKey);
        jedisLock.setCustomSleepTime(30, 15);
        return jedisLock;
    }
}