package com.njq.grab.service.impl.yhwiki;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.ro.BaseFileDealRequestBuilder;
import com.njq.common.util.string.StringUtil;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public class YhwikiBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public YhwikiBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }

    @Override
    public String analysis(Document doc) {
        Element enode = doc.getElementById("main-content");
        /**
         * 重新登录还是登录不进去则停止读取，
         * 下次再读取
         */
        if (enode == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + config.getUrl());
        }
        if(config.getType()) {
        	enode.getElementsByTag("a").forEach(n -> {
        		if (n.attr("href").startsWith(config.getGrabUrl()) || (!n.attr("href").startsWith("http"))) {
        			if (n.attr("href").startsWith("/download")) {
        				n.attr("href",
        						config.getBaseFileService().dealFileUrl(new BaseFileDealRequestBuilder()
        								.ofTypeId(config.getBaseTitle().getTypeId())
        								.ofChannel(ChannelType.YH_WIKI.getValue())
        								.ofPrefix(config.getGrabUrl())
        								.ofSrc(n.attr("href"))
        								.ofShortName(ChannelType.YH_WIKI.getValue())
        								.ofSavePlace(GrabUrlInfoFactory.getDocPlace())
        								.ofImgPlace(GrabUrlInfoFactory.getImgUrl())
        								.build()));
        			} else {
        				n.attr("href", "javascript:void(0)");
        			}
        		}
        	});
        	enode.getElementsByTag("img").forEach(n -> {
        		String imgUrl="";
            	if(StringUtil.IsNotEmpty(n.attr("src"))) {
            		imgUrl = n.attr("src");
            	}else if(StringUtil.IsNotEmpty(n.attr("data-original-src"))) {
            		imgUrl = n.attr("data-original-src");
            	}
            	if(StringUtil.IsNotEmpty(imgUrl)) {
            		n.attr("src", config.getBaseFileService().dealImgSrc(config.getBaseTitle().getTypeId(),
            				ChannelType.YH_WIKI,
            				config.getGrabUrl(),
            				imgUrl));            		
            	}
        	});
        }
        return enode.html();
    }
}
