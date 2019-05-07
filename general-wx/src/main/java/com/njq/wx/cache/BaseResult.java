package com.njq.wx.cache;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseResult implements Serializable {
    private static final long serialVersionUID = 148416168471322L;
    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    @JSONField(name = "errcode")
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    @JSONField(name = "errmsg")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("errorCode", errorCode)
                .append("message", message)
                .toString();
    }
}