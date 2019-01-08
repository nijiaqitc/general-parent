package com.njq.grab.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
		login();
    	String context = yhWikiPageAnalysis.analysisPage("http://wiki.yonghuivip.com/pages/viewpage.action?pageId=2300252");
    	String js="D:\\worksts\\ppcong\\customClearStyle.js";
    	String fun = "decodeStr";
    	model.addAttribute("doc",HtmlDecodeUtil.decodeHtml(context,js,fun));
    	model.addAttribute("doc",context);
    	return "grab/view";
    }
    
    
    
    
    
}
