package com.njq.grab.service.impl.csdn;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.enumreg.title.TitleType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.ro.AnalysisPageRequest;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.common.model.ro.GrabDocSaveRequestBuilder;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.CommonTipAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/2/20
 */
@Component("csdnPageAnalysisPerformer")
public class CsdnPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CsdnPageAnalysisPerformerImpl.class);
    private final BaseTitleService baseTitleService;
    private final BaseTipService baseTipService;
    private final BaseFileService baseFileService;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final GrabDocSaveOperation grabDocSaveOperation;
    private final GrabDocUpdateOperation grabDocUpdateOperation;
    private final CsdnPreHandler csdnPreHandler;

    @Autowired
    public CsdnPageAnalysisPerformerImpl(BaseTitleService baseTitleService, BaseTipService baseTipService,
                                         BaseFileService baseFileService, SaveTitlePerformer grabSaveTitlePerformer,
                                         GrabDocSaveOperation grabDocSaveOperation, GrabDocUpdateOperation grabDocUpdateOperation, CsdnPreHandler csdnPreHandler) {
        this.baseTitleService = baseTitleService;
        this.baseTipService = baseTipService;
        this.baseFileService = baseFileService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.grabDocSaveOperation = grabDocSaveOperation;
        this.grabDocUpdateOperation = grabDocUpdateOperation;
        this.csdnPreHandler = csdnPreHandler;
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
        logger.info("待加载的url:" + url);
        Document doc = csdnPreHandler.preLoad(url);
        Element element = doc.getElementById("asideArchive");
        if (element == null) {
            element = doc.getElementById("asideCategory");
            if (element != null) {
                loadMenuByType(element, typeId);
                return;
            }
            logger.info("未读取到id:asideArchive");
            logger.info("读取到的元素:" + doc.html());
            return;
        }
        Elements elements = element.getElementsByTag("ul").get(0).getElementsByTag("a");
        if (elements.isEmpty()) {
            return;
        }
        elements.parallelStream().forEach(n -> {
            Elements spanElements = n.getElementsByTag("span");
            int num = Integer.parseInt(spanElements.get(0).html().split("篇")[0]);
            int pageNum = num % 20 == 0 ? num / 20 : (num / 20 + 1);
            for (int i = 1; i <= pageNum; i++) {
                Elements ss = csdnPreHandler.preLoad(n.attr("href") + "/" + i).getElementsByClass("article-item-box");
                ss.forEach(m -> {
                    if (m.outerHtml().contains("display: none")) {
                        return;
                    }
                    Element a = m.getElementsByTag("h4").get(0).getElementsByTag("a").get(0);
                    LeftMenu menu = new LeftMenu();
                    Elements sElements = a.getElementsByClass("article-type");
                    if (!sElements.isEmpty()) {
                        menu.setName(a.html().replace(sElements.get(0).outerHtml(), "").trim());
                    }
                    String href = a.attr("href");
                    menu.setValue(href);
                    String[] urlspl = href.split("/");
                    menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                    try {
                        this.saveTitle(menu, typeId, null);
                    } catch (Exception e) {
                        logger.error("保存标题出错" + e.getMessage(), e);
                    }
                });
            }
        });
    }

    private void loadMenuByType(Element element, Long typeId) {
        Elements ets = element.getElementsByTag("a");
        ets.parallelStream().forEach(n -> {
            Elements ss = csdnPreHandler.preLoad(n.attr("href")).getElementsByClass("column_article_title");
            ss.forEach(m -> {
                LeftMenu menu = new LeftMenu();
                Elements titles = m.getElementsByClass("title");
                if (!titles.isEmpty()) {
                    menu.setName(titles.get(0).html());
                }
                String href = m.parent().attr("href");
                menu.setValue(href);
                String[] urlspl = href.split("/");
                menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                try {
                    this.saveTitle(menu, typeId, null);
                } catch (Exception e) {
                    logger.error("保存标题出错" + e.getMessage(), e);
                }
            });
        });
    }

    public void saveTitle(LeftMenu menu, Long typeId, String tip) {
        logger.info("csdn:" + menu.getName() + " :----: " + menu.getValue() + "------" + tip);
        baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                .onMenu(menu)
                .ofTypeId(typeId)
                .ofChannel(ChannelType.CSDN.getValue())
                .ofTitleType(TitleType.GRAB_TITLE)
                .ofTips(baseTipService.checkAndSaveTips(tip))
                .build());
    }


    @Override
    public Long grabAndSave(AnalysisPageRequest request) {
        String doc = this.analysisPage(request);
        CsdnPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CsdnPageAnalysisPerformerImpl.class);
        request.setDoc(doc);
        return impl.saveLoadingDoc(request);
    }

    @Override
    public Long grabAndReload(AnalysisPageRequest request) {
        logger.info("重新加载url" + request.getUrl());
        String doc = this.analysisPage(request);
        CsdnPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CsdnPageAnalysisPerformerImpl.class);
        return impl.updateDoc(doc, request.getBaseTitle().getTitle(), request.getBaseTitle().getDocId());
    }

    @Override
    public Long saveLoadingDoc(AnalysisPageRequest request) {
        if (StringUtil2.isEmpty(request.getDoc())) {
            throw new BaseKnownException("空白页面，不要:" + request.getUrl());
        }
        Long docId = this.saveDoc(request.getDoc(), request.getBaseTitle().getTitle());
        baseTitleService.updateLoadSuccess(docId,
                request.getBaseTitle().getId());
        return docId;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        return grabDocSaveOperation.saveDoc(new GrabDocSaveRequestBuilder()
                .ofChannel(ChannelType.CSDN.getValue())
                .ofDoc(doc)
                .ofTitle(title)
                .build())
                .getId();
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

    @Override
    public String analysisPage(AnalysisPageRequest request) {
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.CSDN).getPageIndex();
        String url = request.getUrl();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = csdnPreHandler.preLoad(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }

        GrabConfig config = new GrabConfigBuilder()
                .ofBaseFileService(baseFileService)
                .ofBaseTipService(baseTipService)
                .ofBaseTitle(request.getBaseTitle())
                .ofGrabUrl(grabUrl)
                .ofUrl(url)
                .ofType(request.getType())
                .build();
        String body = new CsdnBodyAnalysisPerformerImpl(config).analysis(doc);
        if (request.getType()) {
            new CommonTipAnalysisPerformer(config).analysis(doc, "tags-box");
        }

        return HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }


}
