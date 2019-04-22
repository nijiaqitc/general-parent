package com.njq.wap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlColumnDefine;
import com.njq.common.model.po.YxlColumnStore;
import com.njq.common.model.po.YxlTypeName;

@Service
public class WapRecordService {
	@Resource
    private DaoCommon<YxlTypeName> yxlTypeNameDao;
    @Resource
    private DaoCommon<YxlColumnDefine> yxlColumnDefineDao;
    @Resource
    private DaoCommon<YxlColumnStore> yxlColumnStoreDao;
    
    public List<YxlTypeName> queryNameList() {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addInParam("id", new Long[]{52L,54L,55L});
        return yxlTypeNameDao.queryForListNoPage(cc);
    }
    
    public PageList<YxlColumnStore> queryStore(Long recordType,Integer page, Integer size){
    	ConditionsCommon cc = new ConditionsCommon();
    	cc.addEqParam("recordType", recordType);
    	cc.addPageParam(page, size);
    	return yxlColumnStoreDao.queryForPage(cc);
    }
    
}
