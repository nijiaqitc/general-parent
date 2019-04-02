package com.njq.start.testcontroller;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author: nijiaqi
 * @date: 2018/10/19
 */
public class RedPackagePictureInfo implements Serializable {
	private static final long serialVersionUID = 1446617137861978021L;
	/**
     * 关联标签
     */
    private String relevanceLabel;
    /**
     * 图片地址
     */
    private String picSrc;

    public String getRelevanceLabel() {
        return relevanceLabel;
    }

    public void setRelevanceLabel(String relevanceLabel) {
        this.relevanceLabel = relevanceLabel;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("relevanceLabel", relevanceLabel)
                .append("picSrc", picSrc)
                .toString();
    }
}