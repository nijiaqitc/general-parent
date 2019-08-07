package com.njq.grab.service.impl.novel;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.GrabNovelDoc;
import com.njq.common.model.po.GrabNovelMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component("qishuLoadPerformer")
public class QishuLoadPerformer implements NovelLoadPerformer{
	private static final Logger logger = LoggerFactory.getLogger(QishuLoadPerformer.class);
	private String url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.QI_SHU).getPageIndex();
	@Resource
    private DaoCommon<GrabNovelMenu> grabNovelMenuDao;
	@Resource
	private DaoCommon<GrabNovelDoc> grabNovelDocDao;
	@Resource
    private ThreadPoolTaskExecutor loadPageTaskExecutor;
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
	public List<GrabNovelMenu> loadMenu(String str,Long parentId) {
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
		List<Element> list = es.subList(0, 16);
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
				menu.setParentId(parentId);
				menu.setLoadTimes(0);
				menu.setChannel(ChannelType.QI_SHU.getValue());
				menuList.add(menu);
			}
		});
		return menuList;
	}

	@Override
	public void loadDoc() {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("loaded", 0);
		condition.addEqParam("channel", ChannelType.QI_SHU.getValue());
		condition.addLteParam("loadTimes", 10);
		List<GrabNovelMenu> menuList = grabNovelMenuDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(menuList)) {
			Semaphore semaphore = new Semaphore(10,true);
			menuList.forEach(n->{
				loadPageTaskExecutor.submit(() -> {
					Document doc = HtmlGrabUtil
		                .build(ChannelType.CUSTOM.getValue())
		                .getDoc(n.getHref());
					try {
						semaphore.acquire();
						logger.info("读取信号量并发："+(10-semaphore.availablePermits()));
						QishuLoadPerformer former = SpringContextUtil.getBean(QishuLoadPerformer.class);
						former.updateToSave(doc,n.getId());
					} catch (InterruptedException e) {
						logger.error("读取报错"+e.getMessage());
					}finally {
	                    semaphore.release();
	                }
				});
			});
		}
		
	}
	
	public void updateToSave(Document doc,Long menuId) {
		GrabNovelMenu menu = grabNovelMenuDao.queryTById(menuId);
		if(menu.getLoadTimes()>10) {
			return;
		}
		Element et = doc.getElementById("content");
		if(et != null) {
			ConditionsCommon condition = new ConditionsCommon();
			condition.addEqParam("id", menuId);
			condition.addsetObjectParam("loaded", 1);
			condition.addsetObjectParam("loadTimes", menu.getLoadTimes()+1);
			condition.addNotEqParam("loaded", 1);
			int num = grabNovelMenuDao.update(condition);			
			if(num > 0) {
				logger.info("并发修改失败,menuId："+menuId);
			}
			GrabNovelDoc dc = new GrabNovelDoc();
			dc.setCreateDate(new Date());
			dc.setDoc(dealHtml(et.html()));
			dc.setMenuId(menuId);
			grabNovelDocDao.save(dc);
			
		}else {
			ConditionsCommon condition = new ConditionsCommon();
			condition.addEqParam("id", menuId);
			condition.addsetObjectParam("loadTimes", menu.getLoadTimes()+1);
			grabNovelMenuDao.update(condition);
		}
	}

	
	public String dealHtml(String str) {
		str = str.replace("奇书网 www.qishu.tw 最快更新这里有妖气最新章节！", "");
		return HtmlDecodeUtil.decodeHtml(str, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
	}
}
