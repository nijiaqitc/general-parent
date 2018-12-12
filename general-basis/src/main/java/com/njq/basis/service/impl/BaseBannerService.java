package com.njq.basis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.BaseBanner;
@Service
public class BaseBannerService {

    @Resource
    private DaoCommon<BaseBanner> bannerDao;
    
    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<BaseBanner> queryPageList(Map<String, Object> paramMap, int page, int size) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addPageParam(page, size);
        return bannerDao.queryForPage(cc);
    }

    
    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<BaseBanner> queryList(Map<String, Object> paramMap) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("isUse", ConstantsCommon.Use_Type.USED);
        return bannerDao.queryForListNoPage(cc);
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public BaseBanner queryById(Long id) {
        return bannerDao.queryTById(id);
    }

    /**
     * 根据条件查询总数
     * @param paramMap
     * @return
     */
    public int queryCount(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 保存对象
     * @param t
     * @return
     */
    public int saveObject(BaseBanner t) {
        bannerDao.save(t);
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
        ConditionsCommon cc=new ConditionsCommon();
        cc.addInParam("id", ids);
        return bannerDao.deleteBycc(cc);
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

    /**
     * 批量修改图片的应用状态
     * @param ids
     * @param isUse
     * @return
     */
    public int updateBannerApplyStatus(Long[] ids, String isUse) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addsetStringParam("isUse", isUse);
        cc.addInParam("id", ids);
        return bannerDao.update(cc);
    }

    /**
     * 根据id集合查询图片列表
     * @param ids
     * @return
     */
    public List<BaseBanner> queryListByIds(Long[] ids) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addInParam("id", ids);
        return bannerDao.queryForListNoPage(cc);
    }

    
}
