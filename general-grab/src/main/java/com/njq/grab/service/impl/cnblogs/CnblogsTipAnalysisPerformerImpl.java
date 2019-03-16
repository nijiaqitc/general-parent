package com.njq.grab.service.impl.cnblogs;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

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
        		System.out.println(st.split("=")[1].split(";")[0]);
        	}
        }
        return null;
    }
}
