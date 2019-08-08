package com.njq.grab.service.impl.novel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.GrabNovelMenu;
import com.njq.common.model.po.GrabNovelUrl;
import com.njq.common.model.po.GrabUrlInfo;
import com.njq.common.util.string.StringUtil2;

@Component
public class NovelSearchPerformer {

	@Resource
    private DaoCommon<GrabNovelMenu> grabNovelMenuDao;
	@Resource
	private DaoCommon<GrabUrlInfo> grabUrlInfoDao;
	@Resource
	private NovelPerformerFactory factory;
	@Resource
	private DaoCommon<GrabNovelUrl> grabNovelUrlDao;
	
	public Map<String, String> search(String str) {
		Map<String, String> mp = new HashMap<>();
		for(Map.Entry<String, NovelLoadPerformer> entry:factory.getMap().entrySet()) {
			String url = entry.getValue().search(str);
			if(url != null) {
				mp.put(entry.getKey(), url);
			}
		}
		for(Map.Entry<String, String> entry:mp.entrySet()) {
			if(!StringUtil2.isEmpty(mp.get(entry.getKey()))) {
				NovelSearchPerformer former = SpringContextUtil.getBean(NovelSearchPerformer.class);
				former.saveNovelMenuUrl(str, entry.getValue(),entry.getKey());
			}
		}
		
		loadMenu(str,null);
		return mp;
	}
	public void saveNovelMenuUrl(String name,String url,String channel) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("url", url);
		List<GrabNovelUrl> list = grabNovelUrlDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(list)) {
			return ;
		}
		condition = new ConditionsCommon();
		condition.addEqParam("name", name);
		GrabNovelMenu menu =  grabNovelMenuDao.queryTByParamForOne(condition);
		if(menu == null) {
			menu = new GrabNovelMenu();
			menu.setHref(url);
			menu.setName(name);
			menu.setCreateDate(new Date());
			menu.setType("0");
			menu.setChannel(channel);
			menu.setLoaded(0);
			menu.setLoadTimes(0);
			grabNovelMenuDao.save(menu);			
		}
		GrabNovelUrl novelUrl = new GrabNovelUrl();
		novelUrl.setCreateDate(new Date());
		novelUrl.setUrl(url);
		novelUrl.setChannel(channel);
		novelUrl.setMenuId(menu.getId());
		grabNovelUrlDao.save(novelUrl);
	}
	
	
	public void loadMenu(String bookName,Long menuId) {
		ConditionsCommon condition = new ConditionsCommon();
		if(StringUtil2.IsNotEmpty(bookName)) {
			condition.addEqParam("name", bookName);			
		}
		if(menuId != null) {
			condition.addEqParam("id", menuId);
		}
		condition.addEqParam("type", "0");
		GrabNovelMenu bookMenu = grabNovelMenuDao.queryTByParamForOne(condition);
		if(bookMenu != null) {
			condition = new ConditionsCommon();
			condition.addEqParam("parentId", bookMenu.getId());
			//获取当前的标题列表
			List<GrabNovelMenu> menuList = grabNovelMenuDao.queryColumnForList(condition);
			condition = new ConditionsCommon();
			condition.addEqParam("menuId", bookMenu.getId());
			//读取load的url值
			List<GrabNovelUrl> urlList = grabNovelUrlDao.queryColumnForList(condition);
			for(GrabNovelUrl url : urlList) {
				NovelSearchPerformer performer = SpringContextUtil.getBean(NovelSearchPerformer.class);
				List<GrabNovelMenu> list1 = factory.getPerformer(url.getChannel()).loadMenu(url.getUrl(),bookMenu.getId());
				if(list1 == null) {
					continue;
				}
				if(CollectionUtils.isEmpty(menuList)) {
					performer.save(list1);
				}else {
					if(menuList.size() < list1.size()) {
						if(menuList.get(menuList.size()-1).getName().equals(list1.get(menuList.size()-1).getName())) {
							List<GrabNovelMenu> list2  = list1.subList(menuList.size(), list1.size());
							performer.save(list2);
						}
					}					
				}
				menuList = list1;
			}
		}
	}
	
	
	public void save(List<GrabNovelMenu> list) {
		for(GrabNovelMenu menu :list) {
			grabNovelMenuDao.save(menu);
		}
	}
	
	public void loadDoc() {
		for(Map.Entry<String, NovelLoadPerformer> entry:factory.getMap().entrySet()) {
			factory.getPerformer(entry.getKey()).loadDoc();
		}
	}
}
