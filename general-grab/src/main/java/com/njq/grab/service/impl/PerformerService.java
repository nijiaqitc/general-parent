package com.njq.grab.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.PageAnalysisPerformer;

@Service
public class PerformerService {
	private Map<ChannelType, PageAnalysisPerformer> analyMap;
	@Autowired
	public PerformerService(PageAnalysisPerformer yhWikiPageAnalysis, PageAnalysisPerformer cgblogsPageAnalysis, PageAnalysisPerformer csdnPageAnalysisPerformer) {
		analyMap = new HashMap<>();
		analyMap.put(ChannelType.YH_WIKI, yhWikiPageAnalysis);
		analyMap.put(ChannelType.CNBLOGS, cgblogsPageAnalysis);
		analyMap.put(ChannelType.CSDN, csdnPageAnalysisPerformer);
	}
	
	public Map<ChannelType, PageAnalysisPerformer> getAnalysisPerformerMap(){
		return analyMap;
	}
	
	public PageAnalysisPerformer getAnalysisPerformer(ChannelType channel) {
		return analyMap.get(channel);
	}
}
