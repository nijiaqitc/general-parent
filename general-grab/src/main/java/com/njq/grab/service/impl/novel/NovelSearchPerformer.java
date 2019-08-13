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
import com.njq.common.exception.BaseKnownException;
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
		if(mp.isEmpty()) {
			return null;
		}
		NovelSearchPerformer former = SpringContextUtil.getBean(NovelSearchPerformer.class);
		Long menuId = former.saveBook(str);
		for(Map.Entry<String, String> entry:mp.entrySet()) {
			if(!StringUtil2.isEmpty(mp.get(entry.getKey()))) {
				former.saveNovelMenuUrl(str, entry.getValue(),entry.getKey(),menuId);
			}
		}
		loadMenu(str,null);
		for(Map.Entry<String, NovelConsultPerformer> entry:factory.getConsultMap().entrySet()) {
			String u = entry.getValue().search(str);
			if(u != null) {
				former.saveNovelConsult(str, u, entry.getKey(), menuId);
				break;
			}
		}
		
		return mp;
	}
	public Long saveBook(String name) {
		if(name == null) {
			throw new BaseKnownException("书名为空！");
		}
		synchronized (name.intern()) {
			ConditionsCommon condition = new ConditionsCommon();
			condition = new ConditionsCommon();
			condition.addEqParam("name", name);
			GrabNovelMenu menu =  grabNovelMenuDao.queryTByParamForOne(condition);
			if(menu == null) {
				menu = new GrabNovelMenu();
				menu.setName(name);
				menu.setCreateDate(new Date());
				menu.setType("0");
				menu.setLoaded(0);
				menu.setLoadTimes(0);
				grabNovelMenuDao.save(menu);			
			}
			return menu.getId();
		}
	}
	
	public void saveNovelMenuUrl(String name,String url,String channel,Long menuId) {
		String lockey=name+":"+url+":"+channel+":"+menuId;
		synchronized (lockey.intern()) {
			GrabNovelUrl novelUrl = new GrabNovelUrl();
			novelUrl.setCreateDate(new Date());
			novelUrl.setUrl(url);
			novelUrl.setChannel(channel);
			novelUrl.setMenuId(menuId);
			novelUrl.setType("1");
			grabNovelUrlDao.save(novelUrl);			
		}
	}
	
	public void saveNovelConsult(String name,String url,String channel,Long menuId) {
		String lockey=name+":"+url+":"+channel+":"+menuId;
		synchronized (lockey) {
			GrabNovelUrl novelUrl = new GrabNovelUrl();
			novelUrl.setCreateDate(new Date());
			novelUrl.setUrl(url);
			novelUrl.setChannel(channel);
			novelUrl.setMenuId(menuId);
			novelUrl.setType("0");
			grabNovelUrlDao.save(novelUrl);
		}
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
			condition.addEqParam("type", "1");
			//读取load的url值
			List<GrabNovelUrl> urlList = grabNovelUrlDao.queryColumnForList(condition);
			
			for(GrabNovelUrl url : urlList) {
				List<GrabNovelMenu> list1 = factory.getPerformer(url.getChannel()).loadMenu(url.getUrl(),bookMenu.getId());
				if(list1 == null) {
					continue;
				}
				if(CollectionUtils.isEmpty(menuList)) {
					this.toSaveMenu(list1);
				}else {
					if(menuList.size() < list1.size()) {
						if(menuList.get(menuList.size()-1).getName().equals(list1.get(menuList.size()-1).getName())) {
							List<GrabNovelMenu> list2  = list1.subList(menuList.size(), list1.size());
							this.toSaveMenu(list2);
						}
					}					
				}
				menuList = list1;
			}
		}
	}
	
	
	private void toSaveMenu(List<GrabNovelMenu> list) {
		NovelSearchPerformer performer = SpringContextUtil.getBean(NovelSearchPerformer.class);
		for(GrabNovelMenu menu :list) {
			performer.saveMenu(menu);
		}
	}
	
	public void saveMenu(GrabNovelMenu menu) {
		grabNovelMenuDao.save(menu);
	}
	
	public void loadDoc() {
		for(Map.Entry<String, NovelLoadPerformer> entry:factory.getMap().entrySet()) {
			factory.getPerformer(entry.getKey()).loadDoc();
		}
	}
}
