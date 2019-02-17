package com.njq.grab.service.impl.custom;

import java.util.Date;

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
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component
public class CustomAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CustomAnalysisPerformer.class);
    @Autowired
    private BaseTitleService baseTitleService;
    @Autowired
    private DaoCommon<GrabDoc> grabDocDao;
    @Autowired
    private BaseFileService baseFileService;
    public Long grabAndSave(String url, String name, int type, BaseTitle baseTitle) {
        String doc = this.analysisPage(url, name, type,baseTitle.getTypeId());
        CustomAnalysisPerformer impl = SpringContextUtil.getBean(CustomAnalysisPerformer.class);
        return impl.saveLoadingDoc(doc, name, type, baseTitle);
    }

    public Long saveLoadingDoc(String doc, String name, int type, BaseTitle baseTitle) {
        Long docId = this.saveDoc(doc, baseTitle.getTitle(), baseTitle);
        baseTitleService.updateLoadSuccess(docId,
                baseTitle.getId());
        return docId;
    }

    public String analysisPage(String url, String name, int type,Long typeId) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(url);
        logger.info("cusotm:" + name + " :----: " + url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
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
            n.attr("src", baseFileService.dealImgSrc(typeId, ChannelType.CUSTOM.getValue(), uri, n.attr("src"), ChannelType.CUSTOM.getValue(), GrabUrlInfoFactory.getImagePlace(), GrabUrlInfoFactory.getImgUrl()));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }

    public Long saveDoc(String doc, String title, BaseTitle baseTitle) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setChannel(baseTitle.getChannel());
        grabDoc.setCreateDate(new Date());
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.save(grabDoc);
        return grabDoc.getId();
    }
}
