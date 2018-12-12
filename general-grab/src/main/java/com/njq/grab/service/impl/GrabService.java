package com.njq.grab.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.basis.service.impl.BaseTypeService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.ConstantsCommon.Use_Type;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.LeftMenu;
import com.njq.grab.cache.LoginCacheManager;

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

	public void loadPageJobTask() {

	}

	public void loadMenuJobTask() {
		List<GrabUrlInfo> list = grabUrlInfoService.getInfoList();
		list.forEach(n -> {
			performerService.getAnalysisPerformer(ChannelType.getChannelType(n.getChannel()))
				.loadMenu(n.getMenuUrl(),baseTypeService.checkAndSave(n.getTypeName()));
		});
	}

	public void grabOperation(String title, String url, String docId, String channel, String type, String tips) {
		BaseTitleLoading loading = baseTitleService.getLoadingByDocId(docId);
		if (loading == null) {
			this.saveAndGrap(title, url, docId, channel, type, tips);
		} else {
			if (loading.getLoaded().equals(Use_Type.USED)) {
				this.updateAndGrap(title, url, loading.getTitleId() + "", channel, type, tips);
			} else {
				ChannelType tt = ChannelType.getChannelType(channel);
				this.performerService.getAnalysisPerformer(tt).loadPage(Long.parseLong(docId));
			}
		}
	}

	public void saveAndGrap(String title, String url, String docId, String channel, String type, String tips) {
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
		Long id = performerService.getAnalysisPerformer(tt).saveDoc(url, title);
		System.out.println("耗时d" + (System.currentTimeMillis() - time1) / 1000);
		baseTitleService.updateLoadSuccess(ChannelType.getChannelType(channel), String.valueOf(id), baseTitle.getId());
		System.out.println("耗时e" + (System.currentTimeMillis() - time1) / 1000);
	}

	public void updateAndGrap(String title, String url, String docId, String channel, String type, String tips) {
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
}
