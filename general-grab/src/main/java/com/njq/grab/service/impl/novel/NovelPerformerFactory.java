package com.njq.grab.service.impl.novel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.common.enumreg.channel.ChannelType;

@Component
public class NovelPerformerFactory {

	private final NovelLoadPerformer qishuLoadPerformer;
	private final NovelLoadPerformer xiangCunChannelPerformer;
	private final NovelLoadPerformer bbiqugeLoadPerformer;
	private Map<String, NovelLoadPerformer> map;
	
	@Autowired
	public NovelPerformerFactory(NovelLoadPerformer qishuLoadPerformer, 
			NovelLoadPerformer xiangCunChannelPerformer, NovelLoadPerformer bbiqugeLoadPerformer) {
		this.qishuLoadPerformer = qishuLoadPerformer;
		this.xiangCunChannelPerformer = xiangCunChannelPerformer;
		this.bbiqugeLoadPerformer = bbiqugeLoadPerformer;
		map = new HashMap<>();
//		map.put(ChannelType.QI_SHU.getValue(), qishuLoadPerformer);
//		map.put(ChannelType.XIANGCUN.getValue(), xiangCunChannelPerformer);
		map.put(ChannelType.BBIQUGE.getValue(), bbiqugeLoadPerformer);
	}


	public NovelLoadPerformer getPerformer(String key) {
		return map.get(key);
	}
	
	
	public Map<String, NovelLoadPerformer> getMap(){
		return this.map;
	}
	
}
