package com.njq.grab.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.file.load.api.FileLoadService;
import com.njq.file.load.api.model.SaveFileInfo;
import com.njq.file.load.api.model.UpFileInfoRequestBuilder;
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
		return yhWikiPageAnalysis.analysisPage(new AnalysisPageRequestBuilder()
				.ofUrl("")
				.ofBaseTitle(null)
				.build());
	}
	
	@RequestMapping("cgb")
    public String cgb() {
		cgblogsPageAnalysis.analysisPage(new AnalysisPageRequestBuilder()
				.ofUrl("https://www.cnblogs.com/itzgr/p/10546584.html")
				.ofBaseTitle(null)
				.ofType(true)
				.build());
//		return cgblogsPageAnalysis.analysisPage("https://www.cnblogs.com/ceshi2016/category/1065679.html");
		return "";
	}
	
	@RequestMapping("loadMenu")
    public String loadMenu(Model model) {
//		cgblogsPageAnalysis.loadMenu("https://www.cnblogs.com/ceshi2016/mvc/blog/sidecolumn.aspx?blogApp=ceshi2016", 1L);
		
//		cgblogsPageAnalysis.analysisPage("https://www.cnblogs.com/ceshi2016/mvc/blog/sidecolumn.aspx?blogApp=ceshi2016");
//		return cgblogsPageAnalysis.analysisPage("https://www.cnblogs.com/ceshi2016/category/1065679.html");
        model.addAttribute("doc", cgblogsPageAnalysis.analysisPage(new AnalysisPageRequestBuilder()
    			.ofUrl("https://www.cnblogs.com/ibethfy/p/10332819.html")
    			.ofBaseTitle(null)
    			.ofType(false)
    			.build()));
        return "grab/view";
	}
	
	
	@Resource
    private FileLoadService fileLoadService;
	@RequestMapping("tsts")
    public void tsts() {
		
		SaveFileInfo fileInfo = fileLoadService.loadPic(new UpFileInfoRequestBuilder()
				.ofUrl("https://t12.baidu.com/it/u=2126351807,3022701237&fm=76").ofType(ChannelType.CUSTOM).build());
		System.out.println(fileInfo);
		System.out.println(fileInfo.getRealPlace());
	}
}
