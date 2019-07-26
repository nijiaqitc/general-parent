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
	private Map<String, NovelLoadPerformer> map;
	
	@Autowired
	public NovelPerformerFactory(NovelLoadPerformer qishuLoadPerformer, NovelLoadPerformer xiangCunChannelPerformer) {
		this.qishuLoadPerformer = qishuLoadPerformer;
		this.xiangCunChannelPerformer = xiangCunChannelPerformer;
		map = new HashMap<>();
		map.put(ChannelType.QI_SHU.getValue(), qishuLoadPerformer);
		map.put(ChannelType.XIANGCUN.getValue(), xiangCunChannelPerformer);
	}


	public NovelLoadPerformer getPerformer(String key) {
		return map.get(key);
	}
	
	
	public Map<String, NovelLoadPerformer> getMap(){
		return this.map;
	}
	
}
