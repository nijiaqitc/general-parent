package com.njq.junit;

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
public class GrabByCookiesTests2 {


    @Test
    public void contextLoads() {
        String cookie = "login_sid_t=a82f66dceb1771fa5029a210c805304b; _s_tentry=passport.weibo.com; Apache=3624896187552.4956.1585660522953; SINAGLOBAL=3624896187552.4956.1585660522953; ULV=1585660522964:1:1:1:3624896187552.4956.1585660522953:; appkey=; webim_unReadCount=%7B%22time%22%3A1585660735505%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A1%2C%22msgbox%22%3A0%7D; cross_origin_proto=SSL; UOR=,,login.sina.com.cn; SSOLoginState=1585660798; SCF=AlrZvlk6uh6jVAq8jhMEhb7MqIBlpAGPt59QjMY0vPf1i8j-TNKsMIMSgNc2Szxej3af72j5A184rYtoY3mpWRs.; SUB=_2A25zhzMvDeRhGeFK6VcW8SfFyzmIHXVQ9SPnrDV8PUNbmtAfLVHCkW9NQ2gnUI5SiZvYWIurMS00aN46eozh78a9; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5QbSiQauvm6ga8IvaELiGs5JpX5K2hUgL.FoMXeo-NeK.4eh-2dJLoIp7LxKML1KBLBKnLxKqL1hnLBoMfSoq7S05E1K-R; SUHB=0pHhvnvlRBWmE9; ALF=1617196797; un=264432875@qq.com; wvr=6; WBStorage=42212210b087ca50|undefined";
        
        String urlPath = "https://s.weibo.com/weibo?q=%E7%99%BD%E9%AA%A8%E7%B2%BE&scope=ori&suball=1&timescope=custom:2020-03-05-0:2020-03-05-12&sudaref=s.weibo.com&Refer=SWeibo_box&page=2";
        loadDoc(urlPath, cookie);
        System.out.println("---------------");
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
            System.out.println("请求响应结果：" + sb.toString());
            
            
            Document doc =  Jsoup.parse(sb.toString());
            Elements ets = doc.select(".s-scroll li");
            System.out.println("----------------"+ets.size());
            
//            doc.getElementsByClass("s-scroll").get(0).getElementsByTag(tagName)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
