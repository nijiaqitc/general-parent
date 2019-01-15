package com.njq.common.model.vo.grab;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/1/14
 */
public class GrabTypeInfo implements Serializable {
    private static final long serialVersionUID = -7199730294516314443L;
    private String type;
    private List<GrabTitleVO> grabTitleVOList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GrabTitleVO> getGrabTitleVOList() {
        return grabTitleVOList;
    }

    public void setGrabTitleVOList(List<GrabTitleVO> grabTitleVOList) {
        this.grabTitleVOList = grabTitleVOList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("type", type)
                .append("grabTitleVOList", grabTitleVOList)
                .toString();
    }
}
