package com.njq.wap.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.vo.GrabTitleVO;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.impl.GrabService;
@Component
public class WapTitleCacheManager extends GenericValueCacheManager<String, List<GrabTitleVO>>{
	private final String CACHE_GRABTITLE = "cache_grab_title_{0}";
	@Resource
    public GrabService grabService;
	
	public List<GrabTitleVO> getList(Long docId,ChannelType channel) {
		List<GrabTitleVO> ll = super.get(StringUtil2.format(CACHE_GRABTITLE, docId));
		if(CollectionUtils.isEmpty(ll)) {
			List<BaseTitle> list = grabService.queryTitleList(docId,channel);
			List<GrabTitleVO> volist = new ArrayList<GrabTitleVO>();
			list.forEach(n->{
				GrabTitleVO vo = new GrabTitleVO();
				BeanUtils.copyProperties(n, vo);
				vo.setChildrenCount(grabService.queryTitleChildrenCount(n.getId(), channel));
				volist.add(vo);
			});
			this.update(StringUtil2.format(CACHE_GRABTITLE, docId), volist, cacheExpiry);			
			return volist;
		}else {
			return ll;
		}
		
	}
	
}
