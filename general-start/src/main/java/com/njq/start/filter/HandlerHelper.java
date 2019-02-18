package com.njq.start.filter;

import com.njq.common.base.exception.BaseKnownException;
import org.springframework.web.method.HandlerMethod;

public class HandlerHelper {
    public HandlerHelper() {
    }

    public static HandlerMethod getHandlerMethod(Object httpHandler) {
        HandlerMethod possibleHandlerMethod = getPossibleHandlerMethod(httpHandler);
        if (possibleHandlerMethod == null) {
            throw new BaseKnownException("不是合法的请求");
        } else {
            return possibleHandlerMethod;
        }
    }

    public static HandlerMethod getPossibleHandlerMethod(Object httpHandler) {
        return httpHandler instanceof HandlerMethod ? (HandlerMethod)httpHandler : null;
    }
}