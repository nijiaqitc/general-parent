/**
 * 登录过滤
 */
package com.njq.start.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.njq.basis.service.impl.BaseVisitRecordService;
import com.njq.common.base.other.IpUtil;

@Component
@WebFilter(urlPatterns = "/", filterName = "filterName")
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    @Resource
    private BaseVisitRecordService visitRecordService;
    @Resource
    private ThreadPoolTaskExecutor totalTaskExecutor;
    
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
        String ip = IpUtil.getIpAddr((HttpServletRequest)request);
        String url = ((HttpServletRequest) request).getServletPath();
        logger.info("访问ip:"+ip+" -- "+url+" param:"+servletRequest.getQueryString());
        totalTaskExecutor.submit(()->{        	
        	String param = null;        	
        	try {
        		if("GET".equals(servletRequest.getMethod())){
        			param = doGet(servletRequest);
        		}else if("POST".equals(servletRequest.getMethod())){
        			param = doPost(servletRequest);
        		}
				visitRecordService.addRecord(ip, servletRequest.getMethod(), url, servletRequest.getContentType(), param, JSON.json(servletRequest.getParameterMap()));
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        
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


    private static String doGet(HttpServletRequest req) {
        return req.getQueryString();
    }

    private static String doPost(HttpServletRequest req){
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
        	StringBuffer sb = new StringBuffer();
        	is = req.getInputStream();
        	isr = new InputStreamReader(is);
        	br = new BufferedReader(isr);
        	String s = "";
        	while ((s = br.readLine()) != null) {
        		sb.append(s);
        	}
        	return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
        		if(is != null) {
        			is.close();        			
        		}
        		if(isr != null) {
        			isr.close();        			
        		}
        		if(br != null) {
        			br.close();        			
        		}
			} catch (IOException e) {
				logger.info("关闭流出错！！"+e.getMessage());
				e.printStackTrace();
			}
		}
        return null;
    }
    
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
