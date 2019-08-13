package com.njq.grab.service.impl.novel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.GrabNovelUrl;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component("zonghengConsultPerformer")
public class ZonghengConsultPerformer implements NovelConsultPerformer{
	private static final Logger logger = LoggerFactory.getLogger(ZonghengConsultPerformer.class);
	private String url ;
	@Autowired
	public ZonghengConsultPerformer() {
		url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.ZONGHENG).getPageIndex();
	}


	@Override
	public String search(String name) {
		try {
			String searchUrl = url+"/s?keyword="+name;
			Document doc = HtmlGrabUtil
	                .build(ChannelType.CUSTOM.getValue())
	                .getDoc(searchUrl);
			Elements ets1 = doc.getElementsByClass("search-result-list");
			if(ets1.isEmpty()) {
				return null;
			}
			Elements ets2 = ets1.get(0).getElementsByClass("tit");
			if(ets2.isEmpty()) {
				return null;
			}
			Elements ets4 = ets2.get(0).getElementsByTag("font");
			List<String> list = ets4.stream().map(n->{
				return n.html();
			}).collect(Collectors.toList());
			StringBuffer stb = new StringBuffer();
			for(String n:list) {
				stb.append(n);
			}
			Elements ets3 = ets2.get(0).getElementsByTag("a");
			String href = ets3.get(0).attr("href");
			if(stb.toString().equals(name)) {
				return search1(href);
			}
			return null;
		} catch (Exception e) {
			logger.error("文章搜索出错："+e.getMessage());
			return null;
		} 
	}
	
	
	private String search1(String url) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(url);
		Elements ets = doc.getElementsByClass("all-catalog");
		if(ets.isEmpty()) {
			return null;
		}
		return ets.get(0).attr("href");
	}
	
	@Resource
	private DaoCommon<GrabNovelUrl> grabNovelUrlDao;
	
	@Override
	public List<String> loadMenu(Long menuId) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("menuId", menuId);
		condition.addEqParam("type", "0");
		condition.addEqParam("channel", ChannelType.ZONGHENG.getValue());
		List<GrabNovelUrl> infoList = grabNovelUrlDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(infoList)) {
			String u = infoList.get(0).getUrl();
			Document doc = HtmlGrabUtil
	                .build(ChannelType.CUSTOM.getValue())
	                .getDoc(u);
			Elements ets = doc.getElementsByClass("chapter-list");
			if(ets.isEmpty()) {
				return null;
			}
			List<Element> nodes = ets.subList(1, ets.size());
			List<String> titles = new ArrayList<>();
			nodes.forEach(n->{
				Elements as = n.getElementsByTag("a");
				as.forEach(m->{
					titles.add(m.html());
				});
			});
			return titles;
		}else {
			return Collections.EMPTY_LIST;
		}
	}

}
