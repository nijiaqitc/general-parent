package com.njq.grab.service.impl;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.po.GrabUrlInfo;

import java.util.HashMap;

/**
 * @author: nijiaqi
 * @date: 2019/2/14
 */
public class GrabUrlInfoFactory {
    private static final HashMap<ChannelType, GrabUrlInfo> map = new HashMap<>();
    private static String imgUrl;
    private static String imagePlace;
    private static String docUrl;
    private static String docPlace;
    private static String decodeJsPlace;

    public static GrabUrlInfo getUrlInfo(ChannelType channel) {
        if (map.get(channel) == null) {
            GrabUrlInfo info = null;
            synchronized (info) {
                info = SpringContextUtil.getBean(GrabUrlInfoService.class).getUrlInfoByChannel(channel.getValue());
                map.put(channel, info);
            }
        }
        return map.get(channel);
    }


    public static String getImgUrl() {
        if (imgUrl == null) {
            synchronized (imgUrl) {
                imgUrl = SpringContextUtil.getValue("image.url");
            }
        }
        return imgUrl;
    }

    public static String getImagePlace() {
        if (imagePlace == null) {
            synchronized (imagePlace) {
                imagePlace = SpringContextUtil.getValue("image.place");
            }
        }
        return imagePlace;
    }

    public static String getDocUrl() {
        if (docUrl == null) {
            synchronized (docUrl) {
                docUrl = SpringContextUtil.getValue("file.url");
            }
        }
        return docUrl;
    }

    public static String getDocPlace() {
        if (docPlace == null) {
            synchronized (docPlace) {
                docPlace = SpringContextUtil.getValue("file.place");
            }
        }
        return docPlace;
    }

    public static String getDecodeJsPlace() {
        if (decodeJsPlace == null) {
            synchronized (decodeJsPlace) {
                decodeJsPlace = SpringContextUtil.getValue("decode.js.place");
            }
        }
        return decodeJsPlace;
    }
}
