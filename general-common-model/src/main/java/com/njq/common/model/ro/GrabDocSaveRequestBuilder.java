package com.njq.common.model.ro;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public final class GrabDocSaveRequestBuilder {
    private Long id;
    private String title;
    private String channel;
    private String doc;

    public GrabDocSaveRequestBuilder() {
    }

    public static GrabDocSaveRequestBuilder aGrabDocSaveRequest() {
        return new GrabDocSaveRequestBuilder();
    }

    public GrabDocSaveRequestBuilder ofId(Long id) {
        this.id = id;
        return this;
    }

    public GrabDocSaveRequestBuilder ofTitle(String title) {
        this.title = title;
        return this;
    }

    public GrabDocSaveRequestBuilder ofChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public GrabDocSaveRequestBuilder ofDoc(String doc) {
        this.doc = doc;
        return this;
    }

    public GrabDocSaveRequest build() {
        GrabDocSaveRequest grabDocSaveRequest = new GrabDocSaveRequest();
        grabDocSaveRequest.setId(id);
        grabDocSaveRequest.setTitle(title);
        grabDocSaveRequest.setChannel(channel);
        grabDocSaveRequest.setDoc(doc);
        return grabDocSaveRequest;
    }
}
