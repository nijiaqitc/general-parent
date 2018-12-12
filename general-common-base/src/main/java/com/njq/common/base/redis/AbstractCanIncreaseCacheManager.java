package com.njq.common.base.redis;

import java.io.Serializable;

import org.springframework.lang.Nullable;

/**
 * 支持缓存值可原子自增操作的缓存基类
 */
public class AbstractCanIncreaseCacheManager<PK extends Serializable> extends AbstractCacheManager<PK> {
    private Class<?> increaseValueType = null;
    protected AbstractCanIncreaseCacheManager(CacheKeyType cacheKeyType) {
        super(cacheKeyType);
    }


    /**
     * 设置此缓存值可以通过increase 方法直接原子操作累加
     *
     * @param classOfValue value类型，只支持: Long, Integer and Double
     * @deprecated 更换为通过 override getTypeOfValueCanIncrease 来实现
     */
    @Deprecated
    protected void makeValueCanIncrease(Class<?> classOfValue) {
        if (classOfValue != null) {
            this.increaseValueType = classOfValue;
        }
    }

    /**
     * 获取可以支持increase方法的缓存值类型 只支持: Long, Integer and Double，实现时重写此方法并返回 Long, Integer and Double 中的一种。表面此cacheManager可以支持自增操作
     *
     * @return 类型，默认返回null
     */
    @Nullable
    protected Class<?> getTypeOfValueCanIncrease() {
        return increaseValueType;
    }
}