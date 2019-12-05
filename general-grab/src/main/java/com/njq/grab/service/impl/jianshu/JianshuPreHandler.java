package com.njq.grab.service.impl.jianshu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
@Component("jianshuPreHandler")
public class JianshuPreHandler {
    private static final Logger logger = LoggerFactory.getLogger(JianshuPreHandler.class);

    public List<LeftMenu> loadAllMenu(String url) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.JIANSHU.getValue())
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .getDoc(url);
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.JIANSHU).getPageIndex();
        String href = doc.select(".qzhJKO").get(0).attr("href");
        if (StringUtils.isBlank(href)) {
            return null;
        }
        String menuUrl = grabUrl + href;
        Map<String, Boolean> urlMap = new HashMap<>();
        List<LeftMenu> list = new ArrayList<>();
        int page = 1;
        while (true) {
            logger.info("执行:" + (menuUrl+"?order_by=shared_at&page=" + page));
            Document docx = HtmlGrabUtil
                    .build(ChannelType.JIANSHU.getValue())
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .getDoc(menuUrl+"?order_by=shared_at&page=" + page);
            page++;
            if(!docx.getElementsByClass("author").isEmpty()) {
            	break;
            }
            if(page > 1000) {
            	logger.info("出现无限while循环");            	
            	break;
            }
            Elements es = docx.select(".content .title");
            if (es.isEmpty()) {
                break;
            }
            if (urlMap.containsKey(es.get(0).attr("href")) 
            		&&urlMap.containsKey(es.get(es.size() - 1).attr("href"))
            		&&urlMap.containsKey(es.get(es.size()/2).attr("href"))) {
                break;
            }
            
            es.parallelStream().forEach(n -> {
                LeftMenu menu = new LeftMenu();
                menu.setName(n.html());
                menu.setValue(grabUrl+n.attr("href"));
                urlMap.put(n.attr("href"), true);
                String[] urlspl = n.attr("href").split("/");
                menu.setDocId(urlspl[urlspl.length - 1]);
                list.add(menu);
            });
        }
        return list;
    }


}
