package com.njq.grab.service.impl.cnblogs;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.ro.AnalysisPageRequest;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.common.model.ro.GrabDocSaveRequestBuilder;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;

@Component("cgblogsPageAnalysis")
public class CnblogsPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CnblogsPageAnalysisPerformerImpl.class);
    private final BaseTitleService baseTitleService;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final BaseFileService baseFileService;
    private final BaseTipService baseTipService; 
    private final GrabDocSaveOperation grabDocSaveOperation;
    private final GrabDocUpdateOperation grabDocUpdateOperation;

    @Autowired
    public CnblogsPageAnalysisPerformerImpl(BaseTitleService baseTitleService,
                                            SaveTitlePerformer grabSaveTitlePerformer,
                                            BaseFileService baseFileService, GrabDocSaveOperation grabDocSaveOperation, 
                                            GrabDocUpdateOperation grabDocUpdateOperation,BaseTipService baseTipService) {
        this.baseTitleService = baseTitleService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.baseFileService = baseFileService;
        this.grabDocSaveOperation = grabDocSaveOperation;
        this.grabDocUpdateOperation = grabDocUpdateOperation;
        this.baseTipService = baseTipService;
    }

    @Override
    public void loadPage(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(String.valueOf(docId));
        this.grabAndSave(new AnalysisPageRequestBuilder()
                .ofUrl(loading.getUrl())
                .ofBaseTitle(grabSaveTitlePerformer.getTitleById(loading.getTitleId()))
                .build());
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
        Elements els = doc.getElementsByClass("catListPostArchive");
        if (!els.isEmpty()) {
            loadMenu(els.get(0), typeId);
        } else {
            Element element = doc.getElementById("sidebar_postcategory");
            if (element == null) {
                throw new BaseKnownException("找不到加载的标签");
            }
            loadMenu(element, typeId);
        }
    }

    private void loadMenu(Element element, Long typeId) {
        Elements elements = element.getElementsByTag("a");
        elements.parallelStream().forEach(n -> {
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
                CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
                impl.saveTitle(menu, typeId);
            });
        });
    }

    public void saveTitle(LeftMenu menu, Long typeId) {
        logger.info("cnblogs" + menu.getName() + " :----: " + menu.getValue());
        baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                .onMenu(menu)
                .ofTypeId(typeId)
                .ofChannel(ChannelType.CNBLOGS.getValue())
                .ofTitleType(TitleType.GRAB_TITLE)
                .build());
    }

    @Override
    public Long grabAndSave(AnalysisPageRequest request) {
        String doc = this.analysisPage(request);
        CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
        request.setDoc(doc);
        return impl.saveLoadingDoc(request);
    }

    @Override
    public Long saveLoadingDoc(AnalysisPageRequest request) {
        Long docId = this.saveDoc(request.getDoc(), request.getBaseTitle().getTitle());
        baseTitleService.updateLoadSuccess(docId,
                request.getBaseTitle().getId());
        return docId;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        return grabDocSaveOperation.saveDoc(new GrabDocSaveRequestBuilder()
                .ofChannel(ChannelType.CNBLOGS.getValue())
                .ofDoc(doc)
                .ofTitle(title)
                .build())
                .getId();
    }

    @Override
    public String analysisPage(AnalysisPageRequest request) {
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.CNBLOGS).getPageIndex();
        String url = request.getUrl();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = HtmlGrabUtil
                .build(ChannelType.CNBLOGS.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }
        GrabConfig config = new GrabConfigBuilder()
        		.ofBaseTipService(baseTipService)
                .ofBaseFileService(baseFileService)
                .ofBaseTitle(request.getBaseTitle())
                .ofGrabUrl(grabUrl)
                .ofUrl(url)
                .ofType(request.getType())
                .build();
        String body = new CnblogsBodyAnalysisPerformerImpl(config).analysis(doc);
        if (request.getType()) {
            new CnblogsTipAnalysisPerformerImpl(config).analysis(doc);
        }
        return HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }

    @Override
    public Long updateDoc(String doc, String title, Long id) {
        return grabDocUpdateOperation.updateDoc(new GrabDocSaveRequestBuilder()
                .ofTitle(title)
                .ofDoc(doc)
                .ofId(id)
                .build())
                .getId();
    }

    @Override
    public String loginAndAnalysisPage(AnalysisPageRequest request) {
        return this.analysisPage(request);
    }

}
