package com.njq.start.filter;

import org.springframework.web.method.HandlerMethod;

public class HandlerHelper {
    public HandlerHelper() {
    }

    public static HandlerMethod getHandlerMethod(Object httpHandler) {
        HandlerMethod possibleHandlerMethod = getPossibleHandlerMethod(httpHandler);
        if (possibleHandlerMethod == null) {
            return null;
        } else {
            return possibleHandlerMethod;
        }
    }

    public static HandlerMethod getPossibleHandlerMethod(Object httpHandler) {
        return httpHandler instanceof HandlerMethod ? (HandlerMethod)httpHandler : null;
    }
}