package com.njq.xs.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.XsDocDiscuss;
@Service
public class XsDocDiscussService {

    @Resource
    private DaoCommon<XsDocDiscuss> docDiscussDao;

    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<XsDocDiscuss> queryPageList(Map<String, Object> paramMap, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<XsDocDiscuss> queryList(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据id查询对象
     * @param docId
     * @return
     */
    public XsDocDiscuss queryById(Long docId) {
        return docDiscussDao.queryTById(docId);
    }

    /**
     * 根据条件查询总数
     * @param paramMap
     * @return
     */
    public int queryCount(Map<String, Object> paramMap) {
    	ConditionsCommon condition = new ConditionsCommon();
    	condition.addEqParam("docId", paramMap.get("docId"));
    	return docDiscussDao.queryForCount(condition);
    }

    /**
     * 保存对象
     * @param t
     * @return
     */
    public int saveObject(XsDocDiscuss t) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 根据id删除对象
     * @param ids
     * @return
     */
    public int deleteById(Long ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 根据id集合删除对象集合
     * @param ids
     * @return
     */
    public int deleteByIds(Long[] ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 根据id和条件修改对象
     * @param id
     * @param map
     * @return
     */
    public int updateById(Long id, Map<String, Object> map) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    
    
    
}
