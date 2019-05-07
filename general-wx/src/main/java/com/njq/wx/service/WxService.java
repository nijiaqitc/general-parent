package com.njq.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.string.StringUtil2;
import com.njq.wx.cache.AccessToken;
import com.njq.wx.cache.AccessTokenManager;
import com.njq.wx.cache.WechatSubscriptionBatchNewsMaterialResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: nijiaqi
 * @date: 2019/5/7
 */
@Service
public class WxService {
    private static final Logger logger = LoggerFactory.getLogger(WxService.class);
    private AccessTokenManager accessTokenManager;
    private String prefixUrl = "https://api.weixin.qq.com";

    public AccessToken getAccessToken() {
        String appId = "wx689caa81adeb1d96";
        String key = StringUtil2.format("{0}-accessToken", appId);
        AccessToken accessToken = accessTokenManager.get(key);
        Date mark = DateTime.now().plusMinutes(5).toDate();
        if (!accessToken.getExpirationTime().before(mark)) {
            String resultStr = new HtmlGrabUtil()
                    .sendGetFromUrl(StringUtil2.format(prefixUrl + "/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", appId, "ea93151b2195ce45f5ba31268d06eacb"));
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
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        paramsMap.entrySet().forEach(n -> {
            formParams.add(new BasicNameValuePair(n.getKey(), n.getValue()));
        });
        AccessToken accessToken = getAccessToken();
        String str = new HtmlGrabUtil()
                .sendPostFromUrl(prefixUrl+StringUtil2.format("/cgi-bin/material/batchget_material?access_token={0}", accessToken.getToken()), formParams);
        logger.info("获取加载列表", str);
        WechatSubscriptionBatchNewsMaterialResult newsMaterialResult = JSONObject.parseObject(str, WechatSubscriptionBatchNewsMaterialResult.class);
        return str;
    }


    public void sendAllMsg(){

    }


}
