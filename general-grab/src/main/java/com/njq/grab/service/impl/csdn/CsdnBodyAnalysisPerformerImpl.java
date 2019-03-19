package com.njq.grab.service.impl.csdn;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;

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
        if(config.getType()) {
        	enode.getElementsByTag("img").forEach(n -> {
        		n.attr("src", config.getBaseFileService()
        				.dealImgSrc(config.getBaseTitle().getTypeId(),
        						ChannelType.CSDN,
        						config.getGrabUrl(),
        						n.attr("src")));
        	});
        }
        return enode.html();
    }


}
