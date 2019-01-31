package com.njq.grab.controller;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.custom.CustomAnalysisPerformer;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("test")
@Controller
public class YhWikiController {
    private String useage = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
    private RequestConfig configtime = RequestConfig.custom().setCircularRedirectsAllowed(true).setSocketTimeout(10000).setConnectTimeout(10000).build();
    private static CookieStore store;
    @Autowired
    private PageAnalysisPerformer yhWikiPageAnalysis;

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

    @RequestMapping("getPage")
    public String getPage(Model model) {
        login();
//        String context = yhWikiPageAnalysis.analysisPage("http://wiki.yonghuivip.com/pages/viewpage.action?pageId=2310481");
//        yhWikiPageAnalysis.saveDoc(context, "题题题题题题");
//        String js = "D:\\worksts\\ppcong\\customClearStyle.js";
//        String fun = "decodeStr";
//        model.addAttribute("doc", HtmlDecodeUtil.decodeHtml(context, js, fun));
        model.addAttribute("doc", yhWikiPageAnalysis.analysisPage("http://wiki.yonghuivip.com/pages/viewpage.action?pageId=2300872"));
        return "grab/view";
    }

    @Autowired
    private CustomAnalysisPerformer customAnalysisPerformer;
    @ResponseBody
    @RequestMapping("testcustom")
    public String custt(Model model){
        String doc1 = customAnalysisPerformer.analysisPage("http://www.yunwuxian.net/SEO/3019.html", "content", 1);
        String doc2 = customAnalysisPerformer.analysisPage("https://www.cnblogs.com/ceshi2016/p/7244608.html", "cnblogs_post_body", 1);
        return doc1;
    }

}
