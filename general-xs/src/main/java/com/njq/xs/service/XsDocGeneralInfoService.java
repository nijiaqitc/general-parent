package com.njq.xs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.dao.XsDocGeneralInfoJpaRepository;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsDocUserOp;

@Service
public class XsDocGeneralInfoService {

    @Resource
    private DaoCommon<XsDocGeneralInfo> docGeneralInfoDao;
    @Resource
    private XsDocGeneralInfoJpaRepository xsDocGeneralInfoJpaRepository;
    @Resource
    private XsDocUserOpService xsDocUserOpService;
    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<XsDocGeneralInfo> queryPageList(Map<String, Object> paramMap, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<XsDocGeneralInfo> queryList(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public XsDocGeneralInfo queryById(Long id) {
        // TODO Auto-generated method stub
        return null;
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
    public int saveObject(XsDocGeneralInfo t) {
    	docGeneralInfoDao.save(t);
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

    public int update(String op,Long titleId,Long userId) {
    	ConditionsCommon con = new ConditionsCommon();
    	con.addEqParam("userId", userId);
    	con.addEqParam("op", op);
    	con.addEqParam("titleId", titleId);
    	XsDocUserOp uop = xsDocUserOpService.queryByCon(con);
    	if(uop != null) {
    		return 0;
    	}
    	if(op == "2") {
    		xsDocGeneralInfoJpaRepository.updateForAddGoodNum(titleId);
    	}else  if(op == "3"){
    		xsDocGeneralInfoJpaRepository.updateForAddBadNum(titleId);
    	}
    	uop = new XsDocUserOp();
    	uop.setOp(op);
    	uop.setCreateDate(new Date());
    	uop.setTitleId(titleId);
    	uop.setUserId(userId);
    	xsDocUserOpService.saveObject(uop);
    	return 0;
    }
    
    
    /**
     * 根据文章id查询统计数
     * @param id
     * @return
     */
    public XsDocGeneralInfo queryByTitleId(Long id) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("titleId", id);
        return docGeneralInfoDao.queryTByParamForOne(cc);
    }
    
}
