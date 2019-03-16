package com.njq.grab.service;

import org.jsoup.nodes.Document;

/**
 * @author: nijiaqi
 * @date: 2019/3/16
 */
public interface HtmlAnalysisPerformer {

    String analysis(Document doc);
}
