package com.njq.wap.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.vo.GrabTitleVO;
import com.njq.grab.service.impl.GrabService;

@Service
public class WapGrabService {

	@Resource
    public GrabService grabService;
	
	
	public List<GrabTitleVO> makeTitleVo(Long docId,ChannelType channel){
		List<BaseTitle> list = grabService.queryTitleList(docId,channel);
		List<GrabTitleVO> volist = new ArrayList<GrabTitleVO>();
		list.forEach(n->{
			GrabTitleVO vo = new GrabTitleVO();
			BeanUtils.copyProperties(n, vo);
			vo.setChildrenCount(grabService.queryTitleChildrenCount(n.getId(), channel));
			volist.add(vo);
		});
		return volist;
	}
	
	
	
	
	
	
}
