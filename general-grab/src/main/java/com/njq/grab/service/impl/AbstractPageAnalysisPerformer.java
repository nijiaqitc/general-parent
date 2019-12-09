package com.njq.grab.service.impl;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.enumreg.title.TitleType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.ro.AnalysisPageRequest;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.common.model.ro.GrabDocSaveRequestBuilder;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
public abstract class AbstractPageAnalysisPerformer implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPageAnalysisPerformer.class);
    private final BaseTitleService baseTitleService;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final GrabDocUpdateOperation grabDocUpdateOperation;
    private final GrabDocSaveOperation grabDocSaveOperation;

    public AbstractPageAnalysisPerformer(BaseTitleService baseTitleService, SaveTitlePerformer grabSaveTitlePerformer, GrabDocUpdateOperation grabDocUpdateOperation, GrabDocSaveOperation grabDocSaveOperation) {
        this.baseTitleService = baseTitleService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
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

    public void saveTitle(LeftMenu menu, Long typeId, String tip) {
        logger.info("saveTitle:" + menu.getName() + " :----: " + menu.getValue() + "------" + tip);
        baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                .onMenu(menu)
                .ofTypeId(typeId)
                .ofChannel(ChannelType.JIANSHU.getValue())
                .ofTitleType(TitleType.GRAB_TITLE)
                .build());
    }

    @Override
    public Long grabAndSave(AnalysisPageRequest request) {
        logger.info("重新加载url" + request.getUrl());
        String doc = this.analysisPage(request);
        AbstractPageAnalysisPerformer impl = SpringContextUtil.getBean(AbstractPageAnalysisPerformer.class);
        request.setDoc(doc);
        return impl.saveLoadingDoc(request);
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
    public Long grabAndReload(AnalysisPageRequest request) {
        String doc = this.analysisPage(request);
        AbstractPageAnalysisPerformer impl = SpringContextUtil.getBean(AbstractPageAnalysisPerformer.class);
        return impl.updateDoc(doc, request.getBaseTitle().getTitle(), request.getBaseTitle().getDocId());
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
}
