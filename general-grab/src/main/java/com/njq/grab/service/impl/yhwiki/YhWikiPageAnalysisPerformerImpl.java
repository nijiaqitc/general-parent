package com.njq.grab.service.impl.yhwiki;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.model.vo.grab.GrabUrlYhInfo;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.grab.UrlChangeUtil;
import com.njq.common.util.string.UrlUtil;
import com.njq.grab.cache.GrabMenuCacheManager;
import com.njq.grab.cache.LoginCacheManager;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabUrlInfoService;

@Component("yhWikiPageAnalysis")
public class YhWikiPageAnalysisPerformerImpl implements PageAnalysisPerformer {
    private static final Logger logger = LoggerFactory.getLogger(YhWikiPageAnalysisPerformerImpl.class);
    private final BaseTitleService baseTitleService;
    private final DaoCommon<GrabDoc> grabDocDao;
    private final SaveTitlePerformer grabSaveTitlePerformer;
    private final LoginCacheManager loginCacheManager;
    private final GrabUrlInfoService grabUrlInfoService;
    private final GrabMenuCacheManager grabMenuCacheManager;
    private static GrabUrlInfo urlInfo;
    private static String grabUrl;
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
    public YhWikiPageAnalysisPerformerImpl(BaseTitleService baseTitleService,DaoCommon<GrabDoc> grabDocDao,
    		SaveTitlePerformer grabSaveTitlePerformer,LoginCacheManager loginCacheManager,
    		GrabUrlInfoService grabUrlInfoService,GrabMenuCacheManager grabMenuCacheManager) {
    	this.baseTitleService=baseTitleService;
    	this.grabDocDao=grabDocDao;
    	this.grabSaveTitlePerformer=grabSaveTitlePerformer;
    	this.loginCacheManager=loginCacheManager;
    	this.grabUrlInfoService=grabUrlInfoService;
    	this.grabMenuCacheManager=grabMenuCacheManager;
    	//加载下载信息
		urlInfo = grabUrlInfoService.getUrlInfoByChannel(ChannelType.YH_WIKI.getValue());
		grabUrl = urlInfo.getPageIndex();
    }
    
    @Override
    public void loadPage(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(String.valueOf(docId));
        this.saveLoadingDoc(loading.getUrl(), grabSaveTitlePerformer.getTitleById(loading.getTitleId()));
    }

    @Override
    public void login() {
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
        this.saveTitle(null, sort(load(url)), typeId);
    }

    /**
     * 加载菜单，由于要递归所以单独抽一个方法
     *
     * @param url
     * @return
     */
    private List<LeftMenu> load(String url) {
        List<LeftMenu> menuList = grabMenuCacheManager.get(ChannelType.YH_WIKI.getValue() + urlInfo.getTypeName());
        if (menuList == null) {
            Document doc = HtmlGrabUtil.build(ChannelType.YH_WIKI.getValue()).getDoc(url);
            if (doc == null) {
                throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
            }
            Elements e = doc.getElementsByTag("a");
            List<LeftMenu> list = new ArrayList<>();
            e.forEach(n -> {
                LeftMenu menu = new LeftMenu();
                if (n.id().startsWith("plusminus")) {
                    menu.setType(0);
                    String uu = grabUrl + "/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=true&pageId=" + n.id().replace("plusminus", "").split("\\-")[0] + "&treeId=0&startDepth=0&mobile=false&treePageId=2300252&_=1537839754796";
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
            grabMenuCacheManager.update(ChannelType.YH_WIKI.getValue() + urlInfo.getTypeName(), list);
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
    public Long saveLoadingDoc(String url, BaseTitle baseTitle) {
        logger.info("读取url"+url);
        String doc = this.loginAndAnalysisPage(url.startsWith("http") ? url : grabUrl + url);
        Long docId = this.saveDoc(doc, baseTitle.getTitle());
        baseTitleService.updateLoadSuccess(ChannelType.YH_WIKI,
        		docId,
                baseTitle.getId());
        return docId;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setChannel(ChannelType.YH_WIKI.getValue());
        grabDoc.setCreateDate(new Date());
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.save(grabDoc);
        return grabDoc.getId();
    }

    @Override
    public Long updateDoc(String url, String title, Long id) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setId(id);
        String doc = this.loginAndAnalysisPage(url.startsWith("http") ? url : grabUrl + url);
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.updateByPrimaryKeySelective(grabDoc);
        return id;
    }

    @Override
    public String loginAndAnalysisPage(String url) {
        loginCacheManager.checkAndLogin(ChannelType.YH_WIKI);
        return this.analysisPage(url);
    }

    @Override
    public String analysisPage(String url) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.YH_WIKI.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
        }
        if ("Log In - Confluence".equals(doc.getElementsByTag("title").html())) {
            loginCacheManager.reLogin(ChannelType.YH_WIKI);
            doc = HtmlGrabUtil.build(ChannelType.YH_WIKI.getValue()).getDoc(url);
        }
        Element enode = doc.getElementById("main-content");
        enode.getElementsByTag("a").forEach(n -> {
            if (n.attr("href").startsWith(grabUrl) || (!n.attr("href").startsWith("http"))) {
                if (n.attr("href").startsWith("/download")) {
                    n.attr("href", UrlChangeUtil.changeFileUrl(grabUrl, n.attr("href"), ChannelType.YH_WIKI.getValue(), docPlace));
                } else {
                    n.attr("href", "javascript:void(0)");
                }
            }
        });
        enode.getElementsByTag("img").forEach(n -> {
            if (!n.attr("src").startsWith("http")) {
                n.attr("src", imgUrl + UrlChangeUtil.changeSrcUrl(grabUrl, n.attr("src"), ChannelType.YH_WIKI.getValue(), imagePlace));
            }
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), decodeJsPlace, "decodeStr");
    }

    /**
     * 保存标题
     *
     * @param parentId
     * @param menuList
     * @param typeId
     */
    public void saveTitle(Long parentId, List<LeftMenu> menuList, Long typeId) {
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        try {
            menuList.forEach(n -> {
                if (n.getDocId() == null) {
                    System.out.println("");
                }
                BaseTitle title = baseTitleService.saveTitle(new SaveTitleRequestBuilder().ofParentId(parentId)
                        .onMenu(n).ofTypeId(typeId).ofChannel(ChannelType.YH_WIKI).build());
                if (!CollectionUtils.isEmpty(n.getMenuList())) {
                    this.saveTitle(title.getId(), n.getMenuList(), typeId);
                }
            });
        } catch (Exception e) {
            logger.info("保存标题出错", e);
        }
    }

}
