package com.njq.start.filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ErrorPageInterceptor extends HandlerInterceptorAdapter {
	private List<Integer> errorCodeList = Arrays.asList(404, 403, 500, 501);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (errorCodeList.contains(response.getStatus())) {
			/**
			 * forward重定向存在一个问题，它的重定向不会修改response的status值
			 * 即第一次访问若是404，即使重定向到能访问的地址，它的status值依旧是404，
			 * 所以这样会出现无限循环的问题
			 * 内部访问uri原理：在访问路径时它会寻找handleMapper,然后寻找针对这个uri的所有拦截器
			 * 由于当前拦截器配置为针对所有请求的，所以第一次访问是404，重定向后找到新的handle然后重新获取到
			 * 当前拦截器，进来后status值是404，所以死循环了。
			 * 解决办法：当前拦截器配置不拦截重定向的地址
			 */
			request.getRequestDispatcher("/error/" + response.getStatus()).forward(request, response);
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}