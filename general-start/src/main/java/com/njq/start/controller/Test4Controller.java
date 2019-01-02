package com.njq.start.controller;

import com.njq.basis.service.impl.BaseCodeService;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.email.EmailSender;
import com.njq.common.model.po.YxlDocSearch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author: nijiaqi
 * @date: 2018/12/19
 */
@Controller
public class Test4Controller {

    @Resource
    public EmailSender emailSender;

    @Resource
    public BaseCodeService baseCodeService;
    
    @RequestMapping("tttt4")
    public void test4() {
        emailSender.sendCheckCode("583522219@qq.com", "调试");
    }
    
    @Resource
    private DaoCommon<YxlDocSearch> yxlDocSearchDao;
    @RequestMapping("tttt6")
    public void test5() {
//    	baseCodeService.queryAllCode(null,0,10);
    	String hql = "select docId as docId ,id as id ,title as title from com.njq.common.model.po.YxlDocSearch where  typeId = 80";
    	List<YxlDocSearch> list =  yxlDocSearchDao.queryHqlList(hql);
    	System.out.println(list.get(0));
    }
    
}
