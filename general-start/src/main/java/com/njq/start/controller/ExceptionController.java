package com.njq.start.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
public class ExceptionController{

    /**
     * 404 页面
     *
     * @return
     */
    @RequestMapping("404")
    public String error404() {
        return "404";
    }

    @RequestMapping("error/404")
    public String error4041() {
        return "404";
    }
    /**
     * 500 页面
     *
     * @return
     */
    @RequestMapping("500")
    public String error500(HttpServletRequest request, HttpServletResponse response) {
    	return "500";
    }
    
    

}