package com.njq.grab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages={"com.njq.common.model.po"})//由于实体类处于jar包中，扫描jar中的entity注解需要使用此注解
@EnableJpaRepositories("com.njq")//启动jpa并进行扫描
@ComponentScan(basePackages={"com.njq"})//扫描配置文件
@EnableCaching
@SpringBootApplication
public class GeneralParentApplication extends SpringBootServletInitializer{

	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        return builder.sources(GeneralParentApplication.class);  
    }
	
	public static void main(String[] args) {
		SpringApplication.run(GeneralParentApplication.class, args);
	}
	
}
