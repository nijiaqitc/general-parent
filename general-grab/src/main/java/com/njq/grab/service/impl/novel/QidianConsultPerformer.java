package com.njq.grab.service.impl.novel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
@Component("qidianConsultPerformer")
public class QidianConsultPerformer implements NovelConsultPerformer{
	private static final Logger logger = LoggerFactory.getLogger(QidianConsultPerformer.class);
	private String url ;
	@Resource
	private DaoCommon<GrabNovelUrl> grabNovelUrlDao;
	@Autowired
	public QidianConsultPerformer() {
		url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.QIDIAN).getPageIndex();
	}

	@Override
	public String search(String name) {
		try {
			String searchUrl = url+"/search?kw="+URLEncoder.encode(name, "utf-8");
			Document doc = HtmlGrabUtil
	                .build(ChannelType.CUSTOM.getValue())
	                .getDoc(searchUrl);
			Element et = doc.getElementById("result-list");
			Elements ets = et.getElementsByTag("li");
			if(ets.size() > 0) {
				Element fet = ets.get(0);
				Elements etsh4 = fet.getElementsByTag("h4");
				Elements etsa = etsh4.get(0).getElementsByTag("a");
				if(etsa.isEmpty()) {
					return null;
				}
				Elements etscite = etsa.get(0).getElementsByTag("cite");
				if(etscite.isEmpty()) {
					return null;
				}
				System.out.println(etscite.get(0).html());
				return "https:"+etsa.attr("href");
			}
			return null;
		} catch (UnsupportedEncodingException e) {
			logger.error("文章搜索出错："+e.getMessage());
			return null;
		} 
	}
	
	@Override
	public List<String> loadMenu(Long menuId) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("menuId", menuId);
		condition.addEqParam("type", "0");
		condition.addEqParam("channel", ChannelType.QIDIAN.getValue());
		List<GrabNovelUrl> infoList = grabNovelUrlDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(infoList)) {
			String u = infoList.get(0).getUrl();
			Document doc = HtmlGrabUtil
					.build(ChannelType.CUSTOM.getValue())
					.getDoc(u);
			Elements ets = doc.getElementsByClass("volume-wrap");
			Elements etas = ets.select("li a");
			List<String> ll = etas.stream().map(n->{
				return n.html();
			}).collect(Collectors.toList());
			Collections.reverse(ll);
			return ll;
		}else {
			return Collections.EMPTY_LIST;
		}
	}
}
