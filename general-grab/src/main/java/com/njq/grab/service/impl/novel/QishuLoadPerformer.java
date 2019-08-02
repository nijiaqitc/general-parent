package com.njq.grab.service.impl.novel;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.GrabNovelDoc;
import com.njq.common.model.po.GrabNovelMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component("qishuLoadPerformer")
public class QishuLoadPerformer implements NovelLoadPerformer{

	private String url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.QI_SHU).getPageIndex();
	@Resource
    private DaoCommon<GrabNovelMenu> grabNovelMenuDao;
	
	private DaoCommon<GrabNovelDoc> grabNovelDocDao;
	@Override
	public String search(String str) {
		try {
			String searchUrl = url+"/modules/article/search.php?searchkey="+URLEncoder.encode(str, "gb2312"); 
			Document doc =  this.grabUrl(searchUrl);
			Element elt =  doc.getElementById("info");
			if(elt != null) {
				Elements links = doc.getElementsByTag("link");
				for(Element element:links) {
					if("canonical".equals(element.attr("rel"))) {
						return element.attr("href");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Document grabUrl(String str) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(str);
		return doc;
	}
	
	@Override
	public void loadDetail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GrabNovelMenu> loadMenu(String str) {
		System.out.println(12121212);
		Document doc = this.grabUrl(str);
		Elements links = doc.getElementsByTag("link");
		String h1 = "";
		for(Element element:links) {
			if("canonical".equals(element.attr("rel"))) {
				h1 = element.attr("href");
				break;
			}
		}
		Elements es = doc.getElementsByTag("dd");
		String h2 = h1;
		List<Element> list = es.subList(0, 10);
		List<GrabNovelMenu> menuList = new ArrayList<GrabNovelMenu>();
		list.forEach(e->{
			Elements e1 = e.getElementsByTag("a"); 
			if(e1 != null && e1.size()>0) {
				System.out.println(e1.html());
				GrabNovelMenu menu = new GrabNovelMenu();
				menu.setCreateDate(new Date());
				menu.setName(e1.get(0).html().trim());
				menu.setType("1");
				menu.setHref(h2+e1.get(0).attr("href"));
				menu.setLoaded(0);
				menuList.add(menu);
			}
		});
		return menuList;
	}

	@Override
	public void loadDoc() {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("loaded", 0);
		List<GrabNovelMenu> menuList = grabNovelMenuDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(menuList)) {
			menuList.forEach(n->{
				Document doc = HtmlGrabUtil
		                .build(ChannelType.CUSTOM.getValue())
		                .getDoc(n.getHref());
				QishuLoadPerformer former = SpringContextUtil.getBean(QishuLoadPerformer.class);
				former.updateToSave(doc,n.getId());
			});
		}
		
	}
	
	public void updateToSave(Document doc,Long menuId) {
		Element et = doc.getElementById("content");
		GrabNovelDoc dc = new GrabNovelDoc();
		dc.setCreateDate(new Date());
		dc.setDoc(dealHtml(et.html()));
		dc.setMenuId(menuId);
		grabNovelDocDao.save(dc);
		
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("id", menuId);
		condition.addEqParam("loaded", 1);
		grabNovelMenuDao.update(condition);
	}

	
	public String dealHtml(String str) {
		str = str.replace("奇书网 www.qishu.tw 最快更新这里有妖气最新章节！", "");
		return str;
	}
}
