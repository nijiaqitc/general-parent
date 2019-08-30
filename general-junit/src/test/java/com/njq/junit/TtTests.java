package com.njq.junit;

import com.njq.common.util.grab.HtmlGrabUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: nijiaqi
 * @date: 2019/8/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TtTests {
    @Test
    public void contextLoads() {
        String url = "tttff9.com";
        if (url.substring(5, 6).matches("\\d+")) {
            url = url.substring(0, 5);
        }
        if (url.substring(0, 1).matches("\\d+")) {
            url = url.substring(1, 6);
        }
        System.out.println(url);
        for (int i = 0; i < 10; i++) {
            ggg(i + url + ".com");
        }
        for (int i = 0; i < 10; i++) {
            ggg(i + url + ".com");
        }


    }

    private void ggg(String url) {
        for (int i = 0; i < 10; i++) {
            url = this.getUrl("https://" + url + "/video/detail/7992");
            this.completeNums(url);
        }
    }

    private String getUrl(String url) {
        System.out.println("访问：" + url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document doc = HtmlGrabUtil.build("aaa").getDoc(url);
        Elements etsa = doc.getElementsByTag("p");
        String str = etsa.get(0).html();
        System.out.println("---------" + str);
        str = str.replace("帮助: 在上面输入", "");
        str = str.replace(",点击'提交检查'按钮,即可通过检查", "");
        System.out.println(str.trim());
        return str.trim();
    }

    private void completeNums(String url) {
        Document doc = HtmlGrabUtil.build("aaa").getDoc("https://" + url + "/user/profile#header");
        Elements ets = doc.getElementById("body").getElementsByTag("h2");
//        System.out.println(doc);
        System.out.println(ets.get(0).getElementsByTag("span").get(0).html());
        System.out.println(ets.get(1).getElementsByTag("span").get(0).html());

    }
}
