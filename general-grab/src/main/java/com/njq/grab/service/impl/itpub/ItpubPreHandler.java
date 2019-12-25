package com.njq.grab.service.impl.itpub;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.string.StringUtil2;

@Component("itpubPreHandler")
public class ItpubPreHandler {

	public List<LeftMenu> loadAllMenu(String url) {
		List<LeftMenu> list = new ArrayList<>();
		int pageIndex = 1;
		while(true) {
			Document doc = HtmlGrabUtil
					.build(ChannelType.ITPUB.getValue())
					.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
					.getDoc(url+"/"+pageIndex);
			Elements titles = doc.getElementsByClass("title");
			if(titles.isEmpty()) {
				break;
			}
			titles.parallelStream().forEach(n->{
				String href = n.parent().attr("href");
	            String[] strs = href.split("/");
				LeftMenu menu = new LeftMenu();
				menu.setName(n.html());
				menu.setValue(href);
				String docId = StringUtil2.isEmpty(strs[strs.length-1])?strs[strs.length-2]:strs[strs.length-1];
				menu.setDocId(docId);
				list.add(menu);				
			});
			if(titles.size()<20) {
				break;
			}
			pageIndex++;
			if (pageIndex > 1000) {
				break;
			}
		}
		return list;
	}
}
