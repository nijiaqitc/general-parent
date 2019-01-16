package com.njq.wap.cache;

import com.njq.basis.service.impl.BaseTypeService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.redis.GenericValueCacheManager;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.vo.grab.GrabTitleVO;
import com.njq.common.model.vo.grab.GrabTypeInfo;
import com.njq.common.util.string.StringUtil2;
import com.njq.grab.service.impl.GrabService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WapTitleCacheManager extends GenericValueCacheManager<String, List<GrabTypeInfo>> {
    private final String CACHE_GRABTITLE = "cache_grab_title_{0}";
    @Resource
    public GrabService grabService;
    @Resource
    public BaseTypeService baseTypeService;

    public List<GrabTypeInfo> getList(Long docId, ChannelType channel) {
    	String key ;
    	if(docId == null) {
    		key = StringUtil2.format(CACHE_GRABTITLE, "all");
    	}else {
    		key = StringUtil2.format(CACHE_GRABTITLE, docId+channel.getValue());
    	}
        List<GrabTypeInfo> ll = super.get(key);
        if (CollectionUtils.isEmpty(ll)) {
            List<BaseTitle> list = grabService.queryTitleList(docId, channel);
            Map<Long, List<GrabTitleVO>> map = new HashMap<>();
            list.forEach(n -> {
                if (CollectionUtils.isEmpty(map.get(n.getTypeId()))) {
                    map.put(n.getTypeId(), new ArrayList<>());
                }
                GrabTitleVO vo = new GrabTitleVO();
                BeanUtils.copyProperties(n, vo);
                vo.setChildrenCount(grabService.queryTitleChildrenCount(n.getId(), ChannelType.getChannelType(n.getChannel())));
                vo.setChannel(n.getChannel());
                map.get(n.getTypeId()).add(vo);
            });
            List<GrabTypeInfo> volist = map.entrySet().stream().map(n -> {
                GrabTypeInfo info = new GrabTypeInfo();
                if(n.getKey() == null){
                    info.setType("其他");
                }else{
                    info.setType(baseTypeService.getTypeById(n.getKey()).getName());
                }
                info.setGrabTitleVOList(n.getValue());
                return info;
            }).collect(Collectors.toList());
            this.update(key, volist, cacheExpiry);
            return volist;
        } else {
            return ll;
        }

    }

}
