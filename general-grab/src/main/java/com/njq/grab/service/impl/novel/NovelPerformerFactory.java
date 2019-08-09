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
	
	private final NovelConsultPerformer qidianConsultPerformer;
	private Map<String,NovelConsultPerformer> consultMap;
	@Autowired
	public NovelPerformerFactory(NovelLoadPerformer qishuLoadPerformer, 
			NovelLoadPerformer xiangCunChannelPerformer, NovelLoadPerformer bbiqugeLoadPerformer,NovelConsultPerformer qidianConsultPerformer) {
		this.qishuLoadPerformer = qishuLoadPerformer;
		this.xiangCunChannelPerformer = xiangCunChannelPerformer;
		this.bbiqugeLoadPerformer = bbiqugeLoadPerformer;
		map = new HashMap<>();
//		map.put(ChannelType.QI_SHU.getValue(), qishuLoadPerformer);
//		map.put(ChannelType.XIANGCUN.getValue(), xiangCunChannelPerformer);
		map.put(ChannelType.BBIQUGE.getValue(), bbiqugeLoadPerformer);
		consultMap = new HashMap<>();
		this.qidianConsultPerformer = qidianConsultPerformer;
		consultMap.put("", qidianConsultPerformer);
	}


	public NovelLoadPerformer getPerformer(String key) {
		return map.get(key);
	}
	
	
	public Map<String, NovelLoadPerformer> getMap(){
		return this.map;
	}
	
	
	public NovelConsultPerformer getConsultPerformer(String key) {
		return consultMap.get(key);
	}
	
	public Map<String, NovelConsultPerformer> getConsultMap(){
		return this.consultMap;
	}
}
