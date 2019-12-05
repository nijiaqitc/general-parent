package com.njq.junit;

import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByUrlTest {

    @Test
    public void loadDoc1() {
//        String urlPath = "https://www.jianshu.com/u/cb32a7edf150?order_by=shared_at&page=500";
        String urlPath = "https://www.jianshu.com/p/c64bf543f16a";
//        Document doc = HtmlGrabUtil
//                .build("swerer111")
//                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
//                .getDoc(urlPath);
//        Elements es = doc.select(".content .title");
//        for(int i=0;i<es.size();i++){
//            System.out.println(es.get(i).attr("href")+" --- "+ es.get(i).html());
//        }
//        System.out.println();

        Document doc = HtmlGrabUtil
                .build("swerer111")
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .getDoc(urlPath);
        String uuu = "https://www.jianshu.com"+doc.select(".qzhJKO").get(0).attr("href");
        Document doc2 = HtmlGrabUtil
                .build("swerer111")
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .getDoc(uuu);
        Elements es1 = doc2.select(".content .title");
        for(int i=0;i<es1.size();i++){
            System.out.println(es1.get(i).attr("href")+" --- "+ es1.get(i).html());
        }



//        /p/1a19446be126 --- 利用Spring的@Async异步处理改善web应用中耗时操作的用户体验
///p/12620b3c822d --- Java Web使用过滤器防止Xss攻击，解决Xss漏洞
//                /p/a6c08521b9b8 --- JAVA POI操作Excel
//                /p/6ae3eea65246 --- Kaptcha-验证码
//                /p/1eb8230e9da2 --- Activiti 流程变量
//                /p/0294c05a9ca1 --- Activiti 流程实例、任务的执行
//                /p/d316ed249a67 --- Activiti HelloWorld程序
//                /p/986049848fa4 --- Activiti5开发环境
//                /p/caddf04b7cc6 --- Activiti 5.x
//                /p/a707e3979201 --- 在Java中调用Python
//                /p/864d0bd80115 --- 数据量很大，分页查询很慢，怎么优化？
///p/8d60ce1587e1 --- k8s 基本使用（上）
///p/33321fe4b49f --- Java HttpClient发送HTTP 请求
///p/277619f946b0 --- PageHelper分页插件
//                /p/6594fd9e8dae --- 整理：SpringBoot入门教程(下)
//                /p/a97d753cb004 --- 整理：SpringBoot入门教程(上)
//                /p/ed67ea06077b --- 9.shiro整合springboot
//                /p/fa47e4fe533f --- 8.shiro整合SSM
//                /p/1333558b3d2d --- 7.自定义Realm实现授权
//                /p/1d94af53e5b0 --- 6.Shiro授权管理

//        String url= "https://www.jianshu.com"+doc.select(".qzhJKO").get(0).attr("href").toString();
//        System.out.println(url);
//        Document doc1 = HtmlGrabUtil
//                .build("aa")
//                .setFlag(false)
//                .randomSendIp()
//                .getDoc(url);
//        System.out.println(doc1.html());



//        https://www.jianshu.com/u/cb32a7edf150
//        https://www.jianshu.com/u/cb32a7edf150
    }


    @Test
    public void loadDoc2() {
        URL url;
        try {
            url = new URL("https://www.jianshu.com/u/cb32a7edf150");
            URLConnection conn = url.openConnection();
//            String cookie = "__yadk_uid=TEborPujqQqONvvAv3lQSyci68v42Jsg; locale=zh-CN; read_mode=day; default_font=font2; _m7e_session_core=120349be77c3f7e856ce61b083f94143; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575437456,1575437969,1575438066,1575438215; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22163e5006b689e3-0b903e50906504-47e1e39-1049088-163e5006b69920%22%2C%22%24device_id%22%3A%22163e5006b689e3-0b903e50906504-47e1e39-1049088-163e5006b69920%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_utm_source%22%3A%22recommendation%22%2C%22%24latest_utm_medium%22%3A%22seo_notes%22%2C%22%24latest_utm_campaign%22%3A%22maleskine%22%2C%22%24latest_utm_content%22%3A%22note%22%2C%22%24latest_referrer_host%22%3A%22%22%7D%7D; signin_redirect=https%3A%2F%2Fwww.jianshu.com%2F; Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575438346";
//            if(cookie != null){
//                conn.setRequestProperty("Cookie", cookie);
//            }
//            conn.setRequestProperty("User-Agent:", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
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



    private RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10 * 1000).setConnectTimeout(10 * 1000)
            .setSocketTimeout(10 * 1000).build();
    private CookieStore store;
    @Test
    public void loadDoc3(){
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        CloseableHttpResponse response = null;
        String url = "https://www.jianshu.com/u/cb32a7edf150";
        String var6;
        try {
            HttpGet getHttp = new HttpGet(url);
            getHttp.setConfig(this.requestConfig);
            getHttp.setHeader(SendConstants.USER_AGENT_NAME, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            getHttp.addHeader(SendConstants.CONTENT_TYPE_NAME, SendConstants.CONTENT_TYPE_VALUE);
//            getHttp.addHeader("x-requested-with", "XMLHttpRequest");



            response = httpClient.execute(getHttp);
            String charset = response.getEntity().getContentType().getValue().toUpperCase();
            if (!charset.contains("UTF-8")) {
                if (charset.contains("GB2312")) {
                    var6 = EntityUtils.toString(response.getEntity(), "GB2312");
                }

                if (charset.contains("ISO-8859-1")) {
                    var6 = EntityUtils.toString(response.getEntity(), "ISO-8859-1");
                }

                var6 = EntityUtils.toString(response.getEntity(), "GBK");
            }

            var6 = EntityUtils.toString(response.getEntity(), "UTF-8");

            System.out.println("-----------:"+var6);
        } catch (Exception var19) {
            System.out.println(var19);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (response != null) {
                    ((CloseableHttpResponse)response).close();
                }
            } catch (Exception var18) {
            }

        }

    }

//    private void setGetHeader(HttpGet getHttp) {
//
//        getHttp.addHeader(SendConstants.HEAD_IP_1, moIp);
//        getHttp.addHeader(SendConstants.HEAD_IP_2, moIp);
//        getHttp.addHeader(SendConstants.HEAD_IP_3, moIp);
//        getHttp.addHeader(SendConstants.HEAD_IP_4, moIp);
//        getHttp.addHeader(SendConstants.HEAD_IP_5, moIp);
//    }

}
