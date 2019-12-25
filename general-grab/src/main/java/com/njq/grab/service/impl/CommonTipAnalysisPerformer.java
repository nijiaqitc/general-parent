package com.njq.grab.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import com.njq.common.enumreg.title.TitleType;
import com.njq.common.util.string.StringUtil2;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class CommonTipAnalysisPerformer {
    private GrabConfig config;

    public CommonTipAnalysisPerformer(GrabConfig config) {
        this.config = config;
    }

    public void analysis(Document doc, String tagName) {
        Elements elements = doc.getElementsByClass(tagName);
        Set<String> tagSet = new HashSet<String>();
        if (!elements.isEmpty()) {
            elements.forEach(n -> {
                Elements as = n.getElementsByTag("a");
                if (!as.isEmpty()) {
                    as.forEach(m -> {
                        tagSet.add(m.html());
                    });
                }
            });
        }
        if (config.getType()) {
            if (!CollectionUtils.isEmpty(tagSet)) {
                String[] array = new String[tagSet.size()];
                config.getBaseTipService().addNum(config.getBaseTipService()
                                .checkAndSaveTips(tagSet.toArray(array)),
                        config.getBaseTitle().getId(),
                        TitleType.GRAB_TITLE);
            }
        }
    }
    
    
    public void analysis(String tagName) {
    	if(StringUtil2.isEmpty(tagName)) {
    		return;
    	}
    	config.getBaseTipService().addNum(config.getBaseTipService()
                .checkAndSaveTips(tagName),
        config.getBaseTitle().getId(),
        TitleType.GRAB_TITLE);
    }
    
    
}
