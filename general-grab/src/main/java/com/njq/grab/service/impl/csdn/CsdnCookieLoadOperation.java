package com.njq.grab.service.impl.csdn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleGrab;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component
public class CsdnCookieLoadOperation {
	private final BaseTipService baseTipService;
    private final BaseFileService baseFileService;
    @Resource
    private BaseTitleService baseTitleService;
    @Resource
    private SaveTitlePerformer grabSaveTitlePerformer;
    @Resource
    private PageAnalysisPerformer csdnPageAnalysisPerformer;
    @Resource
    private DaoCommon<BaseTitleGrab> baseTitleGrabDao;
    @Autowired
	public CsdnCookieLoadOperation(BaseTipService baseTipService, BaseFileService baseFileService) {
		super();
		this.baseTipService = baseTipService;
		this.baseFileService = baseFileService;
	}
	public void load(String url,String cookie) {
		if(StringUtil2.IsNotEmpty(url)) {
			String str = loadDoc(url, cookie);
			Document doc = null;
			try {
				System.out.println(new String(str.getBytes(),"UTF-8"));
				System.out.println(new String(str.getBytes(),"GBK"));
				doc = Jsoup.parse(new String(str.getBytes(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(doc == null) {
				return;
			}
			LeftMenu left=new LeftMenu();
			String title = doc.getElementsByTag("title").get(0).html(); 
			left.setName(title);
			System.out.println(doc.getElementsByTag("title").get(0).html());
			BaseTitle t = grabSaveTitlePerformer.saveTitle(new SaveTitleRequestBuilder()
					.ofTypeId(1L)
					.ofChannel("csdn")
					.onMenu(left)
					.build());
			t.setTypeId(1L);
			GrabConfig config = new GrabConfigBuilder()
					.ofBaseFileService(baseFileService)
					.ofBaseTipService(baseTipService)
					.ofBaseTitle(t)
					.ofGrabUrl(url)
					.ofUrl(url)
					.ofType(true)
					.build();
			String body = new CsdnBodyAnalysisPerformerImpl(config).analysis(doc);
			new CsdnTipAnalysisPerformerImpl(config).analysis(doc);
			String dd = HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
			System.out.println(dd);
			Long docId = csdnPageAnalysisPerformer.saveDoc(dd, title);
			ConditionsCommon con = new ConditionsCommon();
			con.addEqParam("id", t.getId());
			con.addsetObjectParam("docId", docId);
			grabSaveTitlePerformer.updateByParam(con);
		}
	}
	
	
	
	private String loadDoc(String urlPath, String cookie) {
        URL url;
        try {
            url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            if(StringUtil2.IsNotEmpty(cookie)) {
            	conn.setRequestProperty("Cookie", cookie);
            }
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	
}
