package com.njq.grab.service.impl.jianshu;

import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */

@Component("jianshuBodyAnalysisPerformer")
public class JianshuBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public JianshuBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        return null;
    }
}
