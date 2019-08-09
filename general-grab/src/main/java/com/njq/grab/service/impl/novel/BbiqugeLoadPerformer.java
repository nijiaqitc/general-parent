package com.njq.grab.service.impl.novel;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component("bbiqugeLoadPerformer")
public class BbiqugeLoadPerformer extends AbstractLoadPerformer{

	private static final Logger logger = LoggerFactory.getLogger(BbiqugeLoadPerformer.class);
	private String url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.BBIQUGE).getPageIndex();
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
				menu.setChannel(ChannelType.BBIQUGE.getValue());
				menuList.add(menu);
			}
		});
		return menuList;
	}

	@Override
	public void loadDoc() {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("loaded", 0);
		condition.addEqParam("channel", ChannelType.BBIQUGE.getValue());
		condition.addEqParam("type", "1");
		condition.addLteParam("loadTimes", 10);
		List<GrabNovelMenu> menuList = grabNovelMenuDao.queryColumnForList(condition);
		Map<Long, String> bookMap = new HashMap<Long, String>();
		if(CollectionUtils.isNotEmpty(menuList)) {
			Semaphore semaphore = new Semaphore(10,true);
			BbiqugeLoadPerformer former = SpringContextUtil.getBean(BbiqugeLoadPerformer.class);
			menuList.forEach(n->{
				if(bookMap.get(n.getParentId()) == null) {
					GrabNovelMenu bookMenu = grabNovelMenuDao.queryTById(n.getParentId());
					bookMap.put(n.getParentId(), bookMenu.getName());
				}
				loadPageTaskExecutor.submit(() -> {
					Document doc = super.grabUrl(n.getHref());
					try {
						semaphore.acquire();
						logger.info("读取信号量并发："+(10-semaphore.availablePermits()));
						former.updateToSave(doc,n.getId(),bookMap.get(n.getParentId()));
					} catch (InterruptedException e) {
						logger.error("读取报错"+e.getMessage());
					}finally {
	                    semaphore.release();
	                }
				});
			});
		}
		
	}
	
	public void updateToSave(Document doc,Long menuId,String bookName) {
		Element et = doc.getElementById("content");
		super.updateToSave(et, menuId, bookName);
	}

	
	public String dealHtml(String str,String bookName) {
		str = str.replace("一秒记住【笔趣阁 www.bbiquge.com】，精彩小说无弹窗免费阅读！", "");
		return HtmlDecodeUtil.decodeHtml(str, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
	}

}
