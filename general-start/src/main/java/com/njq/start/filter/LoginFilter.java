/**
 * 登录过滤
 */
package com.njq.start.filter;

import com.njq.common.base.other.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/", filterName = "filterName")
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @SuppressWarnings("unused")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String contextPath = servletRequest.getContextPath();
        String servletPath = servletRequest.getServletPath();
        HttpSession session = servletRequest.getSession();
        session.setAttribute("basePath", servletRequest.getRequestURI());
        logger.info("访问ip:"+IpUtil.getIpAddr((HttpServletRequest)request)+((HttpServletRequest) request).getServletPath());

		/*
		Object sessionId = session.getAttribute("sessionId");
		String url = servletPath.split("/")[1];
		
		 // 判断是否是无须权限的url
		 
		if("login".equals(url)){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		 //* 判断是否存在sessionid，这是每个登录用户必有的标识符
		 
		if(sessionId==null){
			servletResponse.sendRedirect("/login.jsp");
			return;
		}
		User user=(User)session.getAttribute("user");
		
		//* 判断是否是超级管理员
		//* 如果是那么一切请求都允许操作
		 
		if("admin".equals(user.getAccount())){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}else{
			Map<String, Object> m = (Map<String, Object>)session.getAttribute("power");
			
			 * 权限判断
			 
			if(m.get(servletPath)!=null){
				chain.doFilter(servletRequest, servletResponse);
				return;
			}
			servletResponse.sendRedirect("/login.jsp");
			return;
		}*/
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
//		servletResponse.sendRedirect("/index.jsp");
//		chain.doFilter(servletRequest, servletResponse);
//		return ;
		
		/*// 取当前登录的用户
		User user = null;
		HttpSession session = servletRequest.getSession();*/
//		chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
