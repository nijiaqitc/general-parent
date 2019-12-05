package com.njq.grab.service.impl.cnblogs;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;

@Component("cnblogsLoadOnePerformer")
public class CnblogsMenuLoadOnePerformerImpl implements CnblogsMenuLoadPerformer{

	@Override
	public void loadMenu(Element element, Long typeId) {
		Elements elements = element.getElementsByTag("a");
        elements.parallelStream().forEach(n -> {
            Elements ss = HtmlGrabUtil
                    .build(ChannelType.CNBLOGS.getValue())
                    .getDoc(n.attr("href"))
                    .getElementsByClass("entrylistItemTitle");
            ss.forEach(f -> {
                LeftMenu menu = new LeftMenu();
                menu.setName(f.html());
                String href = f.attr("href");
                menu.setValue(href);
                String[] urlspl = href.split("/");
                menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                CnblogsPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CnblogsPageAnalysisPerformerImpl.class);
                try {
                	impl.saveTitle(menu, typeId);					
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
        });
	}

}
