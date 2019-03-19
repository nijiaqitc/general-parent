package com.njq.grab.service.impl;

import java.util.HashMap;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.GrabUrlInfo;

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
            synchronized (map) {
            	if (map.get(channel) == null) {
            		info = SpringContextUtil.getBean(GrabUrlInfoService.class).getUrlInfoByChannel(channel.getValue());
            		map.put(channel, info);            		
            	}
            }
        }
        return map.get(channel);
    }


    public static String getImgUrl() {
        if (imgUrl == null) {
            synchronized (GrabUrlInfoFactory.class) {
            	if (imgUrl == null) {
            		imgUrl = SpringContextUtil.getValue("image.url");            		
            	}
            }
        }
        return imgUrl;
    }

    public static String getImagePlace() {
        if (imagePlace == null) {
            synchronized (GrabUrlInfoFactory.class) {
            	if (imagePlace == null) {
            		imagePlace = SpringContextUtil.getValue("image.place");            		
            	}
            }
        }
        return imagePlace;
    }

    public static String getDocUrl() {
        if (docUrl == null) {
            synchronized (GrabUrlInfoFactory.class) {
            	if (docUrl == null) {
            		docUrl = SpringContextUtil.getValue("file.url");            		
            	}
            }
        }
        return docUrl;
    }

    public static String getDocPlace() {
        if (docPlace == null) {
            synchronized (GrabUrlInfoFactory.class) {
            	if (docPlace == null) {
            		docPlace = SpringContextUtil.getValue("file.place");            		
            	}
            }
        }
        return docPlace;
    }

    public static String getDecodeJsPlace() {
        if (decodeJsPlace == null) {
            synchronized (GrabUrlInfoFactory.class) {
            	if (decodeJsPlace == null) {
            		decodeJsPlace = SpringContextUtil.getValue("decode.js.place");            		
            	}
            }
        }
        return decodeJsPlace;
    }
}
