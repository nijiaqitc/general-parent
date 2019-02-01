package com.njq.grab.service.impl.cnblogs;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.UrlChangeUtil;
import com.njq.grab.service.PageAnalysisPerformer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("cgblogsPageAnalysis")
public class CnblogsPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private final BaseTitleService baseTitleService;
    private final DaoCommon<GrabDoc> grabDocDao;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final BaseTipService baseTipService;
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
    public CnblogsPageAnalysisPerformerImpl(BaseTitleService baseTitleService, DaoCommon<GrabDoc> grabDocDao,
                                            SaveTitlePerformer grabSaveTitlePerformer, BaseTipService baseTipService) {
        this.baseTitleService = baseTitleService;
        this.grabDocDao = grabDocDao;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.baseTipService = baseTipService;
    }

    @Override
    public void loadPage(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(String.valueOf(docId));
        CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
        impl.saveLoadingDoc(loading.getUrl(), grabSaveTitlePerformer.getTitleById(loading.getTitleId()));
    }


    @Override
    public void login() {
        return;
    }

    @Override
    public void loadMenu(String url, Long typeId) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CNBLOGS.getValue())
                .getDoc(url);
        Element element = doc.getElementById("sidebar_postcategory");
        if (element == null) {
            throw new BaseKnownException("找不到加载的标签");
        }
        CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
        impl.saveTitle(element, typeId);
    }

    public void saveTitle(Element element, Long typeId) {
        Elements elements = element.getElementsByTag("a");
        elements.forEach(n -> {
            Elements ss = HtmlGrabUtil
                    .build(ChannelType.CNBLOGS.getValue())
                    .getDoc(n.attr("href"))
                    .getElementsByClass("entrylistItemTitle");
            ss.forEach(f -> {
                LeftMenu menu = new LeftMenu();
                menu.setName(f.html());
                String href = f.attr("href");
                menu.setValue(href);
                String[] urlspl = href.split("/");
                menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                        .onMenu(menu)
                        .ofTypeId(typeId)
                        .ofChannel(ChannelType.CNBLOGS.getValue())
                        .ofTitleType(TitleType.GRAB_TITLE)
                        .ofTips(baseTipService.checkAndSaveTips(n.html().split("\\(")[0]))
                        .build());
            });
        });
    }

    @Override
    public Long saveLoadingDoc(String url, BaseTitle baseTitle) {
        String doc = this.analysisPage(url);
        Long docId = this.saveDoc(doc, baseTitle.getTitle());
        baseTitleService.updateLoadSuccess(ChannelType.CNBLOGS,
                docId,
                baseTitle.getId());
        return docId;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setChannel(ChannelType.CNBLOGS.getValue());
        grabDoc.setCreateDate(new Date());
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.save(grabDoc);
        return grabDoc.getId();
    }

    @Override
    public String analysisPage(String url) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CNBLOGS.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
        }

        Element enode = doc.getElementById("cnblogs_post_body");
        enode.getElementsByTag("img").forEach(n -> {
            n.attr("src", imgUrl + UrlChangeUtil.changeSrcUrl("", n.attr("src"), ChannelType.CNBLOGS.getValue(), imagePlace));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), decodeJsPlace, "decodeStr");
    }

    @Override
    public Long updateDoc(String url, String title, Long id) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setId(id);
        String doc = this.loginAndAnalysisPage(url);
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.updateByPrimaryKeySelective(grabDoc);
        return id;
    }

    @Override
    public String loginAndAnalysisPage(String url) {
        return this.analysisPage(url);
    }

}
