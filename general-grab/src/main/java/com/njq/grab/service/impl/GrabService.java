package com.njq.grab.service.impl;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.basis.service.impl.BaseTypeService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.LeftMenu;
import com.njq.grab.cache.LoginCacheManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GrabService {
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
    
    public void loadPageJobTask() {
        List<BaseTitleLoading> list = baseTitleService.getLoadedTitle(ChannelType.YH_WIKI.getValue());
        list.parallelStream().forEach(n -> {
            loadPageTaskExecutor.submit(() -> {
                performerService.getAnalysisPerformer(ChannelType.getChannelType(n.getChannel()))
                        .saveLoadingDoc(n.getUrl(), grabSaveTitlePerformer.getTitleById(n.getTitleId()));
            });
        });
        loadPageTaskExecutor.getActiveCount();
    }

    public void loadMenuJobTask() {
        List<GrabUrlInfo> list = grabUrlInfoService.getInfoList();
        list.forEach(n -> {
            performerService.getAnalysisPerformer(ChannelType.getChannelType(n.getChannel()))
                    .loadMenu(n.getMenuUrl(), baseTypeService.checkAndSave(n.getTypeName()));
        });
    }

    public void grabOperation(String title, String url, String docId, String channel, String type, String tips) {
        BaseTitleLoading loading = baseTitleService.getLoadingByDocId(docId);
        if (loading == null) {
            this.saveAndGrab(title, url, docId, channel, type, tips);
        } else {
            if (loading.getLoaded().equals(Use_Type.USED)) {
                this.updateAndGrab(title, url, loading.getTitleId() + "", channel, type, tips);
            } else {
                ChannelType tt = ChannelType.getChannelType(channel);
                this.performerService.getAnalysisPerformer(tt).loadPage(Long.parseLong(docId));
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
                .saveTitle(new SaveTitleRequestBuilder().onMenu(menu).ofTypeId(baseTypeService.checkAndSave(type))
                        .ofTips(baseTipService.checkAndSaveTips(tips)).ofChannel(tt).build());
        System.out.println("耗时b" + (System.currentTimeMillis() - time1) / 1000);
        baseTypeService.addNum(channel, baseTitle.getTypeId());
        System.out.println("耗时c" + (System.currentTimeMillis() - time1) / 1000);
        performerService.getAnalysisPerformer(tt).saveLoadingDoc(url, baseTitle);
    }

    public void updateAndGrab(String title, String url, String docId, String channel, String type, String tips) {
        // 修改标题
        LeftMenu menu = new LeftMenu();
        menu.setValue(url);
        menu.setDocId(docId);
        menu.setName(title);
        ChannelType tt = ChannelType.getChannelType(channel);
        baseTitleService.updateTitle(new SaveTitleRequestBuilder().onMenu(menu).ofId(Long.valueOf(docId))
                .ofTypeId(baseTypeService.checkAndSave(type)).ofTips(baseTipService.checkAndSaveTips(tips))
                .ofChannel(tt).build());
        // 修改文章
        performerService.getAnalysisPerformer(tt).updateDoc(url, title, Long.valueOf(docId));
    }
    
    
    public GrabDoc queryById(Long docId) {
    	return grabDocDao.queryTById(docId);
    }
    
    
    public List<BaseTitle> queryTitleList(Long docId,ChannelType channel){
    	return baseTitleService.getTitleList(channel, docId);
    }
    
    
    public int queryTitleChildrenCount(Long docId,ChannelType channel) {
    	return baseTitleService.childrenCount(docId,channel);
    }
}
