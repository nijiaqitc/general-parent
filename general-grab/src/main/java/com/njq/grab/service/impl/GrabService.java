package com.njq.grab.service.impl;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.basis.service.impl.BaseTypeService;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.constants.TitleType;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.LeftMenu;
import com.njq.grab.cache.LoginCacheManager;
import com.njq.grab.service.impl.custom.CustomAnalysisPerformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GrabService {
    private static final Logger logger = LoggerFactory.getLogger(GrabService.class);
    @Resource
    private BaseTitleService baseTitleService;
    @Resource
    private BaseTypeService baseTypeService;
    @Resource
    private BaseTipService baseTipService;
    @Resource
    private LoginCacheManager loginCacheManager;
    @Resource
    private PerformerService performerService;
    @Resource
    private GrabUrlInfoService grabUrlInfoService;
    @Resource
    private SaveTitlePerformer grabSaveTitlePerformer;
    @Resource
    private ThreadPoolTaskExecutor loadPageTaskExecutor;
    @Resource
    private DaoCommon<GrabDoc> grabDocDao;
    @Resource
    private CustomAnalysisPerformer customAnalysisPerformer;

    public void loadPageJobTask() {
        List<BaseTitleLoading> list = baseTitleService.getLoadedTitle(null);
        list.parallelStream().forEach(n -> {
            loadPageTaskExecutor.submit(() -> {
                try {
                    performerService.getAnalysisPerformer(ChannelType.getChannelType(n.getChannel()))
                            .saveLoadingDoc(n.getUrl(), grabSaveTitlePerformer.getTitleById(n.getTitleId()));
                } catch (Exception e) {
                    logger.error("获取失败", e);
                }
            });
        });
        System.out.println(loadPageTaskExecutor.getActiveCount());
    }

    public void loadMenuJobTask() {
        List<GrabUrlInfo> list = grabUrlInfoService.getInfoList(true);
        list.forEach(n -> {
            loadPageTaskExecutor.submit(() -> {
                try {
                    performerService.getAnalysisPerformer(ChannelType.getChannelType(n.getChannel()))
                            .loadMenu(n.getMenuUrl(), baseTypeService.checkAndSave(n.getTypeName()));
                } catch (BaseKnownException e) {
                    logger.info("加载菜单出错", e);
                }
            });
        });
    }

    public void loadSingleDoc(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingById(docId);
        if (loading != null) {
            if (loading.getLoaded() == "1") {
                return;
            }
            loadDoc(loading);
        }
    }

    public void loadDoc(BaseTitleLoading titleLoading) {
        performerService.getAnalysisPerformer(ChannelType.getChannelType(titleLoading.getChannel()))
                .saveLoadingDoc(titleLoading.getUrl(), grabSaveTitlePerformer.getTitleById(titleLoading.getTitleId()));
    }

    public void updateSingleDoc(Long docId) {
        BaseTitleLoading loading = baseTitleService.getLoadingById(docId);
        BaseTitle title = baseTitleService.getTitleId(loading.getTitleId(), ChannelType.getChannelType(loading.getChannel()));
        if (title.getDocId() == null) {
            return;
        }
        GrabDoc grabDoc = this.queryById(title.getDocId());
        performerService.getAnalysisPerformer(ChannelType.getChannelType(loading.getChannel()))
                .updateDoc(loading.getUrl(), grabDoc.getTitle(), grabDoc.getId());
    }

    public void grabOperation(String title, String url, String docId, String channel, String type, String tips, Boolean reload) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(docId);
        GrabService service = SpringContextUtil.getBean(GrabService.class);
        if (loading == null) {
            service.saveAndGrab(title, url, docId, channel, type, tips);
        } else {
            if (reload) {
                if (loading.getLoaded().equals(Use_Type.USED)) {
                    service.updateAndGrab(title, url, loading.getTitleId() + "", channel, type, tips);
                } else {
                    ChannelType tt = ChannelType.getChannelType(channel);
                    this.performerService.getAnalysisPerformer(tt).loadPage(Long.parseLong(docId));
                }
            }
        }
    }

    public void saveAndGrab(String title, String url, String docId, String channel, String type, String tips) {
        long time1 = System.currentTimeMillis();
        loginCacheManager.checkAndLogin(ChannelType.getChannelType(channel));
        long time2 = System.currentTimeMillis();
        System.out.println("耗时a" + ((time2 - time1) / 1000));
        LeftMenu menu = new LeftMenu();
        menu.setValue(url);
        menu.setDocId(docId);
        menu.setName(title);
        ChannelType tt = ChannelType.getChannelType(channel);
        BaseTitle baseTitle = baseTitleService
                .saveTitle(new SaveTitleRequestBuilder()
                        .onMenu(menu)
                        .ofTypeId(baseTypeService.checkAndSave(type))
                        .ofTips(baseTipService.checkAndSaveTips(tips))
                        .ofChannel(tt.getValue())
                        .ofTitleType(TitleType.GRAB_TITLE)
                        .build());
        System.out.println("耗时b" + (System.currentTimeMillis() - time1) / 1000);
        performerService.getAnalysisPerformer(tt).saveLoadingDoc(url, baseTitle);
    }

    public void updateAndGrab(String title, String url, String docId, String channel, String type, String tips) {
        // 修改标题
        LeftMenu menu = new LeftMenu();
        menu.setValue(url);
        menu.setDocId(docId);
        menu.setName(title);
        ChannelType tt = ChannelType.getChannelType(channel);
        BaseTitle baseTitle = baseTitleService.updateTitle(new SaveTitleRequestBuilder().onMenu(menu).ofId(Long.valueOf(docId))
                .ofTypeId(baseTypeService.checkAndSave(type)).ofTips(baseTipService.checkAndSaveTips(tips))
                .ofChannel(tt.getValue()).build());

        // 修改文章
        performerService.getAnalysisPerformer(tt).updateDoc(url, title, baseTitle.getDocId());
    }


    public GrabDoc queryById(Long docId) {
        return grabDocDao.queryTById(docId);
    }


    public List<BaseTitle> queryTitleList(Long docId, ChannelType channel) {
        return baseTitleService.getTitleList(channel, docId);
    }


    public int queryTitleChildrenCount(Long docId, String channel) {
        return baseTitleService.childrenCount(docId, channel);
    }

    public void saveGrabCustomDoc(String title, String url, String docId,
                                  String channel, String type, String tips, String name, int getType) {
        BaseTitleLoading loading = baseTitleService.getLoadingByUrl(url);
        if (loading != null) {
            throw new BaseKnownException("文章已经入库，无需再次入库");
        }
        LeftMenu menu = new LeftMenu();
        menu.setValue(url);
        menu.setDocId(docId);
        menu.setName(title);
        BaseTitle baseTitle = baseTitleService
                .saveTitle(new SaveTitleRequestBuilder()
                        .onMenu(menu)
                        .ofTypeId(baseTypeService.checkAndSave(type))
                        .ofTips(baseTipService.checkAndSaveTips(tips))
                        .ofChannel(channel)
                        .ofTitleType(TitleType.GRAB_TITLE)
                        .build());
        customAnalysisPerformer.saveLoadingDoc(url, name, getType, baseTitle);
    }


    public void updateTips(String tipName, Long docId) {
        String tipId = baseTipService.checkAndSaveTips(tipName);
        BaseTitle title = baseTitleService.getTitleByDocId(docId);
        if (StringUtils.isEmpty(title.getTips())) {
            baseTitleService.updateTips(tipId, title.getId());
        } else {
        	if(!title.getTips().contains(tipId)) {
        		baseTitleService.updateTips(title.getTips() + "," + tipId, title.getId());
        	}
        }
    }
}
