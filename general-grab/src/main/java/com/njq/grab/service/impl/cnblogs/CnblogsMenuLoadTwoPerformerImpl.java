package com.njq.grab.service.impl.cnblogs;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;

@Component("cnblogsLoadTwoPerformer")
public class CnblogsMenuLoadTwoPerformerImpl implements CnblogsMenuLoadPerformer{

	@Override
	public void loadMenu(Element element, Long typeId) {
		Elements elements = element.getElementsByTag("a");
        elements.parallelStream().forEach(n -> {
            Document dd = HtmlGrabUtil
                    .build(ChannelType.CNBLOGS.getValue())
                    .getDoc(n.attr("href"));
            Elements ss = dd.getElementsByTag("h5"); 
            if(ss.isEmpty()) {
            	ss = dd.getElementsByClass("entrylistItemTitle");
            }
            ss.forEach(f -> {
            	Elements titlea = f.getElementsByTag("a");
            	Element a = titlea.get(0); 
                LeftMenu menu = new LeftMenu();
                menu.setName(a.html());
                String href = a.attr("href");
                menu.setValue(href);
                String[] urlspl = href.split("/");
                menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
                impl.saveTitle(menu, typeId);
            });
        });
	}

}
