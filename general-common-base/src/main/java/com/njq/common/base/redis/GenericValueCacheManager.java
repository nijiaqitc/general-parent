package com.njq.common.base.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class GenericValueCacheManager<PK extends Serializable,V> extends AbstractCanIncreaseCacheManager<PK> {
	private static final Logger logger = LoggerFactory.getLogger(GenericValueCacheManager.class);
    public GenericValueCacheManager() {
        super(CacheKeyType.STRING);
    }

	public V get(PK id) {
        V object = null;
        String key = this.makeFullCacheKey(id);

        try {
            try {
                if (redisTemplate != null) {
                    object = (V) redisTemplate.opsForValue().get(key);
                }
            } catch (Exception e) {
            	logger.info(e.getMessage());
            }

            if (object == null) {
                object = getObject(id);

                if (object == null) {
                    object = createObject(id);
                }

                try {
                    if (redisTemplate != null && object != null) {
                        redisTemplate.opsForValue().set(key, object, getCacheExpiry(), TimeUnit.MINUTES);
                    }
                } catch (RuntimeException e) {
                    this.catchOnExceptions(e);
                }
            }
        } catch (RuntimeException e) {
            this.catchOnExceptions(e);
        }

        return object;
    }

    public void update(PK id, V object) {
        update(id, object, getCacheExpiry());
    }

    public void update(PK id, V object, int cacheExpiry) {
        if (redisTemplate != null && object != null) {
            String key = this.makeFullCacheKey(id);
            // 第一次设置key时强制配置过期时间
            try {
                if (shouldUpdateExpiryOnUpdate() || !redisTemplate.hasKey(key)) {
                    redisTemplate.opsForValue().set(key, object, cacheExpiry, TimeUnit.MINUTES);
                } else {
                    redisTemplate.opsForValue().set(key, object);
                }
            } catch (RuntimeException e) {
            	logger.info("设置缓存出错",e);
                this.catchOnExceptions(e);
            }
        }
    }

    /**
     * 累加long
     *
     * @param id 主键
     * @return 新的值
     */
    public Long increase(PK id) {
        return increaseLong(id);
    }

    /**
     * 累加long
     *
     * @param id 主键
     * @return 新的值
     */
    public Long increaseLong(PK id) {
        return increaseLong(id, 1L);
    }

    /**
     * 累加int
     *
     * @param id 主键
     * @return 新的值
     */
    public Integer increaseInteger(PK id) {
        Long increaseLong = increaseLong(id, 1L);

        if (increaseLong > Integer.MAX_VALUE) {
            throw new RuntimeException("超出Int最大数量");
        }

        return increaseLong.intValue();
    }

    /**
     * 以某个步进累加long
     *
     * @param id   主键
     * @param step 步进
     * @return 新的值
     */
    public Long increaseLong(PK id, long step) {
        return this.increase(id, step, (key, s) -> redisTemplate.opsForValue().increment(key, s));
    }

    /**
     * 以某个步进累加double
     *
     * @param id   主键
     * @param step 步进
     * @return 新的值
     */
    public Double increaseDouble(PK id, double step) {
        return this.increase(id, step, (key, s) -> redisTemplate.opsForValue().increment(key, s));
    }


    /**
     * 继承此类需要重载此方法
     *
     * @param id key
     * @return Cache Object
     */
    protected V createObject(PK id) {
        return null;
    }

    /**
     * 继承此类需要重载此方法
     *
     * @param id key
     * @return Cache Object
     */
    protected V getObject(PK id) {
        return null;
    }

    /**
     * 是否每次都更新超时时间, 默认每次都更新
     */
    protected boolean shouldUpdateExpiryOnUpdate() {
        return true;
    }

    private <T> T increase(PK id, T step, BiFunction<String, T, T> biFunction) {
        if (redisTemplate != null) {
            try {
                String key = this.makeFullCacheKey(id);
                T result = biFunction.apply(key, step);
                redisTemplate.expire(key, cacheExpiry, TimeUnit.MINUTES);
                return result;
            } catch (RuntimeException e) {
                this.catchOnExceptions(e);
                return null;
            }
        } else {
            throw new RuntimeException("redisTemplate is null");
        }
    }
}