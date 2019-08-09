package com.njq.grab.service.impl.novel;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.po.GrabNovelDoc;
import com.njq.common.model.po.GrabNovelMenu;
import com.njq.common.util.grab.HtmlGrabUtil;

public class AbstractLoadPerformer implements NovelLoadPerformer{
	private static final Logger flogger = LoggerFactory.getLogger(AbstractLoadPerformer.class);
	@Resource
    private DaoCommon<GrabNovelMenu> grabNovelMenuDao;
	@Resource
	private DaoCommon<GrabNovelDoc> grabNovelDocDao;
	
	@Override
	public String search(String str) {
		return null;
	}

	@Override
	public void loadDetail() {
		
	}

	@Override
	public List<GrabNovelMenu> loadMenu(String str, Long parentId) {
		return null;
	}

	@Override
	public void loadDoc() {
	}

	public Document grabUrl(String str) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(str);
		return doc;
	}
	
	
	public void updateToSave(Element et,Long menuId,String bookName) {
		GrabNovelMenu menu = grabNovelMenuDao.queryTById(menuId);
		if(menu.getLoadTimes()>10) {
			return;
		}
		if(et != null) {
			ConditionsCommon condition = new ConditionsCommon();
			condition.addEqParam("id", menuId);
			condition.addsetObjectParam("loaded", 1);
			condition.addsetObjectParam("loadTimes", menu.getLoadTimes()+1);
			condition.addNotEqParam("loaded", 1);
			int num = grabNovelMenuDao.update(condition);			
			if(num == 0) {
				flogger.info("并发修改失败,menuId："+menuId);
				return;
			}
			GrabNovelDoc dc = new GrabNovelDoc();
			dc.setCreateDate(new Date());
			dc.setDoc(dealHtml(et.html(),bookName));
			dc.setMenuId(menuId);
			grabNovelDocDao.save(dc);
			
		}else {
			ConditionsCommon condition = new ConditionsCommon();
			condition.addEqParam("id", menuId);
			condition.addsetObjectParam("loadTimes", menu.getLoadTimes()+1);
			grabNovelMenuDao.update(condition);
		}
	}
	
	
	public String dealHtml(String str,String bookName) {
		return "";
	}
}
