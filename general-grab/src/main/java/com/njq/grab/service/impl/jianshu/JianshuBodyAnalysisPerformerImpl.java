package com.njq.grab.service.impl.jianshu;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */

public class JianshuBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public JianshuBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        Elements enode = doc.getElementsByTag("article");
        if (enode.isEmpty()) {
            return null;
        }
        if (config.getType()) {
            enode.first().getElementsByTag("img").forEach(n -> {
                n.attr("src", config.getBaseFileService()
                        .dealImgSrc(config.getBaseTitle().getTypeId(),
                                ChannelType.JIANSHU,
                                config.getGrabUrl(),
                                n.attr("src")));
            });
        }
        return enode.first().html();
    }
}
