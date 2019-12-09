package com.njq.grab.service.impl;

import com.njq.common.enumreg.channel.ChannelType;
import com.njq.grab.service.PageAnalysisPerformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PerformerService {
    private Map<ChannelType, PageAnalysisPerformer> analyMap;

    @Autowired
    public PerformerService(PageAnalysisPerformer yhWikiPageAnalysis, PageAnalysisPerformer cgblogsPageAnalysis,
                            PageAnalysisPerformer csdnPageAnalysisPerformer, PageAnalysisPerformer jianshuPageAnalysisPerformer,
                            PageAnalysisPerformer txsqPageAnalysisPerformer) {
        analyMap = new HashMap<>();
        analyMap.put(ChannelType.YH_WIKI, yhWikiPageAnalysis);
        analyMap.put(ChannelType.CNBLOGS, cgblogsPageAnalysis);
        analyMap.put(ChannelType.CSDN, csdnPageAnalysisPerformer);
        analyMap.put(ChannelType.JIANSHU, jianshuPageAnalysisPerformer);
        analyMap.put(ChannelType.TXSQ, txsqPageAnalysisPerformer);
    }

    public Map<ChannelType, PageAnalysisPerformer> getAnalysisPerformerMap() {
        return analyMap;
    }

    public PageAnalysisPerformer getAnalysisPerformer(ChannelType channel) {
        return analyMap.get(channel);
    }
}
