package com.njq.grab.service.impl.cnblogs;

import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


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
//        tages.get(1).html().matches()
        return null;
    }
}
