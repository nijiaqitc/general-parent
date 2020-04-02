package com.njq.junit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.njq.common.util.grab.GenerateRandomIpUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.SendConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrabByCookiesTests3 {


    @Test
    public void contextLoads() {
        String cookie = "login_sid_t=a82f66dceb1771fa5029a210c805304b; _s_tentry=passport.weibo.com; Apache=3624896187552.4956.1585660522953; SINAGLOBAL=3624896187552.4956.1585660522953; ULV=1585660522964:1:1:1:3624896187552.4956.1585660522953:; appkey=; cross_origin_proto=SSL; SSOLoginState=1585663102; un=264432875@qq.com; wvr=6; webim_unReadCount=%7B%22time%22%3A1585748559383%2C%22dm_pub_total%22%3A0%2C%22chat_group_client%22%3A0%2C%22allcountNum%22%3A7%2C%22msgbox%22%3A0%7D; SCF=AlrZvlk6uh6jVAq8jhMEhb7MqIBlpAGPt59QjMY0vPf1ptr9Z0uQ3Ec_IkmKzVW0LPzzyGV9DY581jdzhWLuuoY.; SUB=_2A25zgO5oDeRhGeFK6VcW8SfFyzmIHXVQ9FigrDV8PUNbmtAfLXX-kW9NQ2gnUFzfysSPEkv-K-rqOnPWwum8IFnr; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5QbSiQauvm6ga8IvaELiGs5JpX5KMhUgL.FoMXeo-NeK.4eh-2dJLoIp7LxKML1KBLBKnLxKqL1hnLBoMfSoq7S05E1K-R; SUHB=05iqcTm3I-2qOH; ALF=1617285558; WBStorage=42212210b087ca50|undefined; UOR=,,login.sina.com.cn";
        
        
//        String time = sdateTime.toString("yyyy-MM-dd");
//        String urlPath = "https://s.weibo.com/weibo?q=%E7%99%BD%E9%AA%A8%E7%B2%BE&scope=ori&suball=1&timescope=custom:2020-03-05-0:2020-03-05-12&sudaref=s.weibo.com&Refer=SWeibo_box&page=2";
        DateTime sdateTime = DateTime.parse("2020-04-01");
        DateTime edateTime = DateTime.parse("2020-04-01");
        
//        Days d = Days.daysBetween(sdateTime, edateTime);
//        System.out.println(d.getDays());
        
        String stime = null;
        String etime = null;
        String url = "";
        for(int i=0;i<1;i++) {
        	sdateTime = edateTime;
        	edateTime = sdateTime.plusDays(1);
        	stime = sdateTime.toString("yyyy-MM-dd");
        	etime = edateTime.toString("yyyy-MM-dd");
        	System.out.println("时间"+stime+":");
        	for(int j=0 ;j<23;j++) {
//        		url  = "https://s.weibo.com/weibo?q=%E6%B8%A3%E7%94%B7&scope=ori&suball=1&timescope=custom:"+stime+"-"+j+":"+stime+"-"+(j+1) +"&Refer=g&page=11";
        		url = "http://www.njqityun.com/tools/inx/encryptlock";
        		System.out.println(url);
//        		loadDoc(url, cookie);        		
        		
        		
        		Document doc = HtmlGrabUtil
                        .build("bbb")
                        .getDoc(url);
        		
        	}
//        	url  = "https://s.weibo.com/weibo?q=%E6%B8%A3%E7%94%B7&scope=ori&suball=1&timescope=custom:"+stime+"-"+23+":"+etime+"-" + 0 +"&Refer=g&page=1";
//        	loadDoc(url, cookie);
        }
        
        
    }

    private void loadDoc(String urlPath, String cookie) {
        URL url;
        try {
            url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", cookie);
            String moIp = GenerateRandomIpUtil.getRandomIp();
            conn.setRequestProperty(SendConstants.HEAD_IP_1, moIp);
            conn.setRequestProperty(SendConstants.HEAD_IP_2, moIp);
            conn.setRequestProperty(SendConstants.HEAD_IP_3, moIp);
            conn.setRequestProperty(SendConstants.HEAD_IP_4, moIp);
            conn.setRequestProperty(SendConstants.HEAD_IP_5, moIp);
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            System.out.println("请求响应结果：" + sb.toString());
            
            
            Document doc =  Jsoup.parse(sb.toString());
            Elements ets = doc.select(".s-scroll li");
            
            if(ets.size() == 0) {
            	if(doc.getElementsByClass("card-no-result").size()==0) {
            		System.out.println("被屏蔽");
            	}else {
            		System.out.println(0);
            	}
            }else {
            	System.out.println(ets.size());            	
            }
            
            Thread.sleep(200);
//            doc.getElementsByClass("s-scroll").get(0).getElementsByTag(tagName)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
