package com.njq.grab.service.impl.csdn;

import com.njq.common.base.constants.TitleType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class CsdnTipAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public CsdnTipAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }


    @Override
    public String analysis(Document doc) {
        Elements elements = doc.getElementsByClass("tags-box");
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
        if (!CollectionUtils.isEmpty(tagSet)) {
            String[] array = new String[tagSet.size()];
            config.getBaseTipService().addNum(config.getBaseTipService()
                                    .checkAndSaveTips(tagSet.toArray(array)),
                            config.getBaseTitle().getId(),
                            TitleType.GRAB_TITLE);
        }
        return null;
    }
}
