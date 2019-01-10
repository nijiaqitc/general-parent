package com.njq.wap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.vo.GrabTitleVO;
import com.njq.wap.cache.WapTitleCacheManager;

@Service
public class WapGrabService {

	@Resource
	public WapTitleCacheManager wapTitleCacheManager;

	public List<GrabTitleVO> makeTitleVo(Long docId,ChannelType channel){
		return wapTitleCacheManager.getList(docId, channel);
	}
	
	
	
	
	
	
}
