package com.njq.xs.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsTitleDetail;

@Service
public class XsDocDetailService {

    @Resource
    private DaoCommon<XsDocDetail> docDetailDao;
    @Resource
	public XsDocGeneralInfoService docGeneralInfoService;
    @Resource
	public XsTitleDetailService titleService;
    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<XsDocDetail> queryPageList(Map<String, Object> paramMap, int page, int size) {
        return null;
    }

    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<XsDocDetail> queryList(Map<String, Object> paramMap) {
        return null;
    }

    /**
     * 根据id查询对象
     * @param docId
     * @return
     */
    public XsDocDetail queryById(Long docId) {
        return docDetailDao.queryTById(docId);
    }
    
    /**
     * 根据条件查询总数
     * @param paramMap
     * @return
     */
    public int queryCount(Map<String, Object> paramMap) {
        return 0;
    }

    /**
     * 保存对象
     * @param t
     * @return
     */
    public int saveObject(XsDocDetail t) {
        docDetailDao.save(t);
        return 0;
    }

    /**
     * 根据id删除对象
     * @param ids
     * @return
     */
    public int deleteById(Long ids) {
        return 0;
    }

    /**
     * 根据id集合删除对象集合
     * @param ids
     * @return
     */
    public int deleteByIds(Long[] ids) {
        return 0;
    }

    /**
     * 根据id和条件修改对象
     * @param id
     * @param map
     * @return
     */
    public int updateById(Long id, Map<String, Object> map) {
    	return 0;
    }

	/**
	 * 更新数据
	 * @param detail
	 * @return
	 */
	public XsDocDetail updateObject(XsDocDetail detail) {
		XsDocDetail d=docDetailDao.queryTById(detail.getId());
		d.setTitle(detail.getTitle());
		d.setDoc(detail.getDoc());
		d.setFontNum(detail.getFontNum());
		d.setModiDate(new Timestamp(System.currentTimeMillis()));
		docDetailDao.update(d);
		XsTitleDetail titleDetail = titleService.queryByDocId(detail.getId());
		docGeneralInfoService.updateFontNum(titleDetail.getId(), detail.getFontNum());
		return d;
	}
    
    
    
}
