package com.njq.wap.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.vo.grab.GrabTitleVO;
import com.njq.common.model.vo.grab.GrabTypeInfo;
import com.njq.wap.cache.WapTitleCacheManager;

@Service
public class WapGrabService {

	@Resource
	public WapTitleCacheManager wapTitleCacheManager;

	public List<GrabTypeInfo> makeTitleVo(Long docId, ChannelType channel){
		return wapTitleCacheManager.getList(docId, channel);
	}
	
	
	public List<GrabTitleVO> conver(List<BaseTitle> titleList){
		return titleList.stream().map(n->{
			GrabTitleVO vo = new GrabTitleVO();
            BeanUtils.copyProperties(n, vo);
            vo.setChannel(n.getChannel());
            return vo;
		}).collect(Collectors.toList());
	}
	
	
	
}
