package com.njq.xs.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.redis.lock.JedisLock;
import com.njq.common.base.redis.lock.JedisLockFactory;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.model.dao.XsDocGeneralInfoJpaRepository;
import com.njq.common.model.po.XsDocDetail;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.util.string.StringUtil2;

@Service
public class XsDocDetailService {
	private static final Logger logger = LoggerFactory.getLogger(XsDocDetailService.class);
    @Resource
    private DaoCommon<XsDocDetail> docDetailDao;
    @Resource
	public XsDocGeneralInfoService docGeneralInfoService;
    @Resource
	public XsTitleDetailService titleService;
    @Resource
    public XsDocGeneralInfoJpaRepository xsDocGeneralInfoJpaRepository;
    @Resource
    private JedisLockFactory jedisLockFactory;
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
	public XsDocDetail updateObject(XsDocDetail detail,String finishStatus, String titleIndex) {
		String lockKey = StringUtil2.format("updateXsDocId-{0}", detail.getId());
		XsDocDetail d=docDetailDao.queryTById(detail.getId());
		try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)){
			if (!jedisLock.acquire()) {
                throw new BaseKnownException("修改并发获取锁失败！");
            }
			XsTitleDetail titleDetail = titleService.queryByDocId(detail.getId());
			xsDocGeneralInfoJpaRepository.updateForAddFontNum((detail.getFontNum()-d.getFontNum()),titleDetail.getBookId());
			d.setTitle(detail.getTitle());
			d.setDoc(detail.getDoc());
			d.setFontNum(detail.getFontNum());
			d.setModiDate(new Timestamp(System.currentTimeMillis()));
			docDetailDao.update(d);
			docGeneralInfoService.updateFontNum(titleDetail.getId(), detail.getFontNum());
			XsTitleDetail t = new XsTitleDetail();
			t.setId(titleDetail.getId());
			t.setFinishStatus(finishStatus);
			t.setTitleIndex(titleIndex);
			titleService.updateTitleById(t);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return d;
	}
    
    
    
	public XsDocDetail saveDoc(XsTitleDetail detail,Long userId) {
		String lockKey = StringUtil2.format("saveXsDoctitle-{0}-userId-{1}",detail.getTitle(), userId);
		try (JedisLock jedisLock = this.jedisLockFactory.getLock(lockKey)){
			if (!jedisLock.acquire()) {
                throw new BaseKnownException("修改并发获取锁失败！");
            }
			XsDocDetail docDetail = new XsDocDetail();
			docDetail.setCreateDate(new Date());
			docDetail.setFontNum(0);
			docDetail.setTitle(detail.getTitle());
			docDetail.setUserId(userId);
			this.saveObject(docDetail);
			
			detail.setDocId(docDetail.getId());
			detail.setFinishStatus(ConstantsCommon.Finish_Status.NO_START);
			detail.setCreateDate(new Date());
			detail.setUserId(userId);
			titleService.saveTitle(detail);	
			
			XsDocGeneralInfo info = new XsDocGeneralInfo();
			info.setBadNum(0);
			info.setGoodNum(0);
			info.setCreateDate(new Date());
			info.setFontNum(0);
			info.setTitleId(detail.getId());
			info.setViewNum(0);
			docGeneralInfoService.saveObject(info);
			return docDetail;
		} catch (InterruptedException | IOException e) {
			logger.error("保存失败",e);
			throw new BaseKnownException("保存失败！");
		}	
			
	}
	
}
