package com.njq.junit;

import com.njq.common.util.string.StringUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByCookiesTests {


    @Test
    public void contextLoads() {
        String cookie = "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221690eff5d21a94-0662417dd975f-47e1e39-2359296-1690eff5d226d8%22%2C%22%24device_id%22%3A%221690eff5d21a94-0662417dd975f-47e1e39-2359296-1690eff5d226d8%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; gr_user_id=b9f5d9c3-9392-4c26-8ad1-bf5b8a73fb2c; _gitlab_session=a0ea93a703b28195830214e6a3c3dd10";
        String urlPath = "http://gitlab.yonghuivip.com/?non_archived=true&page={0}&sort=latest_activity_desc";
        for (int i = 0; i < 41; i++) {
            System.out.println("-------------第" + (i + 1) + "页");
            loadDoc(StringUtil2.format(urlPath, i + 1), cookie);
        }
    }

    private void loadDoc(String urlPath, String cookie) {
        URL url;
        try {
            url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", cookie);
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            System.out.println("请求响应结果：" + sb);
            jixi(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jixi(String str) {
        Document doc = Jsoup.parse(str);
        Elements ets = doc.getElementsByClass("project-details");
        ets.forEach(n -> {
            Elements ets1 = n.getElementsByClass("project-name");
            if (!ets1.isEmpty()) {
                System.out.println(ets1.get(0).html());
            }
            Elements ets2 = n.getElementsByClass("description");
            if (!ets2.isEmpty()) {
                System.out.println(ets2.get(0).getElementsByTag("p").get(0).html());
            }
        });
    }
}
