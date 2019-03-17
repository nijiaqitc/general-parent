package com.njq.grab.service.impl;

import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.common.model.po.BaseTitle;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class GrabConfig {
    private BaseFileService baseFileService;
    private BaseTipService baseTipService;
    private String grabUrl;
    private String url;
    private BaseTitle baseTitle;
    private Boolean type = true;
    
    public BaseFileService getBaseFileService() {
        return baseFileService;
    }

    public void setBaseFileService(BaseFileService baseFileService) {
        this.baseFileService = baseFileService;
    }

    public String getGrabUrl() {
        return grabUrl;
    }

    public void setGrabUrl(String grabUrl) {
        this.grabUrl = grabUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BaseTitle getBaseTitle() {
        return baseTitle;
    }

    public void setBaseTitle(BaseTitle baseTitle) {
        this.baseTitle = baseTitle;
    }

    public BaseTipService getBaseTipService() {
        return baseTipService;
    }

    public void setBaseTipService(BaseTipService baseTipService) {
        this.baseTipService = baseTipService;
    }

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}
    
}
