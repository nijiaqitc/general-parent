package com.njq.grab.service.impl.jianshu;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            menuUrl += "?order_by=shared_at&page=" + (page++);
            logger.info("执行:" + menuUrl);
            Document docx = HtmlGrabUtil
                    .build(ChannelType.JIANSHU.getValue())
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .getDoc(menuUrl);
            Elements es = docx.select(".content .title");
            if (es.isEmpty()) {
                break;
            }
            if (urlMap.containsKey(es.get(0).attr("href")) && urlMap.containsKey(es.get(es.size() - 1).attr("href"))) {
                break;
            }
            es.parallelStream().forEach(n -> {
                LeftMenu menu = new LeftMenu();
                menu.setName(n.html());
                menu.setValue(n.attr("href"));
                String[] urlspl = n.attr("href").split("/");
                menu.setDocId(urlspl[urlspl.length - 1]);
                list.add(menu);
            });
        }
        return list;
    }


}
