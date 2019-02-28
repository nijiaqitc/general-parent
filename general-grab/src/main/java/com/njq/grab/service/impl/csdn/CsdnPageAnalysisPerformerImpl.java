package com.njq.grab.service.impl.csdn;

import com.alibaba.fastjson.JSON;
import com.njq.common.base.config.SpringContextUtil;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.PageAnalysisPerformer;
import com.njq.grab.service.impl.cnblogs.CnblogsPageAnalysisPerformerImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/2/20
 */
@Component("csdnPageAnalysisPerformer")
public class CsdnPageAnalysisPerformerImpl implements PageAnalysisPerformer {

    @Override
    public void loadPage(Long docId) {

    }

    @Override
    public void login() {

    }

    @Override
    public void loadMenu(String url, Long typeId) {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CSDN.getValue())
                .getDoc(url);
        Element element = doc.getElementById("asideCategory");
        if(element == null){
            element = doc.getElementById("asideArchive");
        }
        Elements elements =  element.getElementsByTag("a");
        elements.forEach(n -> {
            Elements spanElements = n.getElementsByTag("span");
            String type = spanElements.get(0).html();
            String num = spanElements.get(1).html();
            for (int i = 0 ;i<10;i++){
                Elements ss = HtmlGrabUtil
                        .build(ChannelType.CSDN.getValue())
                        .getDoc(n.attr("href")+"/"+i)
                        .getElementsByTag("h4");
                ss.forEach(m->{
                    Element a =m.getElementsByTag("a").get(0);
                    LeftMenu menu = new LeftMenu();
                    menu.setName(a.html());
                    String href = a.attr("href");
                    menu.setValue(href);
                    String[] urlspl = href.split("/");
                    menu.setDocId(urlspl[urlspl.length - 1].split("\\.")[0]);
                    System.out.println(JSON.toJSONString(menu));
//                    CsdnPageAnalysisPerformerImpl impl = SpringContextUtil.getBean(CsdnPageAnalysisPerformerImpl.class);
    //                impl.saveTitle(menu, typeId, n.html().split("\\(")[0]);
                });

            }





        });
    }

    @Override
    public Long grabAndSave(String url, BaseTitle baseTitle) {
        return null;
    }

    @Override
    public Long saveLoadingDoc(String url, BaseTitle baseTitle) {
        return null;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        return null;
    }

    @Override
    public Long updateDoc(String doc, String title, Long id) {
        return null;
    }

    @Override
    public String loginAndAnalysisPage(String url, Long typeId) {
        return null;
    }

    @Override
    public String analysisPage(String url, Long typeId) {
        return null;
    }
}
