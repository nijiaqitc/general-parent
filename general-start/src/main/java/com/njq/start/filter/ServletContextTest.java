package com.njq.start.filter;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
//@Component
public class ServletContextTest implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
//        System.out.println("111111111111111111");
        
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("111111111111111111");
//        
//    }

    /*@Override
    public void setServletContext(ServletContext arg0) {
        System.out.println("222222222222222222222222");
    }*/
}
