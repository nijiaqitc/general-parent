package com.njq.start.filter;

import com.njq.common.base.interceptor.NeedPwd;
import com.njq.common.util.other.CookieUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: nijiaqi
 * @date: 2019/2/18
 */
public class NeedPwdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HandlerMethod handlerMethod = HandlerHelper.getHandlerMethod(handler);
        /**
         *  针对静态资源的处理器ResourceHttpRequestHandler等需要过滤
         */
        if(handlerMethod == null) {
        	return true;
        }
        NeedPwd annotation = handlerMethod.getMethod().getAnnotation(NeedPwd.class);
        if (annotation == null) {
            return true;
        }
        if (this.checkHadPwd(request, response)) {
            return true;
        } else {
        	request.setAttribute("jumpurl", request.getServletPath());
            request.getRequestDispatcher("/pwdPage").forward(request, response);
            return false;
        }
    }


    private boolean checkHadPwd(HttpServletRequest request,
                                HttpServletResponse response) {
        String cookie = CookieUtil.getCookieVaule("loginFlag", request);
        if (cookie == null) {
            return false;
        }
        return true;
    }
}
