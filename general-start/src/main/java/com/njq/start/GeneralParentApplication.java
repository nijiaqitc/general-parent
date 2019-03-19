package com.njq.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//由于实体类处于jar包中，扫描jar中的entity注解需要使用此注解
@EntityScan(basePackages={"com.njq.common.model.po"})
//启动jpa并进行扫描
@EnableJpaRepositories("com.njq")
//扫描指定包中的类
@ComponentScan(basePackages={"com.njq"})
@ImportResource({"classpath:applicationContext-dubbo.xml"})
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
