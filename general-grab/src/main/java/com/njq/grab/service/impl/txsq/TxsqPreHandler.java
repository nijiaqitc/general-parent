package com.njq.grab.service.impl.txsq;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
@Component("txsqPreHandler")
public class TxsqPreHandler {
    private static final Logger logger = LoggerFactory.getLogger(TxsqPreHandler.class);

    public List<LeftMenu> loadAllMenu(String url) {
        Txmodel md = new Txmodel();
        md.setAction("FetchColumnArticles");
        Payload payload = new Payload();
        String[] uriArray = url.split("\\/");
        int pageIndex = 1;
        List<LeftMenu> list = new ArrayList<>();
        payload.setColumnId(Integer.parseInt(uriArray[uriArray.length - 1]));
        payload.setTagId(0);
        payload.setPageSize(20);
        while (true) {
            payload.setPageNumber(pageIndex);
            md.setPayload(payload);
            String docStr = HtmlGrabUtil
                    .build("swerer222")
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .sendPostFromUrlJson(url, JSON.toJSONString(md));
            TxRespone resp = JSON.parseObject(docStr, TxRespone.class);
            if (CollectionUtils.isNotEmpty(resp.getData().getList())) {
                if ("0".equals(resp.getCode())) {
                    resp.getData().getList().forEach(n -> {
                        LeftMenu menu = new LeftMenu();
                        menu.setName(n.getTitle());
                        menu.setValue(n.getId().toString());
                        menu.setDocId(n.getId().toString());
                        list.add(menu);
                    });
                }
            } else {
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
