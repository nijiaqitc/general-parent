package com.njq.zxgj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.common.base.email.EmailSender;
import com.njq.common.model.dao.BaseUserJpaRepository;
import com.njq.common.model.po.BaseUser;
import com.njq.zxgj.service.TestService;

@Controller
public class TestA {

    @Autowired
    private TestService testService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private BaseUserJpaRepository baseUserJpaRepository;


    @RequestMapping("tsst")
    public String testB() {
        testService.queryList();
//        System.out.println("BBBBBBBBBBBB");
        return "test";
    }

    @RequestMapping("testcc")
    @ResponseBody
    public String testC() {
        try {
            List<BaseUser> userList = baseUserJpaRepository.findAll();
            System.out.println(userList);
            emailSender.sendCheckCode("583522219@qq.com", "123");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "aaaa1111";
    }
}
