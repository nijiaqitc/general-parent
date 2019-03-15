package com.njq.common.base.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;


public class JedisLock implements Closeable {
    public static final Random R = new Random();
    public static final int TIMEOUT_MILLISECONDS_LEVEL_1 = 5000;
    public static final int TIMEOUT_MILLISECONDS_LEVEL_2 = 7000;
    public static final int TIMEOUT_MILLISECONDS_LEVEL_3 = 9000;
    public static int DEFAULT_TIMEOUT_MILLISECONDS = 3000;
    public static int DEFAULT_EXPIRED_MILLISECONDS = 60000;
    private static final Logger logger = LoggerFactory.getLogger(JedisLock.class);
    private int timeoutMilliseconds;
    private int expiredMilliseconds;
    private int sleepTime;
    private int randomRange;
    private String lockKey;
    private boolean locked;
    private RedisConnectionFactory connectionFactory;

    public JedisLock(RedisConnectionFactory factory, String lockKey) {
        this(factory, lockKey, DEFAULT_TIMEOUT_MILLISECONDS, DEFAULT_EXPIRED_MILLISECONDS);
    }

    public JedisLock(RedisConnectionFactory factory, String lockKey, int timeoutMilliseconds, int expiredMilliseconds) {
        this.sleepTime = 100;
        this.locked = false;
        this.connectionFactory = factory;
        this.lockKey = lockKey;
        this.timeoutMilliseconds = timeoutMilliseconds;
        this.expiredMilliseconds = expiredMilliseconds;
    }

    @Override
    public void close() throws IOException {
        this.unlock();
    }

    public Boolean expire(int newExpiredMilliseconds) {
        RedisConnection connection = this.connectionFactory.getConnection();
        Boolean bl = false;
        try {
            connection.expire(this.lockKey.getBytes(), 1000L);
            logger.info("ready to set expire time for {},new expired milliseconds:{}", this.lockKey, newExpiredMilliseconds);
            bl = connection.expire(this.lockKey.getBytes(), Long.valueOf(newExpiredMilliseconds / 1000));
        } finally {
            connection.close();
        }
        return bl;
    }

    public boolean acquire() throws InterruptedException {
        long timeout = System.currentTimeMillis() + (long) this.timeoutMilliseconds;
        while (System.currentTimeMillis() <= timeout && !this.tryAcquire()) {
            int sleep = this.randomRange > 0 ? this.sleepTime + R.nextInt(this.randomRange) : this.sleepTime;
            Thread.sleep((long) sleep, R.nextInt(500));
        }
        return this.locked;
    }

    public boolean acquireNoWait() {
        return this.tryAcquire();
    }

    public void unlock() {
        RedisConnection connection = this.connectionFactory.getConnection();
        try {
            logger.debug("ready to release the jedis lock,locked:{}, lockKey:{}", this.locked, this.lockKey);
            if (this.locked) {
                connection.del(this.lockKey.getBytes());
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
        logger.debug("try to acquire jedis lock for {}", this.lockKey);
        RedisConnection connection = this.connectionFactory.getConnection();
        boolean var6;
        try {
            long expireTimestamp = System.currentTimeMillis() + (long) this.expiredMilliseconds + 1L;
            String expireTimestampStr = String.valueOf(expireTimestamp);
            if (!connection.setNX(this.lockKey.getBytes(), expireTimestampStr.getBytes())) {
                byte[] bt = connection.get(this.lockKey.getBytes());
                String previousTimestamp = new String(bt);
                boolean var7;
                if (previousTimestamp != null) {
                    logger.debug("try to compare expire time,lock key:{},previous timestamp:{},current expire time:{}", new Object[]{this.lockKey, previousTimestamp, expireTimestampStr});
                    if (Long.parseLong(previousTimestamp) < System.currentTimeMillis()) {
                        byte[] bc = connection.getSet(this.lockKey.getBytes(), expireTimestampStr.getBytes());
                        String previousTimestampAfterGetSet = new String(bc);
                        if (previousTimestampAfterGetSet == null || previousTimestampAfterGetSet.equals(previousTimestamp)) {
                            this.locked = true;
                            boolean var8 = true;
                            return var8;
                        }
                        logger.info("can't acquire jedis lock when multi-thread compete,lock key:{},previous timestamp:{},current expire time:{}", new Object[]{this.lockKey, previousTimestamp, expireTimestampStr});
                    }
                    var7 = false;
                    return var7;
                }
                if (connection.setNX(this.lockKey.getBytes(), expireTimestampStr.getBytes())) {
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