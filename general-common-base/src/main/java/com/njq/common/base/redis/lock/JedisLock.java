package com.njq.common.base.redis.lock;
import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisCommands;

public class JedisLock implements Closeable {
    public static final Random R = new Random();
    public static final int TIMEOUT_MILLISECONDS_LEVEL_1 = 5000;
    public static final int TIMEOUT_MILLISECONDS_LEVEL_2 = 7000;
    public static final int TIMEOUT_MILLISECONDS_LEVEL_3 = 9000;
    public static int DEFAULT_TIMEOUT_MILLISECONDS = 3000;
    public static int DEFAULT_EXPIRED_MILLISECONDS = 60000;
    private Logger logger;
    private int timeoutMilliseconds;
    private int expiredMilliseconds;
    private int sleepTime;
    private int randomRange;
    private String lockKey;
    private boolean locked;
    private JedisConnectionFactory connectionFactory;

    public JedisLock(JedisConnectionFactory factory, String lockKey) {
        this(factory, lockKey, DEFAULT_TIMEOUT_MILLISECONDS, DEFAULT_EXPIRED_MILLISECONDS);
    }

    public JedisLock(JedisConnectionFactory factory, String lockKey, int timeoutMilliseconds, int expiredMilliseconds) {
        this.logger = LoggerFactory.getLogger(JedisLock.class);
        this.sleepTime = 100;
        this.locked = false;
        this.connectionFactory = factory;
        this.lockKey = lockKey;
        this.timeoutMilliseconds = timeoutMilliseconds;
        this.expiredMilliseconds = expiredMilliseconds;
    }

    public void close() throws IOException {
        this.unlock();
    }

    public Long expire(int newExpiredMilliseconds) {
        RedisConnection connection = this.connectionFactory.getConnection();
        JedisCommands jedis = (JedisCommands)connection.getNativeConnection();

        Long var4;
        try {
            this.logger.debug("ready to set expire time for {},new expired milliseconds:{}", this.lockKey, newExpiredMilliseconds);
            var4 = jedis.expire(this.lockKey, newExpiredMilliseconds / 1000);
        } finally {
            connection.close();
        }

        return var4;
    }

    public boolean acquire() throws InterruptedException {
        long timeout = System.currentTimeMillis() + (long)this.timeoutMilliseconds;

        while(System.currentTimeMillis() <= timeout && !this.tryAcquire()) {
            int sleep = this.randomRange > 0 ? this.sleepTime + R.nextInt(this.randomRange) : this.sleepTime;
            Thread.sleep((long)sleep, R.nextInt(500));
        }

        return this.locked;
    }

    public boolean acquireNoWait() {
        return this.tryAcquire();
    }

    public void unlock() {
        RedisConnection connection = this.connectionFactory.getConnection();
        JedisCommands jedis = (JedisCommands)connection.getNativeConnection();

        try {
            this.logger.debug("ready to release the jedis lock,locked:{}, lockKey:{}", this.locked, this.lockKey);
            if (this.locked) {
                jedis.del(this.lockKey);
            }
        } finally {
            connection.close();
        }

    }

    public void setCustomSleepTime(int sleepTime, int randomRange) {
        if (sleepTime > 0 && sleepTime < this.timeoutMilliseconds) {
            this.sleepTime = sleepTime;
            if (randomRange > 0 && randomRange < this.timeoutMilliseconds) {
                this.randomRange = randomRange;
            }
        }
    }

    private boolean tryAcquire() {
        this.logger.debug("try to acquire jedis lock for {}", this.lockKey);
        RedisConnection connection = this.connectionFactory.getConnection();
        JedisCommands jedis = (JedisCommands)connection.getNativeConnection();

        boolean var6;
        try {
            long expireTimestamp = System.currentTimeMillis() + (long)this.expiredMilliseconds + 1L;
            String expireTimestampStr = String.valueOf(expireTimestamp);
            if (jedis.setnx(this.lockKey, expireTimestampStr) != 1L) {
                String previousTimestamp = jedis.get(this.lockKey);
                boolean var7;
                if (previousTimestamp != null) {
                    this.logger.debug("try to compare expire time,lock key:{},previous timestamp:{},current expire time:{}", new Object[]{this.lockKey, previousTimestamp, expireTimestampStr});
                    if (Long.parseLong(previousTimestamp) < System.currentTimeMillis()) {
                        String previousTimestampAfterGetSet = jedis.getSet(this.lockKey, expireTimestampStr);
                        if (previousTimestampAfterGetSet == null || previousTimestampAfterGetSet.equals(previousTimestamp)) {
                            this.locked = true;
                            boolean var8 = true;
                            return var8;
                        }

                        this.logger.info("can't acquire jedis lock when multi-thread compete,lock key:{},previous timestamp:{},current expire time:{}", new Object[]{this.lockKey, previousTimestamp, expireTimestampStr});
                    }

                    var7 = false;
                    return var7;
                }

                if (jedis.setnx(this.lockKey, expireTimestampStr) == 1L) {
                    this.locked = true;
                    var7 = true;
                    return var7;
                }

                var7 = false;
                return var7;
            }

            this.locked = true;
            var6 = true;
        } finally {
            connection.close();
        }

        return var6;
    }
}