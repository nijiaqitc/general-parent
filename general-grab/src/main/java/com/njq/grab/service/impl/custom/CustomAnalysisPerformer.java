package com.njq.grab.service.impl.custom;

import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.UrlChangeUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CustomAnalysisPerformer.class);
    @Value("${image.url}")
    private String imgUrl;
    @Value("${image.place}")
    private String imagePlace;
    @Value("${file.url}")
    private String docUrl;
    @Value("${file.place}")
    private String docPlace;
    @Value("${decode.js.place}")
    private String decodeJsPlace;
    @Autowired
    private BaseTitleService baseTitleService;
    @Autowired
    private DaoCommon<GrabDoc> grabDocDao;

    public Long saveLoadingDoc(String url, String name, int type, BaseTitle baseTitle) {
        String doc = this.analysisPage(url, name, type);
        Long docId = this.saveDoc(doc, baseTitle.getTitle(), baseTitle);
        baseTitleService.updateLoadSuccess(ChannelType.CUSTOM,
                docId,
                baseTitle.getId());
        return docId;
    }

    public String analysisPage(String url, String name, int type) {
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
            throw new BaseKnownException("无法获取节点元素" + name);
        }
        String uriStr = url.split("\\/")[2];
        if (url.startsWith("https")) {
            uriStr = "https://" + uriStr;
        } else {
            uriStr = "http://" + uriStr;
        }
        String uri = uriStr;
        enode.getElementsByTag("img").forEach(n -> {
            n.attr("src", imgUrl + UrlChangeUtil.changeSrcUrl(uri, n.attr("src"), ChannelType.CUSTOM.getValue(), imagePlace));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), decodeJsPlace, "decodeStr");
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
