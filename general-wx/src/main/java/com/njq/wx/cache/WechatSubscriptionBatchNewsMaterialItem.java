package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WechatSubscriptionBatchNewsMaterialItem {
    private String title;
    private String thumbMediaId;
    private String showCoverPic;
    private String author;
    private String digest;
    private String content;
    private String url;
    private String contentSourceUrl;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("title", title)
                .append("thumbMediaId", thumbMediaId)
                .append("showCoverPic", showCoverPic)
                .append("author", author)
                .append("digest", digest)
                .append("content", content)
                .append("url", url)
                .append("contentSourceUrl", contentSourceUrl)
                .toString();
    }

    @JsonProperty("title")
    @JSONField(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("thumb_media_id")
    @JSONField(name = "thumb_media_id")
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @JsonProperty("show_cover_pic")
    @JSONField(name = "show_cover_pic")
    public String getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(String showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    @JsonProperty("author")
    @JSONField(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("digest")
    @JSONField(name = "digest")
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @JsonProperty("content")
    @JSONField(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("url")
    @JSONField(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("content_source_url")
    @JSONField(name = "content_source_url")
    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }
}