package com.njq.start.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.njq.start.filter.ErrorPageInterceptor;
import com.njq.start.filter.ServletInterceptor;

@Configuration
public class FilterConfig extends WebMvcConfigurationSupport {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		// 添加拦截器，然后排除指定地址，避免出现重定向时出现无限循环
		registry.addInterceptor(new ErrorPageInterceptor()).addPathPatterns("/**").excludePathPatterns("/ffff",
				"/error/404", "/error/403", "/error/500", "/error/501");
		registry.addInterceptor(new ServletInterceptor()).addPathPatterns("/**").excludePathPatterns("/test");
	}

	
//	-------------------------配置静态资源的访问开始----------------------
	private static final String RESOURCES_LOCATION = "/static/";
	private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

	/**
	 * 配置静态文件的访问
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION)
				.setCachePeriod(31556926);
	}

//	-------------------------配置静态资源的访问结束----------------------
	
//	-------------------------配置返回视图乱码开始----------------------
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}

	/**
	 * 默认的springboot视图返回不会出现乱码
	 * 但由于初始化了WebMvcConfigurationSupport 导致配置被覆盖
	 * 所以需要重新配置返回的编码问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Bean
    public HttpMessageConverter responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
    }
//	-------------------------配置返回视图乱码结束----------------------
}
