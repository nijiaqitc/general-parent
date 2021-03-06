package com.njq.start.filter;

import com.njq.common.base.redis.RedisCommon;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class ServletContextInit implements ServletContextAware,
        InitializingBean {
    private ServletContext sc;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${image.url}")
    private String imageUrl;
    @Value("${auth.url}")
    private String authUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        String path = sc.getContextPath();
//		PropertyUtil.init(sc);
        sc.setAttribute("path", path);
        sc.setAttribute("resPath", path + "/static");
        sc.setAttribute("imgPath", imageUrl);
        sc.setAttribute("authUrl", authUrl);
        RedisCommon.redisTemplate = this.redisTemplate;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.sc = servletContext;
    }

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
