package com.njq.grab.service.impl.cnblogs;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabImgPerformer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class CnblogsBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CnblogsBodyAnalysisPerformerImpl.class);
    private GrabConfig config;

    public CnblogsBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        Element enode = doc.getElementById("cnblogs_post_body");
        if (enode == null) {
            enode = doc.getElementsByTag("body").first();
        }
        if (config.getType()) {
            GrabImgPerformer.loadImg(enode, ChannelType.CNBLOGS, config);
        }
        return enode.html();
    }
}
