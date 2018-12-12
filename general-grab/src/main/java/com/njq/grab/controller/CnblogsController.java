package com.njq.grab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njq.grab.service.PageAnalysisPerformer;

@RequestMapping("cnblogs")
@Controller
public class CnblogsController {
	@Autowired
	public PageAnalysisPerformer yhWikiPageAnalysis;
	@Autowired
	public PageAnalysisPerformer cgblogsPageAnalysis;
	
	@RequestMapping("login")
    public String login() {
		return yhWikiPageAnalysis.analysisPage("");
	}
	
	@RequestMapping("cgb")
    public String cgb() {
		
		return cgblogsPageAnalysis.analysisPage("https://www.cnblogs.com/ceshi2016/category/1065679.html");
	}
}
