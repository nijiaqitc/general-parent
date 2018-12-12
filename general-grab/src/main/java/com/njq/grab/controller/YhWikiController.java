package com.njq.grab.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.PageAnalysisPerformer;

@RequestMapping("test")
@Controller
public class YhWikiController {
	private String useage = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
    private RequestConfig configtime=RequestConfig.custom().setCircularRedirectsAllowed(true).setSocketTimeout(10000).setConnectTimeout(10000).build();
    private static CookieStore store ;
    @Autowired
    private PageAnalysisPerformer yhWikiPageAnalysis;
    
    @RequestMapping("login")
    public void login() {
    	Map<String, String> param =new HashMap<>();
    	param.put("os_username", "nijiaqi");
    	param.put("os_password", "KTdP2uKrL415");
    	param.put("os_cookie", "1");
    	HtmlGrabUtil
    		.build("wiki")
    		.login("http://wiki.yonghuivip.com/dologin.action", "post", param);
    	return;
    }
    
    @RequestMapping("getPage")
    public String getPage(Model model) {
//    	String context = HtmlGrabUtil.build("wiki").getContext("http://wiki.yonghuivip.com/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=true&pageId=2302391&treeId=0&startDepth=0&mobile=false&ancestors=2302391&treePageId=2302391");
    	String context = yhWikiPageAnalysis.analysisPage("");
//    	String context = "";
//    	context = "<img src=\"http://img.zcool.cn/community/0125fd5770dfa50000018c1b486f15.jpg@1280w_1l_2o_100sh.jpg\" style=\"width: 232px; height: 176px; cursor: pointer;float: right;\" title=\"点击查看源网页\">";
    	;
    	model.addAttribute("body",HtmlDecodeUtil.decodeHtml(context));
    	return "index";
    }
    
    
    
    
    
}
