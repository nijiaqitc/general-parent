package com.njq.grab.service.impl.yhwiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.enumreg.title.TitleType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.ro.AnalysisPageRequest;
import com.njq.common.model.ro.AnalysisPageRequestBuilder;
import com.njq.common.model.ro.GrabDocSaveRequestBuilder;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.model.vo.grab.GrabUrlYhInfo;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.string.UrlUtil;
import com.njq.grab.cache.GrabMenuCacheManager;
import com.njq.grab.cache.LoginCacheManager;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;

@Component("yhWikiPageAnalysis")
public class YhWikiPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(YhWikiPageAnalysisPerformerImpl.class);
    private final BaseTitleService baseTitleService;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final LoginCacheManager loginCacheManager;
    private final GrabMenuCacheManager grabMenuCacheManager;
    private final BaseFileService baseFileService;
    private final GrabDocSaveOperation grabDocSaveOperation;
    private final GrabDocUpdateOperation grabDocUpdateOperation;

    @Autowired
    public YhWikiPageAnalysisPerformerImpl(BaseTitleService baseTitleService,
                                           SaveTitlePerformer grabSaveTitlePerformer,
                                           LoginCacheManager loginCacheManager,
                                           GrabMenuCacheManager grabMenuCacheManager,
                                           BaseFileService baseFileService,
                                           GrabDocSaveOperation grabDocSaveOperation,
                                           GrabDocUpdateOperation grabDocUpdateOperation) {
        this.baseTitleService = baseTitleService;
        this.grabSaveTitlePerformer = grabSaveTitlePerformer;
        this.loginCacheManager = loginCacheManager;
        this.grabMenuCacheManager = grabMenuCacheManager;
        this.baseFileService = baseFileService;
        this.grabDocSaveOperation = grabDocSaveOperation;
        this.grabDocUpdateOperation = grabDocUpdateOperation;
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
        GrabUrlInfo urlInfo = GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI);
        Map<String, String> param = new HashMap<>();
        param.put("os_username", urlInfo.getUserName());
        param.put("os_password", urlInfo.getPwd());
        GrabUrlYhInfo yhInfo = JSON.parseObject(urlInfo.getDataJson(), GrabUrlYhInfo.class);
        param.put("os_cookie", yhInfo.getCookieType());
        HtmlGrabUtil.build(urlInfo.getShortName()).login(urlInfo.getLoginUrl(), SendConstants.SEND_POST, param);
    }

    @Override
    public void loadMenu(String url, Long typeId) {
        loginCacheManager.checkAndLogin(ChannelType.YH_WIKI);
        this.recursionSaveTitle(null, sort(load(url)), typeId);
    }

    /**
     * 加载菜单，由于要递归所以单独抽一个方法
     *
     * @param url
     * @return
     */
    private List<LeftMenu> load(String url) {
        List<LeftMenu> menuList = grabMenuCacheManager.get(ChannelType.YH_WIKI.getValue() + GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI).getTypeName());
        if (menuList == null) {
            Document doc = HtmlGrabUtil.build(ChannelType.YH_WIKI.getValue()).getDoc(url);
            if (doc == null) {
                throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
            }
            Elements e = doc.getElementsByTag("a");
            List<LeftMenu> list = new ArrayList<>();
            e.forEach(n -> {
                LeftMenu menu = new LeftMenu();
                if (n.id().startsWith("plusminus")) {
                    menu.setType(0);
                    String uu = GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI).getPageIndex() + "/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=true&pageId=" + n.id().replace("plusminus", "").split("\\-")[0] + "&treeId=0&startDepth=0&mobile=false&treePageId=2300252&_=1537839754796";
                    menu.setMenuList(load(uu));
                } else {
                    menu.setType(1);
                    menu.setName(n.html());
                    menu.setValue(n.attr("href"));
                    menu.setDocId(UrlUtil.URLRequest(n.attr("href")).get("pageId"));
                }
                list.add(menu);
                logger.info(n.html() + "--" + n.attr("href"));
            });
            grabMenuCacheManager.update(ChannelType.YH_WIKI.getValue() + GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI).getTypeName(), list);
            return list;
        } else {
            return menuList;
        }
    }

    /**
     * 对菜单进行排序操作（原先list里面 子菜单在母菜单前面，所以要移下来）
     *
     * @param list
     */
    private List<LeftMenu> sort(List<LeftMenu> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<LeftMenu> memuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 0) {
                if (list.get(i + 1).getType() == 1) {
                    if (!CollectionUtils.isEmpty(list.get(i).getMenuList())) {
                        list.get(i + 1).setMenuList(this.sort(list.get(i).getMenuList()));
                    }
                }
            } else {
                memuList.add(list.get(i));
            }
        }
        return memuList;
    }

    @Override
    public Long grabAndSave(AnalysisPageRequest request) {
        logger.info("读取url" + request.getUrl());
        String doc = this.loginAndAnalysisPage(request);
        YhWikiPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(YhWikiPageAnalysisPerformerImpl.class);
        request.setDoc(doc);
        return impl.saveLoadingDoc(request);
    }

    @Override
    public Long grabAndReload(AnalysisPageRequest request) {
    	logger.info("重新加载url" + request.getUrl());
        String doc = this.loginAndAnalysisPage(request);
        YhWikiPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(YhWikiPageAnalysisPerformerImpl.class);
        return impl.updateDoc(doc, request.getBaseTitle().getTitle(), request.getBaseTitle().getDocId());
    }
    
    @Override
    public Long saveLoadingDoc(AnalysisPageRequest request) {
        Long docId = this.saveDoc(request.getDoc(), request.getBaseTitle().getTitle());
        baseTitleService.updateLoadSuccess(docId, request.getBaseTitle().getId());
        return docId;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        return grabDocSaveOperation.saveDoc(new GrabDocSaveRequestBuilder()
                .ofChannel(ChannelType.YH_WIKI.getValue())
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
        loginCacheManager.checkAndLogin(ChannelType.YH_WIKI);
        return this.analysisPage(request);
    }

    @Override
    public String analysisPage(AnalysisPageRequest request) {
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.YH_WIKI).getPageIndex();
        String url = request.getUrl();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = HtmlGrabUtil
                .build(ChannelType.YH_WIKI.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }
        Element et = doc.getElementById("login-container");
        if (et != null) {
            loginCacheManager.reLogin(ChannelType.YH_WIKI);
            doc = HtmlGrabUtil.build(ChannelType.YH_WIKI.getValue()).getDoc(url);
        }
        GrabConfig config = new GrabConfigBuilder()
                .ofBaseFileService(baseFileService)
                .ofBaseTitle(request.getBaseTitle())
                .ofGrabUrl(grabUrl)
                .ofUrl(url)
                .ofType(request.getType())
                .build();
        //解析body
        String body = new YhwikiBodyAnalysisPerformerImpl(config)
                .analysis(doc);
        return HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }


    /**
     * 递归保存标题
     *
     * @param parentId
     * @param menuList
     * @param typeId
     */
    public void recursionSaveTitle(Long parentId, List<LeftMenu> menuList, Long typeId) {
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        try {
            menuList.forEach(n -> {
                BaseTitle title = baseTitleService.saveTitle(new SaveTitleRequestBuilder()
                        .ofParentId(parentId)
                        .onMenu(n)
                        .ofTypeId(typeId)
                        .ofChannel(ChannelType.YH_WIKI.getValue())
                        .ofTitleType(TitleType.GRAB_TITLE)
                        .build());
                if (!CollectionUtils.isEmpty(n.getMenuList())) {
                    this.recursionSaveTitle(title.getId(), n.getMenuList(), typeId);
                }
            });
        } catch (Exception e) {
            logger.info("保存标题出错", e);
        }
    }

}
