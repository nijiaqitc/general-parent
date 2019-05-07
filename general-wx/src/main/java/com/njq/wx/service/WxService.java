package com.njq.wx.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.string.StringUtil2;
import com.njq.wx.cache.AccessToken;
import com.njq.wx.cache.AccessTokenManager;

/**
 * @author: nijiaqi
 * @date: 2019/5/7
 */
@Service
public class WxService {
    private static final Logger logger = LoggerFactory.getLogger(WxService.class);
    @Autowired
    private AccessTokenManager accessTokenManager;
    private String prefixUrl = "https://api.weixin.qq.com";

    public AccessToken getAccessToken() {
        String appId = "wx689caa81adeb1d96";
        String key = StringUtil2.format("{0}-accessToken", appId);
        AccessToken accessToken = accessTokenManager.get(key);
        Date mark = DateTime.now().plusMinutes(5).toDate();
        if (accessToken==null||accessToken.getExpirationTime().before(mark)) {
        	String sendUrl1 = StringUtil2.format(prefixUrl + "/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", appId, "ea93151b2195ce45f5ba31268d06eacb");
        	
        	String sendUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx689caa81adeb1d96&secret=ea93151b2195ce45f5ba31268d06eacb";
        	
        	if(sendUrl1.equals(sendUrl)) {
        		System.out.println(111);
        	}
        	
            String resultStr = new HtmlGrabUtil().sendGetFromUrl(sendUrl);
            accessToken = JSONObject.parseObject(resultStr, AccessToken.class);
            int expirationMinutes = accessToken.getExpireSecond() / 60;
            accessTokenManager.update(key, accessToken, expirationMinutes);
            return accessToken;
        }
        return accessToken;
    }

    public String loadMediaList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("type", "news");
        paramsMap.put("count", "20");
        paramsMap.put("offset", "0");
        AccessToken accessToken = getAccessToken();
        String sendUrl = prefixUrl+StringUtil2.format("/cgi-bin/material/batchget_material?access_token={0}", accessToken.getToken());
        String str = new HtmlGrabUtil().sendPostFromUrlJson(sendUrl, JSON.toJSONString(paramsMap));
        logger.info("获取加载列表", str);
//        WechatSubscriptionBatchNewsMaterialResult newsMaterialResult = JSONObject.parseObject(str, WechatSubscriptionBatchNewsMaterialResult.class);
        return str;
    }


    public void sendAllMsg(){

    }


}
