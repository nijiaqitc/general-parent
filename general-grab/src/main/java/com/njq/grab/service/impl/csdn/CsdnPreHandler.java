package com.njq.grab.service.impl.csdn;

import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.GrabUrlInfoFactory;

@Component
public class CsdnPreHandler {
	
	public Document preLoad(String url) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CSDN.getValue())
                .setFlag(false)
                .randomSendIp()
                .getDoc(url);
		String scr = doc.getElementsByTag("script").get(0).html();
		scr = scr.substring(10, 50);
		
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
            String jsName = GrabUrlInfoFactory.getDecodeJsKey();
            FileReader fileReader = new FileReader(jsName );
            engine.eval(fileReader);
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                String cookieStr = in.invokeFunction("l",scr).toString();
                Document doc1 = HtmlGrabUtil
                        .build(ChannelType.CSDN.getValue())
                        .getDoc(url, "acw_sc__v2", cookieStr.trim(),".blog.csdn.net");
                return doc1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
		return null;
	}
}
