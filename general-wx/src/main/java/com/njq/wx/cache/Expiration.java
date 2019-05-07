package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import org.joda.time.DateTime;

import java.util.Date;

public class Expiration extends BaseResult {
    private Date expirationTime;
    private int expireSecond;

    public Date getExpirationTime() {
        return expirationTime;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    @JSONField(name = "expires_in")
    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
        this.expirationTime = DateTime.now().plusSeconds(expireSecond).toDate();
    }
}