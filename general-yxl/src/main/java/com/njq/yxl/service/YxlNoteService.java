package com.njq.yxl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlFolder;
import com.njq.common.model.po.YxlNote;
import com.njq.common.model.po.YxlNoteGeneral;
import com.njq.common.util.string.StringUtil;

@Service
public class YxlNoteService {

	
	@Resource
    private DaoCommon<YxlNote> yxlNoteDao;
	@Resource
    private DaoCommon<YxlNoteGeneral> yxlNoteGeneralDao;
	@Resource
	private DaoCommon<YxlFolder> yxlFolderDao;
    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
	public PageList<YxlNote> queryPageList(Map<String, Object> paramMap, int page , int size){
//	    ConditionsCommon cc=new ConditionsCommon();
//        cc.addPageParam(page, size);
//        return yxlNoteDao.queryForPage(cc);
	    return null;
	}
	
	/**
	 * 根据条件查询列表
	 * @param paramMap
	 * @return
	 */
	public List<YxlNote> queryList(Map<String, Object> paramMap){
//	    ConditionsCommon cc=new ConditionsCommon();
//        cc.addEqParam("isUse", ConstantsCommon.Use_YxlNoteype.USED);
//        return bannerDao.queryForListNoPage(cc);
	    return null;
	}
	
	/**
	 * 查询文件夹名字集合
	 * @return
	 */
	public List<YxlFolder> queryFolderList(int type){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("type", type);
	    cc.addSetOrderColum("id", "desc");
		return yxlFolderDao.queryForListNoPage(cc);
	}
	
	/**
	 * 锁校验
	 * @param id
	 * @param pwd
	 * @return
	 */
	public int queryFolderCount(Long id,String pwd){
		ConditionsCommon cc=new ConditionsCommon();
		cc.addEqParam("id", id);
		List<YxlFolder> list=yxlFolderDao.queryForListNoPage(cc);
		if(list.size()==1&&("0".equals(list.get(0).getIsLock())||pwd.equals(list.get(0).getLockPwd()))){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 查询概要列表
	 * @return
	 */
	public List<YxlNoteGeneral> queryTitleList(){
	    ConditionsCommon cc=new ConditionsCommon();
	    cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
	    cc.addSetOrderColum("id", "desc");
	    return yxlNoteGeneralDao.queryForListNoPage(cc);
	}
	
	public List<YxlNoteGeneral> queryTitleListByFolder(Long folderId){
	    ConditionsCommon cc=new ConditionsCommon();
	    cc.addEqParam("folder_id", folderId);
	    cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
	    cc.addSetOrderColum("id", "desc");
	    return yxlNoteGeneralDao.queryForListNoPage(cc);
	}
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public YxlNote queryById(Long id){
	    return yxlNoteDao.queryTById(id);
    }

	/**
	 * 根据条件查询总数
	 * @param column
	 * @param value
	 * @return
	 */
	public int queryCount(String column,String value){
		ConditionsCommon cc=new ConditionsCommon();
	    cc.addEqParam(column, value);
	    cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
	    return yxlNoteGeneralDao.queryForCount(cc);
    }
    
	public void saveFolder(YxlFolder folder){
		if(StringUtil.IsNotEmpty(folder.getLockPwd())){
			folder.setIsLock("1");
		}else{
			folder.setIsLock("0");
		}
		folder.setCreateDate(new Date());
		yxlFolderDao.update(folder);
	}
	
	/**
	 * 保存对象
	 * @param t
	 * @return
	 */
	public YxlNoteGeneral saveObject(YxlNote t,String general,Long folderId){
	    YxlNoteGeneral gen=null;
	    if(t.getId()!=null){
	        ConditionsCommon cc=new ConditionsCommon();
	        cc.addEqParam("docId",t.getId());
	        gen=yxlNoteGeneralDao.queryForListNoPage(cc).get(0);
	    }
	    yxlNoteDao.update(t);
	    if(gen==null){
	        gen=new YxlNoteGeneral();	        
	        gen.setDocId(t.getId());
	        gen.setCreateDate(new Date());
	        gen.setUserId(t.getUserId());
	        gen.setStatus(ConstantsCommon.Del_Status.YES);
	        gen.setFolderId(folderId);
	    }
	    general=general.replaceAll("<", "&lt;");
	    if(general.length()>30){
	        gen.setGeneral(general.substring(0,30));	        
	    }else{
	        gen.setGeneral(general);
	    }
	    gen.setTitle(t.getTitle());
	    yxlNoteGeneralDao.update(gen);
	    return gen;
    }
    
	public int deleteFolderById(Long id){
		return yxlFolderDao.delTRealById(id);
	}
	
	/**
	 * 根据id删除对象
	 * @param ids
	 * @return
	 */
	public int deleteById(Long ids){
	    ConditionsCommon cc=new ConditionsCommon();
	    cc.addsetObjectParam("status", ConstantsCommon.Del_Status.NO);
	    cc.addEqParam("id", ids);
	    yxlNoteDao.update(cc);
	    ConditionsCommon cc1=new ConditionsCommon();
        cc1.addsetObjectParam("status", ConstantsCommon.Del_Status.NO);
        cc1.addEqParam("docId", ids);
        yxlNoteGeneralDao.update(cc1);
	    return 0;
    }
    
	/**
	 * 根据id集合删除对象集合
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Long[] ids){
//	    return yxlNoteDao.delYxlNote(ids);
        return 0;
    }
    
	/**
	 * 根据id和条件修改对象
	 * @param id
	 * @param map
	 * @return
	 */
	public int updateById(Long id,Map<String, Object> map){
//	    yxlNoteDao.update(object)
        return 0;
    }

	
}
