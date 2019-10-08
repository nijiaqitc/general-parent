package com.njq.grab.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.grab.HtmlGrabUtil;

@Service("customLoadService")
public class CustomLoadService {

	
	public void loadsgx() {
		for(int i=1 ;i<114;i++) {
			String url  = "https://www.juzimi.com/article/%E4%B8%89%E5%9B%BD%E6%9D%80?page="+i;
			Document doc = HtmlGrabUtil
					.build(ChannelType.CSDN.getValue())
					.getDoc(url);	
			System.out.println(doc.html());
			
			
			Elements elements = doc.getElementsByClass("views-field-phpcode");
			elements.forEach(n->{
				System.out.println(n.html());
				Elements es =n.getElementsByClass("xlistju");
				Element et = es.get(0);
				System.out.println(et.attr("href").split("/")[2]);
				System.out.println(et.html());
				Elements es1 = n.getElementsByClass("views-field-field-oriwriter-value");
				if(!es1.isEmpty()) {
					System.out.println("-----"+es1.get(0).html());					
				}
				
			});
			
		}
	}
}
