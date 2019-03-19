package com.njq.grab.service.impl.cnblogs;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;


/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class CnblogsTipAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;
    public CnblogsTipAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        Elements tages = doc.getElementsByTag("script");
        if(!tages.isEmpty()) {
        	String curId = null;
        	String enId = null;
        	for(int i=0;i<tages.size();i++) {
        		Pattern pt1 =Pattern.compile("currentBlogId=.*?;");
        		Matcher mt1 =  pt1.matcher(tages.get(i).html());
        		if(mt1.find()) {
        			curId = mt1.group();
        		}
        		Pattern pt2 =Pattern.compile("cb_entryId=.*?,");
        		Matcher mt2 =  pt2.matcher(tages.get(i).html());
        		if(mt2.find()) {
        			enId = mt2.group();
        		}
        	}
        	if(curId!=null&&enId!=null) {
        		String blogId = curId.split("=")[1].split(";")[0];
        		String postId = enId.split("=")[1].split(",")[0];
        		String[] sarray = StringUtil.urlsplit(config.getUrl());
        		String pstr = "https://www.cnblogs.com/mvc/blog/CategoriesTags.aspx?blogApp="+sarray[3]+"&postId="+postId+"&blogId="+blogId;
        		System.out.println(pstr);
		        Document tipDoc = HtmlGrabUtil
		                .build(ChannelType.CNBLOGS.getValue())
		                .getDoc(pstr);
		        JSONObject jsonObject  = JSON.parseObject(tipDoc.body().html());
		        String sptip=jsonObject.getString("Tags");
		        if(!StringUtil.isEmpty(sptip)) {
		        	String tips=sptip.split(">")[1].split("<")[0];
		        	if(config.getType()) {
		        		config.getBaseTipService().addNum(config.getBaseTipService()
		        				.checkAndSaveTips(tips.split("、")),config.getBaseTitle().getId(),TitleType.GRAB_TITLE);
		        	}
		        }
        	}
        }
        return null;
    }
}
