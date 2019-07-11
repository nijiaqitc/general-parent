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
    private final CnblogsMenuLoadPerformer cnblogsLoadOnePerformer;
    private final CnblogsMenuLoadPerformer cnblogsLoadTwoPerformer;
    
    
    
    @Autowired
    public CnblogsPageAnalysisPerformerImpl(BaseTitleService baseTitleService,
                                            SaveTitlePerformer grabSaveTitlePerformer,
                                            BaseFileService baseFileService, GrabDocSaveOperation grabDocSaveOperation, 
                                            GrabDocUpdateOperation grabDocUpdateOperation,BaseTipService baseTipService,
                                            CnblogsMenuLoadPerformer cnblogsLoadOnePerformer,CnblogsMenuLoadPerformer cnblogsLoadTwoPerformer) {
        this.baseTitleService = baseTitleService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.baseFileService = baseFileService;
        this.grabDocSaveOperation = grabDocSaveOperation;
        this.grabDocUpdateOperation = grabDocUpdateOperation;
        this.baseTipService = baseTipService;
        this.cnblogsLoadOnePerformer = cnblogsLoadOnePerformer;
        this.cnblogsLoadTwoPerformer = cnblogsLoadTwoPerformer;
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
        	cnblogsLoadOnePerformer.loadMenu(els.get(0), typeId);
        } else {
        	//第一版的菜单加载
            Element element = doc.getElementById("sidebar_postcategory");
            if (element != null) {
            	cnblogsLoadOnePerformer.loadMenu(element, typeId);
            }
            //第二版的菜单加载
            Element et = doc.getElementById("sidebar_categories");
            if(et != null) {
            	cnblogsLoadTwoPerformer.loadMenu(et, typeId);
            }else {
            	logger.info("cnblogs加载找不到id sidebar_categories catListPostArchive ："+doc.html());
            	throw new BaseKnownException("找不到加载的标签");
            }
        }
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
    public Long grabAndReload(AnalysisPageRequest request) {
    	logger.info("重新加载url" + request.getUrl());
        String doc = this.analysisPage(request);
        CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
        return impl.updateDoc(doc, request.getBaseTitle().getTitle(), request.getBaseTitle().getDocId());
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
