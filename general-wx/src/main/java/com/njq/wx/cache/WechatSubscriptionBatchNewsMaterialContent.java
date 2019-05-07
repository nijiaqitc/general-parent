package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class WechatSubscriptionBatchNewsMaterialContent {
    @JsonProperty("news_item")
    @JSONField(name = "news_item")
    private List<WechatSubscriptionBatchNewsMaterialItem> newsItem;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("newsItem", newsItem)
                .toString();
    }

    public List<WechatSubscriptionBatchNewsMaterialItem> getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(List<WechatSubscriptionBatchNewsMaterialItem> newsItem) {
        this.newsItem = newsItem;
    }
}