package com.njq.junit;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.string.StringUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByCookiesTests1 {


    @Test
    public void contextLoads() {
        Document doc = HtmlGrabUtil
                .build(ChannelType.CSDN.getValue())
                .getDoc("https://blog.csdn.net/ieflex/article/details/86768066");
        System.out.println(doc.getElementsByTag("script").html());
        evalJs(doc.getElementsByTag("script").html());
    }

    private void evalJs(String html){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
            engine.eval("function run(){" +

                    html +
                    "}");
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("run",null));
            }
        }catch(Exception e){
            e.printStackTrace();
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
