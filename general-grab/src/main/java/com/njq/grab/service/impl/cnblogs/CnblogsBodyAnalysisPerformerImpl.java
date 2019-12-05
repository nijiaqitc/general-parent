package com.njq.grab.service.impl.cnblogs;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;

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
        if(config.getType()) {
        	enode.getElementsByTag("img").parallelStream().forEach(n -> {
        		String imgUrl="";
            	if(StringUtil.IsNotEmpty(n.attr("src"))) {
            		imgUrl = n.attr("src");
            	}else if(StringUtil.IsNotEmpty(n.attr("data-original-src"))) {
            		imgUrl = n.attr("data-original-src");
            	}
            	if(StringUtil.IsNotEmpty(imgUrl)) {
            		logger.info("读取图片:" + n.attr("src"));
            		n.attr("src", config.getBaseFileService().dealImgSrc(
            				config.getBaseTitle().getTypeId(),
            				ChannelType.CNBLOGS,
            				config.getGrabUrl(),
            				imgUrl));
            	}
        	});        	
        }
        return enode.html();
    }
}
