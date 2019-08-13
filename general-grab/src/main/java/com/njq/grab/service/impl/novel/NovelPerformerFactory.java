package com.njq.grab.service.impl.novel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njq.common.enumreg.channel.ChannelType;

@Component
public class NovelPerformerFactory {

	private Map<String, NovelLoadPerformer> map;
	private Map<String,NovelConsultPerformer> consultMap;
	@Autowired
	public NovelPerformerFactory(NovelLoadPerformer qishuLoadPerformer, NovelLoadPerformer dingdianLoadPerformer,
			NovelLoadPerformer xiangCunChannelPerformer, NovelLoadPerformer bbiqugeLoadPerformer,
			NovelConsultPerformer qidianConsultPerformer,NovelConsultPerformer zonghengConsultPerformer,NovelConsultPerformer xxsyConsultPerformer) {
		map = new HashMap<>();
//		map.put(ChannelType.QI_SHU.getValue(), qishuLoadPerformer);
//		map.put(ChannelType.XIANGCUN.getValue(), xiangCunChannelPerformer);
//		map.put(ChannelType.BBIQUGE.getValue(), bbiqugeLoadPerformer);
		map.put(ChannelType.DINGDIAN.getValue(), dingdianLoadPerformer);
		consultMap = new HashMap<>();
//		consultMap.put(ChannelType.QIDIAN.getValue(), qidianConsultPerformer);
//		consultMap.put(ChannelType.ZONGHENG.getValue(), zonghengConsultPerformer);
		consultMap.put(ChannelType.XIAOXIANGSHUYUAN.getValue(), xxsyConsultPerformer);
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
