package com.njq.wap.service;

import java.util.List;

import javax.annotation.Resource;

import com.njq.common.model.vo.grab.GrabTypeInfo;
import org.springframework.stereotype.Service;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.vo.grab.GrabTitleVO;
import com.njq.wap.cache.WapTitleCacheManager;

@Service
public class WapGrabService {

	@Resource
	public WapTitleCacheManager wapTitleCacheManager;

	public List<GrabTypeInfo> makeTitleVo(Long docId, ChannelType channel){
		return wapTitleCacheManager.getList(docId, channel);
	}
	
	
	
	
	
	
}
