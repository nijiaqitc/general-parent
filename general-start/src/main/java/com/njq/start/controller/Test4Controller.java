package com.njq.start.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.njq.basis.service.impl.BaseCodeService;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.email.EmailSender;
import com.njq.common.model.po.YxlDocSearch;

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
    
    private Logger logger = LoggerFactory.getLogger(Test4Controller.class);  
    @Value("${spring.mail.host}")
    private String ffff;
    @RequestMapping("tttt5")
    public void tttt5(@RequestParam(value="bbb") String bbb ) {
    	try {
    		List<String>  aaa = new ArrayList<String>();
    		System.out.println(aaa.get(2));
			System.out.println(ffff);
		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
    }
    @Resource
    private  ThreadPoolTaskExecutor loadPageTaskExecutor;
    @RequestMapping("ttt444")
    public void ttt6() {
    	List<Callable<String>> tasks = new ArrayList<>();
    	CountDownLatch lanch= new CountDownLatch(7);
    	List<Future<String>> mutexList = new ArrayList<>();
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
    	mutexList.add(loadPageTaskExecutor.submit(this.getCallable(lanch)));
//    	tasks.add(this.getCallable(lanch));
//    	tasks.add(this.getCallable(lanch));
//    	tasks.add(this.getCallable(lanch));
//    	tasks.add(this.getCallable(lanch));
    	try {
    		
    		lanch.await(3, TimeUnit.SECONDS);
    		mutexList.forEach(f->{
    			if (f.isDone()) {
    				try {
						System.out.println(f.get());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
                } else {
                	System.out.println("已取消");
                    f.cancel(true);
                }
    		});
    		
    		
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Callable<String> getCallable(CountDownLatch latch){
    	List<String> list = new ArrayList<>();
    	list.add("1");
    	list.add("2");
    	list.add("3");
    	list.add("4");
    	Callable<String> callable = () -> {
    		list.forEach(n->{
    			System.out.println(n);
    		});
    		TimeUnit.SECONDS.sleep(5);
    		latch.countDown();
    		return "abc";
    	};
    	return callable;
    }
    
    @RequestMapping(value = "generateInfo", method = RequestMethod.POST)
    public void ttt7(@RequestBody @Valid Deee d, BindingResult bindingResult,HttpServletRequest request) {
    	if(bindingResult.hasErrors()){
    		System.out.println(bindingResult.getFieldError().getDefaultMessage());
    	}
    	
    	System.out.println("11111");
    }
    
}
