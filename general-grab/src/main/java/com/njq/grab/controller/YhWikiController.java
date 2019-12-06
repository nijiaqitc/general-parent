package com.njq.grab.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njq.basis.service.impl.BaseTipService;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.file.load.api.FileLoadService;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabService;
import com.njq.grab.service.impl.custom.CustomAnalysisPerformer;

@SuppressWarnings("unused")
@RequestMapping("test")
@Controller
public class YhWikiController {
    @Autowired
    private PageAnalysisPerformer yhWikiPageAnalysis;
    @Resource
    private FileLoadService fileLoadService;

    @RequestMapping("login")
    public void login() {
        Map<String, String> param = new HashMap<>();
        param.put("os_username", "nijiaqi");
        param.put("os_password", "KTdP2uKrL415");
        param.put("os_cookie", "1");
        HtmlGrabUtil
                .build(ChannelType.YH_WIKI.getValue())
                .login("http://wiki.yonghuivip.com/dologin.action", "post", param);
        return;
    }

    @Autowired
    private PageAnalysisPerformer csdnPageAnalysisPerformer;

    @RequestMapping("getPage")
    public String getPage(Model model) {
//        login();
        BaseTitle base = new BaseTitle();
//        
//        String context = yhWikiPageAnalysis.analysisPage(new AnalysisPageRequestBuilder()
//                .ofUrl("http://wiki.yonghuivip.com/pages/viewpage.action?pageId=3544929")
//                .ofBaseTitle(base)
//                .build());
//        model.addAttribute("doc", context);
        
        
//        yhWikiPageAnalysis.saveDoc(context, "题题题题题题11");
//        String js = "D:\\worksts\\ppcong\\customClearStyle.js";
//        String fun = "decodeStr";
//        base.setId(14193L);
        model.addAttribute("doc", csdnPageAnalysisPerformer.analysisPage(new AnalysisPageRequestBuilder()
                .ofUrl("https://blog.csdn.net/qq_41058526/article/details/80631548")
                .ofType(false)
                .ofBaseTitle(base)
                .build()));
        return "grab/view";
    }

    @RequestMapping("gpage")
    public String gpage(Model model,Long docId) {
    	model.addAttribute("doc", grabService.readDoc(docId).getDoc());
    	return "grab/view";
    }
    
    @ResponseBody
    @RequestMapping("ttt")
    public String tttt(Model model) {
//    	UpFileInfoRequest req = new UpFileInfoRequest();
//    	req.setType(ChannelType.CSDN);
//    	req.setUrl("http://img-blog.csdn.net/20180428102835632");
//    	SaveFileInfo info = fileLoadService.loadPic(req);
//    	System.out.println(info.getRealPlace());
    	
    	String src = "img-blog.csdn.net/20180428102835632";
    	if(!(src.split("\\.").length>2)) {
    		src = "https://blog.csdn.net/" + src;
    		
    	}
    	if (!src.startsWith(SendConstants.HTTP_PREFIX)) {
    		src = SendConstants.HTTP_PREFIX+"://"+src;
    	}
    	System.out.println(src);
    	
    	return "";
    }
    
    
    
	@Autowired
    private CustomAnalysisPerformer customAnalysisPerformer;
    @Autowired
    private BaseTipService baseTipService;
    @Autowired
    private GrabService grabService;
    @ResponseBody
    @RequestMapping("testcustom")
    public String custt(Model model){
//        String doc1 = customAnalysisPerformer.analysisPage("http://www.yunwuxian.net/SEO/3019.html", "content", 1);
//        String doc2 = customAnalysisPerformer.analysisPage("https://www.cnblogs.com/ceshi2016/p/7244608.html", "cnblogs_post_body", 1);
//        GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI);
//        System.out.println(GrabUrlInfoFactory.getImgUrl());
//        return doc1;
//        csdnPageAnalysisPerformer.loadMenu("https://blog.csdn.net/weianluo/article/category/6948630", 11L);
    	grabService.repairTip();
        return "";
    }



    

}
