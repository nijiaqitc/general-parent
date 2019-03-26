package com.njq.grab.service.impl.custom;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.ro.GrabDocSaveRequestBuilder;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;

@Component
public class CustomAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CustomAnalysisPerformer.class);
    @Autowired
    private BaseTitleService baseTitleService;
    @Autowired
    private BaseFileService baseFileService;
    @Autowired
    private GrabDocSaveOperation grabDocSaveOperation;
    @Autowired
    private GrabDocUpdateOperation grabDocUpdateOperation;
    
    public Long grabAndSave(String url, String name, int type, BaseTitle baseTitle) {
        String doc = this.analysisPage(url, name, type, baseTitle.getTypeId());
        CustomAnalysisPerformer impl = SpringContextUtil.getBean(CustomAnalysisPerformer.class);
        return impl.saveLoadingDoc(doc, baseTitle);
    }

    public Long grabAndReload(String url, String name, int type, BaseTitle baseTitle) {
    	logger.info("重新加载url" + url);
        String doc = this.analysisPage(url, name, type, baseTitle.getTypeId());
        CustomAnalysisPerformer impl = SpringContextUtil.getBean(CustomAnalysisPerformer.class);
        return impl.updateDoc(doc, baseTitle.getTitle(), baseTitle.getId());
    }
    
    public Long saveLoadingDoc(String doc, BaseTitle baseTitle) {
        Long docId = this.saveDoc(doc, baseTitle);
        baseTitleService.updateLoadSuccess(docId,
                baseTitle.getId());
        return docId;
    }

    public String analysisPage(String url, String name, int type, Long typeId) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(url);
        logger.info("cusotm:" + name + " :----: " + url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }
        Element enode = null;
        switch (type) {
            case 1:
                enode = doc.getElementById(name);
                break;
            case 2:
                Elements es = doc.getElementsByTag(name);
                if (es != null) {
                    enode = es.get(0);
                }
                break;
            case 3:
                Elements es1 = doc.getElementsByClass(name);
                if (es1 != null) {
                    enode = es1.get(0);
                }
                break;
            default:
                break;
        }
        if (enode == null) {
            enode = doc.getElementsByTag("body").first();
        }
        String uriStr = url.split("\\/")[2];
        if (url.startsWith("https")) {
            uriStr = "https://" + uriStr;
        } else {
            uriStr = "http://" + uriStr;
        }
        String uri = uriStr;
        enode.getElementsByTag("img").forEach(n -> {
            n.attr("src", baseFileService.dealImgSrc(typeId, ChannelType.CUSTOM, uri, n.attr("src")));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }

    public Long saveDoc(String doc, BaseTitle baseTitle) {
        return grabDocSaveOperation.saveDoc(new GrabDocSaveRequestBuilder()
                .ofChannel(baseTitle.getChannel())
                .ofDoc(doc)
                .ofTitle(baseTitle.getTitle())
                .build())
                .getId();
    }
    
    public Long updateDoc(String doc, String title, Long id) {
        return grabDocUpdateOperation.updateDoc(new GrabDocSaveRequestBuilder()
                .ofTitle(title)
                .ofDoc(doc)
                .ofId(id)
                .build())
                .getId();
    }
}
