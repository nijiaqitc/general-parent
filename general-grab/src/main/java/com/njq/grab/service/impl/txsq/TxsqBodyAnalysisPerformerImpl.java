package com.njq.grab.service.impl.txsq;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.HtmlAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabImgPerformer;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public class TxsqBodyAnalysisPerformerImpl implements HtmlAnalysisPerformer {
    private GrabConfig config;

    public TxsqBodyAnalysisPerformerImpl(GrabConfig config) {
        this.config = config;
    }


    @Override
    public String analysis(Document doc) {
        Elements est = doc.getElementsByClass("J-articleContent");
        if (est.isEmpty()) {
            est = doc.getElementsByTag("body");
        }
        if (config.getType()) {
            GrabImgPerformer.loadImg(est.first(), ChannelType.TXSQ, config);
        }
        return est.first().html();
    }
}
