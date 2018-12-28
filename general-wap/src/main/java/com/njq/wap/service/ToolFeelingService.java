package com.njq.wap.service;


import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.ToolFeeling;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ToolFeelingService {

    @Resource
    private DaoCommon<ToolFeeling> feelingDao;

    /**
     * 查询心情
     *
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<ToolFeeling> queryFeelings(Map<String, Object> paramMap,
                                               int page, int size) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        cc.addEqParam("userId", paramMap.get("userId"));
        cc.addEqParam("textType", paramMap.get("textType"));
        return feelingDao.queryForPage(cc);
    }

    /**
     * 保存心情
     *
     * @param feel
     * @param userId
     * @param map
     * @return
     */
    public Map<String, Object> saveFeelings(ToolFeeling feel, Long userId, Map<String, Object> map) {
        feelingDao.save(feel);
        //日志记录
        MessageCommon.getSuccessMap(map);
        return map;
    }

}
