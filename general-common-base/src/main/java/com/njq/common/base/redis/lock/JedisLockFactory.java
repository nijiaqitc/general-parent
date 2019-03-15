package com.njq.common.base.redis.lock;

import javax.validation.constraints.NotNull;

public interface JedisLockFactory {

    JedisLock getLock(@NotNull String lockKey);
}