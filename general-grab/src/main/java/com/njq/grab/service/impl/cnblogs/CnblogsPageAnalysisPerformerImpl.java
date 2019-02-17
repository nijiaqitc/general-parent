package com.njq.grab.service.impl.cnblogs;

import java.util.Date;

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
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component("cgblogsPageAnalysis")
public class CnblogsPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(CnblogsPageAnalysisPerformerImpl.class);
    private final BaseTitleService baseTitleService;
    private final DaoCommon<GrabDoc> grabDocDao;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final BaseTipService baseTipService;
    private final BaseFileService baseFileService;

    @Autowired
    public CnblogsPageAnalysisPerformerImpl(BaseTitleService baseTitleService, DaoCommon<GrabDoc> grabDocDao,
                                            SaveTitlePerformer grabSaveTitlePerformer, BaseTipService baseTipService,
                                            BaseFileService baseFileService) {
        this.baseTitleService = baseTitleService;
        this.grabDocDao = grabDocDao;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.baseTipService = baseTipService;
        this.baseFileService = baseFileService;
    }

    @Override
    public void loadPage(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(String.valueOf(docId));
        this.grabAndSave(loading.getUrl(), grabSaveTitlePerformer.getTitleById(loading.getTitleId()));
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
                CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
                impl.saveTitle(menu, typeId, n.html().split("\\(")[0]);
            });
        });
    }

    public void saveTitle(LeftMenu menu, Long typeId, String tip) {
        logger.info("cnblogs" + menu.getName() + " :----: " + menu.getValue());
        baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                .onMenu(menu)
                .ofTypeId(typeId)
                .ofChannel(ChannelType.CNBLOGS.getValue())
                .ofTitleType(TitleType.GRAB_TITLE)
                .ofTips(baseTipService.checkAndSaveTips(tip))
                .build());
    }

    @Override
    public Long grabAndSave(String url, BaseTitle baseTitle) {
        String doc = this.analysisPage(url,baseTitle.getTypeId());
        CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
        return impl.saveLoadingDoc(doc, baseTitle);
    }

    @Override
    public Long saveLoadingDoc(String doc, BaseTitle baseTitle) {
        Long docId = this.saveDoc(doc, baseTitle.getTitle());
        baseTitleService.updateLoadSuccess(docId,
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
    public String analysisPage(String url,Long typeId) {
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.CNBLOGS).getPageIndex();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = HtmlGrabUtil
                .build(ChannelType.CNBLOGS.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
        }
        Element enode = doc.getElementById("cnblogs_post_body");
        if (enode == null) {
            enode = doc.getElementsByTag("body").first();
        }
        enode.getElementsByTag("img").forEach(n -> {
        	logger.info("读取图片:"+n.attr("src"));
            n.attr("src", baseFileService.dealImgSrc(typeId, ChannelType.CNBLOGS.getValue(), grabUrl, n.attr("src"), ChannelType.CNBLOGS.getValue(), GrabUrlInfoFactory.getImagePlace(), GrabUrlInfoFactory.getImgUrl()));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }

    @Override
    public Long updateDoc(String doc, String title, Long id) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setId(id);
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.updateByPrimaryKeySelective(grabDoc);
        return id;
    }

    @Override
    public String loginAndAnalysisPage(String url,Long typeId) {
        return this.analysisPage(url,typeId);
    }

}
