package com.njq.common.model.ro;

import com.njq.common.model.po.BaseTitle;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public final class AnalysisPageRequestBuilder {
    private String url;
    private BaseTitle baseTitle;
    private Boolean type = true;
    private String doc;

    public AnalysisPageRequestBuilder() {
    }

    public static AnalysisPageRequestBuilder anAnalysisPageRequest() {
        return new AnalysisPageRequestBuilder();
    }

    public AnalysisPageRequestBuilder ofUrl(String url) {
        this.url = url;
        return this;
    }

    public AnalysisPageRequestBuilder ofBaseTitle(BaseTitle baseTitle) {
        this.baseTitle = baseTitle;
        return this;
    }

    public AnalysisPageRequestBuilder ofType(Boolean type) {
        this.type = type;
        return this;
    }

    public AnalysisPageRequestBuilder ofDoc(String doc) {
        this.doc = doc;
        return this;
    }

    public AnalysisPageRequest build() {
        AnalysisPageRequest analysisPageRequest = new AnalysisPageRequest();
        analysisPageRequest.setUrl(url);
        analysisPageRequest.setBaseTitle(baseTitle);
        analysisPageRequest.setType(type);
        analysisPageRequest.setDoc(doc);
        return analysisPageRequest;
    }
}
