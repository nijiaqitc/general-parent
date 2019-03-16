package com.njq.common.model.ro;

import com.njq.common.model.po.BaseTitle;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class AnalysisPageRequest {
    private String url;
    private BaseTitle baseTitle;
    private Boolean type = true;
    private String doc;

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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
