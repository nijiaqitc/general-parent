package com.njq.grab.service.impl.yhwiki;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.basis.service.impl.GrapSaveTitlePerformerImpl;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.request.SaveTitleRequestBuilder;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.BaseTitleLoading;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.model.vo.GrabUrlYhInfo;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.string.UrlUtil;
import com.njq.grab.cache.LoginCacheManager;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.GrabUrlInfoService;

@Component("yhWikiPageAnalysis")
public class YhWikiPageAnalysisPerformerImpl implements PageAnalysisPerformer {
	private static final Logger logger = LoggerFactory.getLogger(YhWikiPageAnalysisPerformerImpl.class);
	@Resource
	private BaseTitleService baseTitleService;
	@Resource
	private DaoCommon<GrabDoc> grabDocDao;
	@Resource
	private GrapSaveTitlePerformerImpl grapSaveTitlePerformer;
	@Resource
	private LoginCacheManager loginCacheManager;
	@Resource
	private GrabUrlInfoService grabUrlInfoService;
	private static GrabUrlInfo urlInfo;

	@Override
	public void loadPageJobTask() {
		List<BaseTitleLoading> list = baseTitleService.getLoadedTitle(ChannelType.YH_WIKI.getValue());
		list.forEach(n -> {
			this.saveDoc(n.getUrl(), grapSaveTitlePerformer.getTitleById(n.getTitleId()).getTitle());
		});
	}

	@Override
	public void loadPage(Long docId) {
		BaseTitleLoading loading = baseTitleService.getLoadingByDocId(String.valueOf(docId));
		this.saveDoc(loading.getUrl(), grapSaveTitlePerformer.getTitleById(loading.getTitleId()).getTitle());
	}

	@Override
	public void login() {
		urlInfo = grabUrlInfoService.getUrlInfoByChannel(ChannelType.YH_WIKI.getValue());
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
		List<LeftMenu> menuList = load(url);
		sort(menuList);
		this.saveTitle(null, menuList, typeId);
	}

	/**
	 * 加载菜单，由于要递归所以单独抽一个方法
	 * 
	 * @param url
	 * @return
	 */
	private List<LeftMenu> load(String url) {
		Document doc = HtmlGrabUtil.build(urlInfo.getShortName()).getDoc(url);
		Elements e = doc.getElementsByTag("a");
		List<LeftMenu> list = new ArrayList<>();
		e.forEach(n -> {
			LeftMenu menu = new LeftMenu();
			if (n.id().startsWith("plusminus")) {
//				menu.setType(0);
//				String uu ="http://wiki.yonghuivip.com/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=true&pageId="+n.id().replace("plusminus", "").split("\\-")[0]+"&treeId=0&startDepth=0&mobile=false&treePageId=2300252&_=1537839754796";
//				menu.setMenuList(load(uu));
			} else {
				menu.setType(1);
				menu.setName(n.html());
				menu.setValue(n.attr("href"));
				menu.setDocId(UrlUtil.URLRequest(n.attr("href")).get("pageId"));
			}
			list.add(menu);
			logger.info(n.html() + "--" + n.attr("href"));
		});

		return list;
	}

	/**
	 * 对菜单进行排序操作（原先list里面 子菜单在母菜单前面，所以要移下来）
	 * 
	 * @param list
	 */
	private void sort(List<LeftMenu> list) {
		if (list == null) {
			return;
		}
		LeftMenu menu;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getType() == 0) {
				menu = list.get(i);
				try {
					list.set(i, list.get(i + 1));
					list.set(i + 1, menu);
					++i;
				} catch (Exception e) {
					logger.info("菜单排序出现错", e);
				}
				this.sort(menu.getMenuList());
			}
		}
	}

	@Override
	public Long saveDoc(String url, String title) {
		loginCacheManager.checkAndLogin(ChannelType.YH_WIKI);
		String doc = this.analysisPage(url);
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
		loginCacheManager.checkAndLogin(ChannelType.YH_WIKI);
		String doc = this.analysisPage(url);
		GrabDoc grabDoc = new GrabDoc();
		grabDoc.setId(id);
		grabDoc.setDoc(doc);
		grabDoc.setTitle(title);
		grabDocDao.updateByPrimaryKeySelective(grabDoc);
		return id;
	}

	@Override
	public String analysisPage(String url) {

		// Document doc = HtmlGrabUtil
//				.build("wiki")
//				.getDoc("http://wiki.yonghuivip.com/plugins/pagetree/naturalchildren.action?decorator=none&excerpt=false&sort=position&reverse=false&disableLinks=false&expandCurrent=true&hasRoot=true&pageId=2302391&treeId=0&startDepth=0&mobile=false&ancestors=2302391&treePageId=2302391");
//		System.out.println(menuList);
//		Element enode = HtmlGrabUtil
//				.build("wiki").getDoc("http://wiki.yonghuivip.com/pages/viewpage.action?pageId=5572937").getElementById("main-content");
//		
//		
//		
//		enode.getElementsByTag("a").forEach(n->{
//			if(!n.attr("href").startsWith("http")) {
//				n.attr("href","http://wiki.yonghuivip.com"+n.attr("href"));
//			}
//		});
//		enode.getElementsByTag("img").forEach(n->{
//			if(!n.attr("src").startsWith("http")) {
//				n.attr("src",UrlChangeUtil.changeSrcUrl(pageIndex,n.attr("src"),shortName));
//			}
//		});

//		return enode.html();
		return "123";
	}

	/**
	 * 保存标题
	 * 
	 * @param parentId
	 * @param menuList
	 * @param typeId
	 */
	public void saveTitle(Long parentId, List<LeftMenu> menuList, Long typeId) {
		try {
			menuList.forEach(n -> {
				BaseTitle title = baseTitleService.saveTitle(new SaveTitleRequestBuilder().ofParentId(parentId)
						.onMenu(n).ofTypeId(typeId).ofChannel(ChannelType.YH_WIKI).build());
				if (!CollectionUtils.isEmpty(n.getMenuList())) {
					this.saveTitle(title.getId(), menuList, typeId);
				}
			});
		} catch (Exception e) {
			logger.info("保存标题出错", e);
		}
	}

}
