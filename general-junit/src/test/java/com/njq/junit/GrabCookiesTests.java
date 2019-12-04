package com.njq.junit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.njq.common.util.grab.HtmlGrabUtil;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabCookiesTests {


    @Test
    public void contextLoads() {
//        BasicClientCookie bcookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
//        bcookie.setDomain(cookie.getDomain());
//        bcookie.setExpiryDate(cookie.getExpiry());
//        bcookie.setPath(cookie.getPath());
//        cookiestore.addCookie(bcookie);


    	String urlPath = "https://blog.csdn.net/qq895627041/article/details/82109576";
    	Document doc = HtmlGrabUtil
                .build("bbb")
                .setFlag(false)
                .randomSendIp()
                .getDoc(urlPath);
        String scr = doc.getElementsByTag("script").get(0).html();
        System.out.println(scr.substring(10, 50));
        scr = scr.substring(10, 50);


//        loadDoc(urlPath, null);

//    	System.out.println(doc.html());


//    	System.out.println(HtmlGrabUtil.build("bbb").getCookieStr());



        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
            String jsName = "D:\\testjs.js";
            FileReader fileReader = new FileReader(jsName );
            engine.eval(fileReader);
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                String cookieStr = in.invokeFunction("l",scr).toString();
                System.out.println(cookieStr);
                Document doc1 = HtmlGrabUtil
                        .build("bbb")
                        .getDoc(urlPath, "acw_sc__v2", cookieStr.trim(),".blog.csdn.net");
                System.out.println("最后获取的数据：-------------------");
                System.out.println(doc1.html());
//    	        loadDoc(urlPath, "acw_sc__v2="+cookieStr);
            }
        }catch(Exception e){
            e.printStackTrace();
        }





//        loadDoc(urlPath, "BAIDU_SSP_lcr=https://www.baidu.com/link?url=8E8tyNZmQMHTIB1xB4AgbOcZ3dx4iRWYoDjGJrFHxdJoCtWbdZwteseBF3swQQoA4p-kmTgZUVt5715NJ_usfBW_NLNkiOROh-Khl-O4hMK&wd=&eqid=f1303290004bb2ea000000045d8f7fd4; TINGYUN_DATA=%7B%22id%22%3A%22-sf2Cni530g%23HL5wvli0FZI%22%2C%22n%22%3A%22WebAction%2FCI%2Farticle%252Fdetails%22%2C%22tid%22%3A%22f963058d06b026%22%2C%22q%22%3A0%2C%22a%22%3A132%7D; uuid_tt_dd=10_36983602220-1550400771238-129064; ADHOC_MEMBERSHIP_CLIENT_ID1.0=1311dd99-2c40-7e23-0a79-9e04c12cfe8b; __yadk_uid=HUaselMWMF2T3XFYYChpdYwKZMOU86ev; _ga=GA1.2.194555175.1553309581; dc_session_id=10_1554005199574.194851; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_36983602220-1550400771238-129064!1788*1*PC_VC; smidV2=20190522231451d6b2822fc571346f1d0745f95844308200078c9948eabaa60; UM_distinctid=16af994b4345c7-0180c0cdc55911-366e7e03-13c680-16af994b435624; acw_tc=2760827015683379965938332ebafbb2d69cf965b2065dcf1ae51326685a93; Hm_lvt_e5ef47b9f471504959267fd614d579cd=1569682673; Hm_lpvt_e5ef47b9f471504959267fd614d579cd=1569682673; Hm_ct_e5ef47b9f471504959267fd614d579cd=6525*1*10_36983602220-1550400771238-129064; aliyun_webUmidToken=T7D2C4D8332AF7DB7E7CD655AC67C9A6227D6B294F864F8291730F58866; SESSION=6f93086b-a81b-4fe1-b16e-cb024630ebc1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1569685806,1569685828,1569685937,1569685969; firstDie=1; acw_sc__v2=5d91d11c248f59a2b69a4e746fc5f0137b47dce9; dc_tos=pyn28u; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1569837343; c-login-auto=18");
//    	loadDoc("https://blog.csdn.net/fm345689/article/details/100559159", "acw_sc__v2=5d9d716916b4ce886da4c7dd46d74570e8b46035");

    	
    }


    @Test
    public void contextLoads1() {
        String urlPath = "https://blog.csdn.net/qq895627041/article/details/82109576";
        String key = "5d9d7f1dc76fb3077139333c1e9935f354f96ced";
        Document doc = HtmlGrabUtil
                .build("bbb")
                .setFlag(false)
                .setSendIp("116.233.79.107")
                .setCookie("acw_sc__v2", key ,".blog.csdn.net")
                .getDoc(urlPath);
        System.out.println("获取返回的结果1：");
        System.out.println("!!!"+HtmlGrabUtil
                .build("bbb")
                .getCookieStr());
        System.out.println(doc.html());
        System.out.println("获取返回的结果2：");
        loadDoc(urlPath, "acw_sc__v2="+key);
    }



    private String loadDoc(String urlPath, String cookie) {
        URL url;
        try {
            url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            if(cookie != null){
                conn.setRequestProperty("Cookie", cookie);
            }
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("请求响应结果：" + sb);
            return sb.toString();
//            jixi(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
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
