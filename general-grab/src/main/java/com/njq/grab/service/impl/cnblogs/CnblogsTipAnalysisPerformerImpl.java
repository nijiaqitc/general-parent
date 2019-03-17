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
        	Pattern pattern =Pattern.compile("currentBlogId=.*?;");
        	List<String> stlist = tages.stream().map(n->{        		
        		Matcher mt =  pattern.matcher(n.html());
        		if(mt.find()) {
        			return mt.group();
        		}
        		return null;        		
        	}).filter(n->n!=null).collect(Collectors.toList());
        	if(!CollectionUtils.isEmpty(stlist)) {
        		String st = stlist.get(0);
        		String blogId = st.split("=")[1].split(";")[0];
        		String[] sarray = StringUtil.urlsplit(config.getUrl());
        		String pstr = "https://www.cnblogs.com/mvc/blog/CategoriesTags.aspx?blogApp="+sarray[3]+"&postId="+sarray[5].split("\\.")[0]+"&blogId="+blogId;
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
