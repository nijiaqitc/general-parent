package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;

public class AccessToken extends Expiration {
    private String token;

    public String getToken() {
        return token;
    }

    @JSONField(name = "access_token")
    public void setToken(String token) {
        this.token = token;
    }
}