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
@Component("xiangCunChannelPerformer")
public class XiangCunLoadPerformer implements NovelLoadPerformer{
	
	private String url =  GrabUrlInfoFactory.getUrlInfo(ChannelType.XIANGCUN).getPageIndex();
	@Resource
    private DaoCommon<GrabNovelMenu> grabNovelMenuDao;
	@Resource
	private DaoCommon<GrabNovelDoc> grabNovelDocDao;
	
	@Override
	public String search(String str) {
		try {
			Document doc = HtmlGrabUtil
					.build(ChannelType.CUSTOM.getValue())
					.getDoc(url+"/modules/article/search.php?searchtype=articlename&searchkey="+URLEncoder.encode(str, "gb2312"));
			Elements ets =  doc.getElementsByClass("read_yd");
			if(ets.size()>0) {
				String href = url + ets.get(0).getElementsByTag("a").get(0).attr("href");
//				loadMenu(href);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public void loadDetail() {
		ConditionsCommon condition = new ConditionsCommon();
		
		grabNovelMenuDao.queryColumnForList(condition);
		GrabNovelMenu menu = new GrabNovelMenu();
		menu.setHref("");
		menu.setName("");
		menu.setCreateDate(new Date());
		menu.setType("0");
		grabNovelMenuDao.save(menu);
	}
	
	@Override
	public List<GrabNovelMenu> loadMenu(String str,Long parentId) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(str);
		Elements es = doc.getElementsByTag("dd");
		List<Element> list = es.subList(0, 10);
		List<GrabNovelMenu> ll = new ArrayList<GrabNovelMenu>();
		list.forEach(e->{
			Elements e1 = e.getElementsByTag("a"); 
			if(e1 != null && e1.size()>0) {
				System.out.println(e1.html());
				GrabNovelMenu menu = new GrabNovelMenu();
				menu.setCreateDate(new Date());
				menu.setName(e1.get(0).html().trim());
				menu.setType("1");
				menu.setHref(url+e1.get(0).attr("href"));
				menu.setLoaded(0);
				menu.setParentId(parentId);
				menu.setLoadTimes(0);
				menu.setChannel(ChannelType.XIANGCUN.getValue());
				ll.add(menu);
			}
		});
		return ll;
	}
	
	@Override
	public void loadDoc() {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("loaded", 0);
		condition.addEqParam("channel", ChannelType.XIANGCUN.getValue());
		condition.addLteParam("loadTimes", 10);
		List<GrabNovelMenu> menuList = grabNovelMenuDao.queryColumnForList(condition);
		if(CollectionUtils.isNotEmpty(menuList)) {
			menuList.forEach(n->{
				Document doc = HtmlGrabUtil
		                .build(ChannelType.CUSTOM.getValue())
		                .getDoc(n.getHref());
				XiangCunLoadPerformer former = SpringContextUtil.getBean(XiangCunLoadPerformer.class);
				former.updateToSave(doc,n.getId());
			});
		}
	}
	
	public void updateToSave(Document doc,Long menuId) {
		GrabNovelDoc dc = new GrabNovelDoc();
		dc.setCreateDate(new Date());
		dc.setDoc(doc.html());
		dc.setMenuId(menuId);
		grabNovelDocDao.save(dc);
		
		GrabNovelMenu menu = new GrabNovelMenu();
		menu.setId(menuId);
		menu.setLoaded(1);
		grabNovelMenuDao.updateByPrimaryKeySelective(menu);
	}
}
