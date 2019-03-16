package com.njq.grab.service.impl;

import com.njq.basis.service.impl.BaseFileService;
import com.njq.common.model.po.BaseTitle;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public final class GrabConfigBuilder {
    private BaseFileService baseFileService;
    private String grabUrl;
    private String url;
    private BaseTitle baseTitle;

    public GrabConfigBuilder() {
    }

    public static GrabConfigBuilder aGrabConfig() {
        return new GrabConfigBuilder();
    }

    public GrabConfigBuilder ofBaseFileService(BaseFileService baseFileService) {
        this.baseFileService = baseFileService;
        return this;
    }

    public GrabConfigBuilder ofGrabUrl(String grabUrl) {
        this.grabUrl = grabUrl;
        return this;
    }

    public GrabConfigBuilder ofUrl(String url) {
        this.url = url;
        return this;
    }

    public GrabConfigBuilder ofBaseTitle(BaseTitle baseTitle) {
        this.baseTitle = baseTitle;
        return this;
    }

    public GrabConfig build() {
        GrabConfig grabConfig = new GrabConfig();
        grabConfig.setBaseFileService(baseFileService);
        grabConfig.setGrabUrl(grabUrl);
        grabConfig.setUrl(url);
        grabConfig.setBaseTitle(baseTitle);
        return grabConfig;
    }
}
