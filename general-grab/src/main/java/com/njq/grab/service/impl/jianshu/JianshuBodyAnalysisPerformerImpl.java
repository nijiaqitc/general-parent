package com.njq.grab.service.impl.jianshu;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;

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
            	String imgUrl="";
            	if(StringUtil.IsNotEmpty(n.attr("src"))) {
            		imgUrl = n.attr("src");
            	}else if(StringUtil.IsNotEmpty(n.attr("data-original-src"))) {
            		imgUrl = n.attr("data-original-src");
            	}
            	if(StringUtil.IsNotEmpty(imgUrl)) {
            		n.attr("src", config.getBaseFileService()
            				.dealImgSrc(config.getBaseTitle().getTypeId(),
            						ChannelType.JIANSHU,
            						config.getGrabUrl(),
            						imgUrl));
            	}
            });
        }
        return enode.first().html();
    }
}
