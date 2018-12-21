package com.njq.common.base.redis;
public enum CacheKeyType {
    /**
     * string类型
     */
    STRING(""),
    /**
     * list类型
     */
    LIST("List"),
    /**
     * set类型
     */
    SET("Set"),
    /**
     * hash类型
     */
    HASH("Hash"),
    /**
     * zset类型
     */
    ZSET("ZSet");

    private String value;

    private CacheKeyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}