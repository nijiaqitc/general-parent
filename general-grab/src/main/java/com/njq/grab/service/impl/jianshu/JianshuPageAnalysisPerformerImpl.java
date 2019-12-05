package com.njq.grab.service.impl.jianshu;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
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
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
@Component("jianshuPageAnalysisPerformer")
public class JianshuPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(JianshuPageAnalysisPerformerImpl.class);
    private final JianshuPreHandler jianshuPreHandler;
    private final BaseTitleService baseTitleService;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final BaseFileService baseFileService;
    private final GrabDocUpdateOperation grabDocUpdateOperation;
    private final GrabDocSaveOperation grabDocSaveOperation;

    @Autowired
    public JianshuPageAnalysisPerformerImpl(JianshuPreHandler jianshuPreHandler, BaseTitleService baseTitleService, SaveTitlePerformer grabSaveTitlePerformer, BaseFileService baseFileService, GrabDocUpdateOperation grabDocUpdateOperation, GrabDocSaveOperation grabDocSaveOperation) {
        this.jianshuPreHandler = jianshuPreHandler;
        this.baseTitleService = baseTitleService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.baseFileService = baseFileService;
        this.grabDocUpdateOperation = grabDocUpdateOperation;
        this.grabDocSaveOperation = grabDocSaveOperation;
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
        List<LeftMenu> list = jianshuPreHandler.loadAllMenu(url);
        if (list == null) {
            return;
        }
        list.parallelStream().forEach(n -> {
            this.saveTitle(n, typeId, null);
        });
    }

    public void saveTitle(LeftMenu menu, Long typeId, String tip) {
        logger.info("jianshu:" + menu.getName() + " :----: " + menu.getValue() + "------" + tip);
        baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                .onMenu(menu)
                .ofTypeId(typeId)
                .ofChannel(ChannelType.JIANSHU.getValue())
                .ofTitleType(TitleType.GRAB_TITLE)
                .build());
    }

    @Override
    public Long grabAndSave(AnalysisPageRequest request) {
        String doc = this.analysisPage(request);
        JianshuPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(JianshuPageAnalysisPerformerImpl.class);
        request.setDoc(doc);
        return impl.saveLoadingDoc(request);
    }

    @Override
    public Long grabAndReload(AnalysisPageRequest request) {
        logger.info("重新加载url" + request.getUrl());
        String doc = this.analysisPage(request);
        JianshuPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(JianshuPageAnalysisPerformerImpl.class);
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
                .ofChannel(ChannelType.JIANSHU.getValue())
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
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.JIANSHU).getPageIndex();
        String url = request.getUrl();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = HtmlGrabUtil
                .build(ChannelType.JIANSHU.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }
        GrabConfig config = new GrabConfigBuilder()
                .ofBaseFileService(baseFileService)
                .ofBaseTitle(request.getBaseTitle())
                .ofGrabUrl(grabUrl)
                .ofUrl(url)
                .ofType(request.getType())
                .build();
        String body = new JianshuBodyAnalysisPerformerImpl(config).analysis(doc);
        return HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }

}
