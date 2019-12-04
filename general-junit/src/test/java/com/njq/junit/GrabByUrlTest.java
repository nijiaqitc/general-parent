package com.njq.junit;

import com.njq.common.util.grab.HtmlGrabUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByUrlTest {

    @Test
    public void loadDoc1() {
        String urlPath = "https://www.jianshu.com/p/040551605dda";
        Document doc = HtmlGrabUtil
                .build("aa")
                .setFlag(false)
                .randomSendIp()
                .getDoc(urlPath);
        System.out.println(doc.html());
    }


    @Test
    public void loadDoc2() {
        URL url;
        try {
            url = new URL("https://www.jianshu.com/p/040551605dda");
            URLConnection conn = url.openConnection();
//            if(cookie != null){
//                conn.setRequestProperty("Cookie", cookie);
//            }
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("请求响应结果：" + sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
