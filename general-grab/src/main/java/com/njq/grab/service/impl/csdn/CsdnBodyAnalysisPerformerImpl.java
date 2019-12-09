package com.njq.grab.service.impl.csdn;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabImgPerformer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class CsdnBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public CsdnBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        Element enode = doc.getElementById("content_views");
        if (enode == null) {
            enode = doc.getElementsByTag("body").first();
        }
        if (config.getType()) {
            GrabImgPerformer.loadImg(enode, ChannelType.CSDN, config);
        }
        return enode.html();
    }


}
