package com.njq.common.base.redis;
public enum CacheKeyType {
    STRING(""),
    LIST("List"),
    SET("Set"),
    HASH("Hash"),
    ZSET("ZSet");

    private String value;

    private CacheKeyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}