package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WechatSubscriptionBatchNewsMaterialList {
    private String mediaId;
    private WechatSubscriptionBatchNewsMaterialContent content;
    private String updateTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("mediaId", mediaId)
                .append("content", content)
                .append("updateTime", updateTime)
                .toString();
    }

    @JsonProperty("media_id")
    @JSONField(name = "media_id")
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @JsonProperty("content")
    @JSONField(name = "content")
    public WechatSubscriptionBatchNewsMaterialContent getContent() {
        return content;
    }

    public void setContent(WechatSubscriptionBatchNewsMaterialContent content) {
        this.content = content;
    }

    @JsonProperty("update_time")
    @JSONField(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}