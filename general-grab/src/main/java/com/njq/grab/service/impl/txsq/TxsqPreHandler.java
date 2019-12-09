package com.njq.grab.service.impl.txsq;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;

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
        String menuUrl = "https://cloud.tencent.com/developer/services/ajax/column/article";
        while (true) {
            payload.setPageNumber(pageIndex);
            md.setPayload(payload);
            String docStr = HtmlGrabUtil
                    .build(ChannelType.TXSQ.getValue())
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .sendPostFromUrlJson(menuUrl, JSON.toJSONString(md));
            TxRespone resp = JSON.parseObject(docStr, TxRespone.class);
            if (CollectionUtils.isNotEmpty(resp.getData().getList())) {
                if (resp.getCode() == 0) {
                    resp.getData().getList().forEach(n -> {
                        LeftMenu menu = new LeftMenu();
                        menu.setName(n.getTitle());
                        menu.setValue("https://cloud.tencent.com/developer/article/"+n.getId().toString());
                        menu.setDocId(n.getId().toString());
                        list.add(menu);
                    });
                    if(resp.getData().getList().size()<20) {
                    	break;
                    }
                }
            } else {
                break;
            }
            pageIndex++;
            if (pageIndex > 1000) {
                break;
            }
        }
        System.out.println("获取到的list:"+JSON.toJSONString(list));
        return list;
    }
}
