package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class WechatSubscriptionBatchNewsMaterialResult extends BaseResult {
    private static final long serialVersionUID = 1160737080241118516L;
    private Long totalCount;
    private Long itemCount;
    private List<WechatSubscriptionBatchNewsMaterialList> item;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("totalCount", totalCount)
                .append("itemCount", itemCount)
                .append("item", item)
                .toString();
    }

    @JsonProperty("total_count")
    @JSONField(name = "total_count")
    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("item_count")
    @JSONField(name = "item_count")
    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    @JsonProperty("item")
    @JSONField(name = "item")
    public List<WechatSubscriptionBatchNewsMaterialList> getItem() {
        return item;
    }

    public void setItem(List<WechatSubscriptionBatchNewsMaterialList> item) {
        this.item = item;
    }
}