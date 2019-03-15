package com.njq.common.model.ro;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public class GrabDocSaveRequest {

    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 文档内容
     */
    private String doc;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
