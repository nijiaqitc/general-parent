package com.njq.start.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.njq.common.model.po.BaseUser;

public class UrlInterceptor extends  HandlerInterceptorAdapter{

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
//		System.out.println(request.getServletContext().getContextPath()); 
//		System.out.println(request.getServletPath());
		HttpSession session = request.getSession();
		Object sessionId = session.getAttribute("sessionId");
		if (sessionId==null) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/login");
//			request.getRequestDispatcher("/loginOrRegister/loginOrRegister").forward(request, response);
			return false;
		}else {
			BaseUser user = (BaseUser)session.getAttribute("user");
			/*
			 * 如果是管理员那么直接忽略权限直接通过
			 */
			if("admin".equals(user.getAccount())){
				return true;
			}
			String servletPath = request.getServletPath().split("/")[1] ;
			Map<String, Object> power = (Map<String, Object>)session.getAttribute("power");
			/*
			 * 判断是否存在权限去操作
			 */
			if(power.get(servletPath)!=null){
				return true;
			}else{
				//跳转到没有权限页面
				request.getRequestDispatcher("/noPowerPage").forward(request, response);
				return false;
			}
		}
	}
}

