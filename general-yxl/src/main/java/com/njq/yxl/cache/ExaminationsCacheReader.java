package com.njq.yxl.cache;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njq.basis.service.impl.YxlStudyService;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.enumreg.cachepre.CacheNamePrefixEnum;

@Component
public class ExaminationsCacheReader extends GenericValueCacheManager<String, Map<String, Object>>{

	@Resource
	private YxlStudyService yxlStudyService;
	
	@Override
	protected CacheNamePrefixEnum registerCacheName() {
		return CacheNamePrefixEnum.EXAMINATIONS;
	}

	public Map<String, Object> getOrsetData(String key){
		Map<String, Object> map = yxlStudyService.getExam();
		update(key, map, 1440);
		return map;
	}
	
	
}
