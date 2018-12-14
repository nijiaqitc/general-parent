package com.njq.start.config;

import com.njq.start.filter.ServletContextInit;
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

@Configuration
public class BaseConfig {
    /**
     * 视图的内容类型
     */
    private static final String VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";

    /**
     * 配置标准的html解析器
     *
     * @return
     */
    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        //视图解析器的使用优先级，越小优先级越高
        templateResolver.setOrder(0);
        //是否使用视图缓存，开发时不建议启用缓存，改动即可生效
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /**
     * 生成标准解析器的模板引擎
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    /**
     * 配置thymeleaf解析器，
     * 它得基于标准的解析器模板引擎
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setViewClass(ThymeleafView.class);
        /*
         * 他会根据视图名字开头来决定使用哪一类视图解析器
         * 返回视图名字（controller里面return的以这个开头，如return “thymeleaf/thytest”），
         * 返回的视图全路径为Prefix+“thymeleaf/thytest”
         * 比如此视图路径为：“/WEB-INF/thymeleaf/thytest”
         */
        thymeleafViewResolver.setViewNames(new String[]{"thymeleaf/*"});
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        //视图解析器的使用优先级，越小优先级越高
        thymeleafViewResolver.setOrder(1);
        //是否使用视图缓存，开发时不启用缓存，改动即可生效
        thymeleafViewResolver.setCache(false);
        return thymeleafViewResolver;
    }

//freemark视图解析器--------------------------------------------------------------

    /**
     * 生成freemark的配置类，不配置加载freemark解析器会报错
     *
     * @return
     */
    @Bean
    public FreeMarkerConfigurer ftlconfigure() {
        FreeMarkerConfigurer configure = new FreeMarkerConfigurer();
        configure.setTemplateLoaderPath("/WEB-INF/");
        configure.setDefaultEncoding("utf-8");
        return configure;
    }

    /**
     * 生成freemark的视图解析器
     *
     * @return
     */
    @Bean
    public ViewResolver ftlViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setSuffix(".ftl");
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setContentType(VIEW_CONTENT_TYPE);
        /*
         * 他会根据视图名字开头来决定使用哪一类视图解析器
         * 返回视图名字（controller里面return的以这个开头，如return “ftl/freetest”），
         * 返回的视图全路径为Prefix+“ftl/freetest”，注意：Prefix只要填/WEB-INF/即可
         * 比如此视图路径为：“/WEB-INF/ftl/freetest”
         * 视图解析器是在dispatcherservlet中的resolveViewName方法进行解析
         */
        resolver.setViewNames("ftl/*");
        resolver.setViewClass(FreeMarkerView.class);
        //视图解析器的使用优先级，越小优先级越高
        resolver.setOrder(2);
        return resolver;
    }
//jsp视图解析器--------------------------------------------------------------

    /**
     * 配置jsp视图解析器
     *
     * @return
     */
    @Bean
    public ViewResolver jspviewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setCache(false);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setContentType(VIEW_CONTENT_TYPE);
        resolver.setViewClass(JstlView.class);
        //不填视图解析名字，说明除了上面的全用此解析器
//		resolver.setViewNames("jsp/*");
        //视图解析器的使用优先级，越小优先级越高
        resolver.setOrder(3);
        return resolver;
    }

    /**
     * 初始化系统的常用常量
     */
    @Bean
    public ServletContextInit servletContextInit() {
        return new ServletContextInit();
    }

}
