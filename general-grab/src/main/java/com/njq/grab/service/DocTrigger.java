package com.njq.grab.service;

import com.njq.common.model.po.BaseTitle;
import org.jsoup.nodes.Document;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public interface DocTrigger {
    void analysisTrigger(Document doc, BaseTitle baseTitle);
}
