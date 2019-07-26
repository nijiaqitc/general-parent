package com.njq.grab.service.impl.novel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private XiangCunLoadPerformer xiangCunChannelPerformer;
	@Autowired
	private NovelLoadPerformer qishuLoadPerformer;
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
			mp.put(entry.getKey(), entry.getValue().search(str));
		}
		for(Map.Entry<String, String> entry:mp.entrySet()) {
			if(!StringUtil2.isEmpty(mp.get(entry.getKey()))) {
				NovelSearchPerformer former = SpringContextUtil.getBean(NovelSearchPerformer.class);
				former.saveNovelMenuUrl(str, entry.getValue(),entry.getKey());
			}
		}
		return mp;
	}
	public void saveNovelMenuUrl(String name,String url,String channel) {
		ConditionsCommon condition = new ConditionsCommon();
		grabNovelMenuDao.queryColumnForList(condition);
		GrabNovelMenu menu = new GrabNovelMenu();
		menu.setHref(url);
		menu.setName(name);
		menu.setCreateDate(new Date());
		menu.setType("0");
		grabNovelMenuDao.save(menu);
		GrabNovelUrl novelUrl = new GrabNovelUrl();
		novelUrl.setCreateDate(new Date());
		novelUrl.setUrl(url);
		novelUrl.setChannel(channel);
		novelUrl.setMenuId(menu.getId());
		grabNovelUrlDao.save(novelUrl);
	}
	
	public void loadDoc() {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("urlType", "1");
		List<GrabUrlInfo> list = grabUrlInfoDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(list)) {
			list.forEach(n->{
				factory.getPerformer(n.getChannel()).loadDoc();
			});
		}
	}
}
