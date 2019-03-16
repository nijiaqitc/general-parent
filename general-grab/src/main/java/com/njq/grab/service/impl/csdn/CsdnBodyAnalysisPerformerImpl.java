package com.njq.grab.service.impl.csdn;

import com.njq.common.base.constants.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
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
        enode.getElementsByTag("img").forEach(n -> {
            if (!n.attr("src").startsWith("http")) {
                n.attr("src",
                        config.getBaseFileService()
                                .dealImgSrc(config.getBaseTitle().getTypeId(),
                                        ChannelType.CSDN.getValue(),
                                        config.getGrabUrl(),
                                        n.attr("src"),
                                        ChannelType.CSDN.getValue(),
                                        GrabUrlInfoFactory.getImagePlace(),
                                        GrabUrlInfoFactory.getImgUrl()));
            }
        });
        return enode.html();
    }


}
