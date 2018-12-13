package com.njq.start.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import com.njq.start.filter.ServletContextInit;

@Configuration
public class BaseConfig {
	// 视图前缀
	private static final String VIEW_PREFIX = "/WEB-INF/jsp/";
	// 视图后缀
	private static final String VIEW_SUFFIX = ".jsp";
	// 视图的内容类型。
	private static final String VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";

	/**
	 * 配置 视图解析器
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver jspviewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setCache(false);
		resolver.setPrefix(VIEW_PREFIX);
		resolver.setSuffix(VIEW_SUFFIX);
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setContentType(VIEW_CONTENT_TYPE);
		resolver.setViewClass(JstlView.class);
		resolver.setOrder(3);
		return resolver;
	}

	@Bean
	public FreeMarkerConfigurer ftlconfigure() {
		FreeMarkerConfigurer configure = new FreeMarkerConfigurer();
		configure.setTemplateLoaderPath("/WEB-INF/ftl/");
		configure.setDefaultEncoding("utf-8");
		return configure;
	}

	@Bean
	public ViewResolver ftlViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(false);
		resolver.setSuffix(".ftl");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setContentType(VIEW_CONTENT_TYPE);
		resolver.setViewNames("freemarker");
		resolver.setViewClass(FreeMarkerView.class);
		resolver.setOrder(2);
		return resolver;
	}

	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/thymeleaf/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(0);
		templateResolver.setCacheable(false);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setViewNames(new String[]{"thymeleaf/*"});
		resolver.setViewClass(ThymeleafView.class);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType(VIEW_CONTENT_TYPE);
		resolver.setCache(false);
		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public ServletContextInit servletContextInit() {
		return new ServletContextInit();
	}

}
