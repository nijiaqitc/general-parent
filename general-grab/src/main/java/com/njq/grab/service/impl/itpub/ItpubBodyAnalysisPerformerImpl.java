package com.njq.grab.service.impl.itpub;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabImgPerformer;

public class ItpubBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer{
	private GrabConfig config;

    public ItpubBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

	@Override
	public String analysis(Document doc) {
		 Elements est = doc.getElementsByClass("preview-main");
	        if (est.isEmpty()) {
	        	est = doc.getElementsByClass("preview-body");
	        	if(est.isEmpty()) {
	        		est = doc.getElementsByTag("body");	        		
	        	}
	        }
	        if (config.getType()) {
	        	GrabImgPerformer.resetImg(est.first());
	            GrabImgPerformer.loadImg(est.first(), ChannelType.ITPUB, config);
	        }
	        return est.first().html();
	}

	
}
