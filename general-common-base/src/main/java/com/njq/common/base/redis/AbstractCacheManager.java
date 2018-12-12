package com.njq.common.base.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.exceptions.JedisConnectionException;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractCacheManager<PK extends Serializable> {
    private final CacheKeyType cacheKeyType;
    /**
     * redis 过期时间,分钟为单位,默认5分钟
     */
    protected int cacheExpiry = 5;
	@Autowired
    protected RedisTemplate redisTemplate;

    protected AbstractCacheManager(CacheKeyType cacheKeyType) {
        this.cacheKeyType = cacheKeyType;
    }

    public int getCacheExpiry() {
        return cacheExpiry;
    }

    public void setCacheExpiry(int cacheExpiry) {
        this.cacheExpiry = cacheExpiry;
    }

    /**
     * 对于存在的key设置过期时间，如果不存在则跳过。
     *
     * @param id 业务id
     */
    public void expire(PK id) {
        this.expire(id, this.cacheExpiry, TimeUnit.MINUTES);
    }

    /**
     * 对于存在的key进行删除，如果不存在则跳过。
     *
     * @param id 业务id
     */
	public void remove(PK id) {
        try {
            String key = makeFullCacheKey(id);
            if (redisTemplate != null && redisTemplate.hasKey(key)) {
                redisTemplate.delete(key);
            }
        } catch (RuntimeException e) {
            this.catchOnExceptions(e);
        }
    }

    /**
     * 判断是否存在key。
     *
     * @param id 业务id
     */
    public boolean exist(PK id) {
        try {
            String key = makeFullCacheKey(id);
            if (redisTemplate != null) {
                return redisTemplate.hasKey(key);
            }
        } catch (RuntimeException e) {
            this.catchOnExceptions(e);
        }

        return false;
    }

    /**
     * 对于存在的key设置过期时间，如果不存在则跳过。
     *
     * @param id          业务id
     * @param cacheExpiry 过期时间，如果不填则为默认时间
     * @param timeUnit    时间单位，如不填默认为分钟。
     */
    public void expire(PK id, int cacheExpiry, TimeUnit timeUnit) {
        try {
            String key = makeFullCacheKey(id);
            if (timeUnit == null) {
                timeUnit = TimeUnit.MINUTES;
            }

            if (cacheExpiry == 0) {
                cacheExpiry = this.cacheExpiry;
            }

            if (redisTemplate != null && redisTemplate.hasKey(key)) {
                redisTemplate.expire(key, cacheExpiry, timeUnit);
            }
        } catch (RuntimeException e) {
            this.catchOnExceptions(e);
        }
    }

    /**
     * 是否持久化的缓存，默认为否，返回false，如果需要配置为持久化缓存，不受redis重启影响，需要在子类中重载此方法并返回true
     *
     * @return 非持久化纯缓存返回false
     */
    protected boolean isPersistentCache() {
        return false;
    }

    /**
     * 是否在redis连接超时时抛出RedisConnectionFailureException异常
     *
     * @return 默认不抛出异常，返回false，如果子类返回true，抛出此异常
     */
    protected boolean throwsExceptionOnConnectionTimeout() {
        return false;
    }

    /**
     * 是否不在 getObject/createObject 实现方法中捕获异常，子类重写此方法返回true可不捕获异常，由子类自行处理
     *
     * @return 默认捕获所有异常，返回false，如果子类重写此返回true表示，实现本身不捕获异常，子类或者子类调用者自行处理
     */
    protected boolean doNotCatchExceptionsOnGetOrCreateCache() {
        return false;
    }

    /**
     * 对缓存的值，使用简单的字符串序列化器，请谨慎使用此配置，此序列化器要求缓存的值是string等简单类型，不可是复杂对象，否则会导致反序列化失败
     *
     * @return 默认false，不适用。重写后，返回true，表示使用字符串序列化器
     */
    protected boolean useStringSerializerForValue() {
        return false;
    }

    /**
     * 提供用来避免缓存key冲突的唯一前缀字符串。
     * 所有cacheManager都使用cacheManager本身的类simpleName作为缓存的key前缀。当两个不同项目的cacheManager正好是相同名称，加上使用了相同的id key之后，会导致不同业务的缓存因为相同的key而产生冲突。
     * 为了避免此问题，要求各个cacheManager定义一个唯一的前缀字符串。此字符串请从 {@see <a href="https://www.random.org/strings/?num=10&len=10&digits=on&upperalpha=on&loweralpha=on&unique=on&format=html&rnd=new">生成10位随机字符串</a>} 生成
     * <p>
     * 请勿在随机字符串中包含- 横杆 特殊符号、空格等
     * <p>
     * 对现有的cacheManager，如果业务上允许缓存在项目发布后失效，可以重载此方法来添加前缀，避免冲突。如果业务上不允许缓存失效，请勿重载此方法。
     * 对于新的cacheManager实现，统一要求添加此前缀
     * 一旦生成了唯一前缀并且上线，请勿修改此值，以便保持兼容性
     *
     * @return 随机的唯一的key前缀
     */
    @Deprecated
    protected String provideUniquePrefix() {
        return StringUtils.EMPTY;
    }

    protected CacheNamePrefixEnum registerCacheName() {
        return null;
    }

    final protected void catchOnExceptions(RuntimeException e) {
        if (this.throwsExceptionOnConnectionTimeout() && e instanceof JedisConnectionException) {
            throw e;
        }

        if (this.doNotCatchExceptionsOnGetOrCreateCache()) {
            throw e;
        }
    }

    /**
     * 根据key的类型获取具体的完整的缓存key
     */
    final protected String makeFullCacheKey(PK id) {
        String prefix = this.makeCacheKeyPrefix();
        return prefix + id;
    }

    /**
     * 生成缓存key前缀
     *
     * @return 缓存key前缀
     */
    final protected String makeCacheKeyPrefix() {
        CacheNamePrefixEnum prefixEnum = registerCacheName();
        if (prefixEnum != null) {
            return prefixEnum.getValue();
        }

        StringBuilder prefix = new StringBuilder();
        String uniquePrefix = this.provideUniquePrefix();
        if (StringUtils.isNotBlank(uniquePrefix)) {
            prefix.append(uniquePrefix);
            prefix.append("-");
        }
        prefix.append(getClass().getSimpleName());

        if (cacheKeyType == null) {
            return prefix.toString();
        }

        switch (cacheKeyType) {
            case LIST:
                prefix.append(CacheKeyType.LIST.getValue());
                break;
            case SET:
                prefix.append(CacheKeyType.SET.getValue());
                break;
            case HASH:
                prefix.append(CacheKeyType.HASH.getValue());
                break;
            default:
                break;
        }
        return prefix.toString();
    }
}