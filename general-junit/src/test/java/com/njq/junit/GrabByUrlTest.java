package com.njq.junit;

import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;
import com.njq.common.util.grab.UrlChangeUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
import java.io.Serializable;
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
//        String urlPath = "http://blog.itpub.net/29715045/viewspace-2653621";
//        Document doc = HtmlGrabUtil
//                .build("swerer111")
//                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
//                .getDoc(urlPath);
//        Elements tips = doc.select(".mess a");
//        System.out.println(tips.first());

        try {
            UrlChangeUtil.downLoad("http://img.blog.itpub.net/blog/2019/08/12/3d4b90f0f6c56f99.png?x-oss-process=style/bb", "D:\\test\\aaaa.png", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(doc.html());
//        Elements titles = doc.getElementsByClass("title");
//        if(titles.isEmpty()){
//            System.out.println("空空空");
//        }
//        titles.forEach(n -> {
//            String href = n.parent().attr("href");
//            String[] strs = href.split("/");
//
//            System.out.println(href+"---"+n.html());
//            System.out.println(strs[strs.length-1]+"  "+strs[strs.length-2]);
//        });

//        Txmodel md = new Txmodel();
//        md.setAction("FetchColumnArticles");
//        Payload payload = new Payload();
//        payload.setColumnId(2205);
//        payload.setPageNumber(1);
//        payload.setPageSize(20);
//        payload.setTagId(0);
//        md.setPayload(payload);
//
//        String doc = HtmlGrabUtil
//                .build("swerer222")
//                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
//                .sendPostFromUrlJson("https://cloud.tencent.com/developer/services/ajax/column/article", JSON.toJSONString(md));
//        HashMap<String,String> hashMap = JSON.parseObject(doc, HashMap.class);
//        System.out.println(hashMap.get("data"));


//        String doc =this.sendPostFromUrlJson1("https://cloud.tencent.com/developer/services/ajax/column/article", JSON.toJSONString(md));

//        System.out.println(JSON.toJSONString(md));
//        System.out.println(doc);

//        md
//        action: "FetchColumnArticles"
//        payload: {columnId: 2205, tagId: 0, pageNumber: 2, pageSize: 20}
//        columnId: 2205
//        pageNumber: 2
//        pageSize: 20
//        tagId: 0


//        Elements est = doc.select(".com-article-panel-title a");
//        for (int i = 0; i < est.size(); i++) {
//            System.out.println(est.get(i).attr("href") + "   " + est.get(i).html());
//        }

//        String uuu = "https://www.jianshu.com"+doc.select(".qzhJKO").get(0).attr("href");
//        Document doc2 = HtmlGrabUtil
//                .build("swerer111")
//                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
//                .getDoc(uuu);
//        Elements es1 = doc2.select(".content .title");
//        for(int i=0;i<es1.size();i++){
//            System.out.println(es1.get(i).attr("href")+" --- "+ es1.get(i).html());
//        }

    }


    public String sendPostFromUrlJson1(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(this.store).build();
        CloseableHttpResponse response = null;
        try {
            StringEntity formEntity = new StringEntity(jsonStr, "UTF-8");
            HttpPost postHttp = new HttpPost(url);
            postHttp.setConfig(this.requestConfig);
//            this.setPostHeader(postHttp);
            postHttp.addHeader("Content-Type", "application/json; charset=UTF-8");
            postHttp.setEntity(formEntity);
            response = httpClient.execute(postHttp);
            String var7 = EntityUtils.toString(response.getEntity(), "UTF-8");
            return var7;
        } catch (Exception var17) {
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (response != null) {
                    ((CloseableHttpResponse) response).close();
                }
            } catch (Exception var16) {
            }

        }

        return null;
    }


    public class Txmodel implements Serializable {
        private String action;
        private Payload payload;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Payload getPayload() {
            return payload;
        }

        public void setPayload(Payload payload) {
            this.payload = payload;
        }
    }

    public class Payload implements Serializable {
        private int columnId;
        private int pageNumber;
        private int pageSize;
        private int tagId;

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }
    }

    @Test
    public void loadDoc2() {
        URL url;
        try {
            url = new URL("https://cloud.tencent.com/developer/column/80001");
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
    public void loadDoc3() {
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

            System.out.println("-----------:" + var6);
        } catch (Exception var19) {
            System.out.println(var19);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (response != null) {
                    ((CloseableHttpResponse) response).close();
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

    public static void main(String[] args) {
        System.out.println("0".equals(0));
    }
}
