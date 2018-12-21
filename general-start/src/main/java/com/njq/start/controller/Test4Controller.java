package com.njq.start.controller;

import com.njq.admin.common.UserCommon;
import com.njq.common.util.other.EmailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author: nijiaqi
 * @date: 2018/12/19
 */
@Controller
public class Test4Controller {

    @Resource
    public EmailSender emailSender;

    @RequestMapping("tttt4")
    public void test4(){
        emailSender.sendCheckCode("583522219@qq.com", "调试");
    }
}
