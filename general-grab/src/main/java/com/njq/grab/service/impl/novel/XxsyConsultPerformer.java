package com.njq.grab.service.impl.novel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

@Component("xxsyConsultPerformer")
public class XxsyConsultPerformer implements NovelConsultPerformer{

	private static final Logger logger = LoggerFactory.getLogger(ZonghengConsultPerformer.class);
	private String url;
	@Autowired
	public XxsyConsultPerformer() {
		url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.XIAOXIANGSHUYUAN).getPageIndex();
	}

	@Override
	public String search(String name) {
		try {
			String searchUrl = url+"/search?s_wd="+URLEncoder.encode(name, "utf-8");
			Document doc = HtmlGrabUtil
	                .build(ChannelType.CUSTOM.getValue())
	                .getDoc(searchUrl);
			Elements ets = doc.getElementsByClass("result-list");
			if(ets.isEmpty()) {
				return null;
			}
			Elements ets1 = ets.get(0).getElementsByTag("li");
			if(ets1.isEmpty()) {
				return null;
			}
			Elements ets2 = ets1.get(0).getElementsByTag("h4");
			if(ets2.isEmpty()) {
				return null;
			}
			Elements etsa = ets2.get(0).getElementsByTag("a");
			if(etsa.isEmpty()) {
				return null;
			}
			if(!name.equals(etsa.get(0).html())) {
				return null;
			}
			String href1 = etsa.get(0).attr("href");
	        String[] ss= href1.split("/");
	        String bb = ss[ss.length-1];
	        String bookId = bb.split("\\.")[0];
	        String uu =url + "/partview/GetChapterList?bookid="+bookId;
			 return uu;
		} catch (UnsupportedEncodingException e) {
			logger.error("文章搜索出错："+e.getMessage());
			return null;
		} 
	}
	
	@Resource
	private DaoCommon<GrabNovelUrl> grabNovelUrlDao;
	
	@Override
	public List<String> loadMenu(Long menuId) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("menuId", menuId);
		condition.addEqParam("type", "0");
		condition.addEqParam("channel", ChannelType.XIAOXIANGSHUYUAN.getValue());
		List<GrabNovelUrl> infoList = grabNovelUrlDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(infoList)) {
			String u = infoList.get(0).getUrl();
			Document doc1 = HtmlGrabUtil
					.build(ChannelType.CUSTOM.getValue())
					.getDoc(u);
			 Element et = doc1.getElementById("chapter");
			 Elements etsd = et.getElementsByTag("dd");
			 List<Element> ll = new ArrayList<>();
			 etsd.forEach(n->{
				 Elements etss= n.getElementsByTag("li");
				 ll.addAll(etss);
			 });
			 List<String> titleList = ll.stream().map(n->{
				 Elements emts = n.getElementsByTag("a");
				 if(emts.isEmpty()) {
					 return null;
				 }
			 return emts.get(0).html();
			 }).filter(n->n!=null).collect(Collectors.toList());
		 	return titleList;
		}else {
			return Collections.EMPTY_LIST;
		}
	}
}
