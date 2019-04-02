package com.njq.xs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.XsTitleDesign;
import com.njq.common.model.vo.TitlethcVO;
import com.njq.common.util.string.StringUtil;
@Service
public class XsTitleDesignService {

	@Resource
	private DaoCommon<XsTitleDesign> titleDao;
	
	/**
	 * 查询所有章节
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<XsTitleDesign> queryAllName(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return titleDao.queryForPage(cc);
	}

	/**
	 * 查询所有章节
	 * @param docId
	 * @return
	 */
	public List<XsTitleDesign> queryAllName(Long docId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addNotEqParam("id", 0);
		cc.addEqParam("parentId", docId);
		cc.addSetOrderColum("indexOne", "asc");
		List<XsTitleDesign> queryList=titleDao.queryForListNoPage(cc);
		Map<String, List<XsTitleDesign>> map=new HashMap<String, List<XsTitleDesign>>();
		List<XsTitleDesign> l;
		List<Long> indexList=new  ArrayList<Long>();
		for(int i=0;i<queryList.size();i++){
			if(queryList.get(i).getType()==0){
				if(map.get(queryList.get(i).getType().toString())==null){
					l=new ArrayList<XsTitleDesign>();
					l.add(queryList.get(i));
					map.put(queryList.get(i).getId().toString(), l);
					indexList.add(queryList.get(i).getId());
				}
			}else{
				map.get(queryList.get(i).getParentId().toString()).add(queryList.get(i));
			}
		}
		List<XsTitleDesign> l1=new ArrayList<XsTitleDesign>();
		java.util.Collections.sort(indexList);
		for(int i=0;i<indexList.size();i++){
			l1.addAll(map.get(indexList.get(i).toString()));
		}
		return l1;
	}
	
	
	
	
	/**
	 * 根据文章id查询文章列表
	 * @param docId
	 * @return
	 */
    public List<XsTitleDesign> queryDocTitleList(Long docId) {
	    ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0L);
        cc.addEqParam("parentId", docId);
        cc.addSetOrderColum("indexOne", "asc");
        List<XsTitleDesign> queryList=titleDao.queryForListNoPage(cc);
        ConditionsCommon cc1=new ConditionsCommon();
        List<Long> ids=new ArrayList<Long>();
        Map<Long, List<XsTitleDesign>> map=new TreeMap<Long, List<XsTitleDesign>>();
        for(XsTitleDesign t:queryList){
            ids.add(t.getId());
            map.put(t.getId(), new ArrayList<XsTitleDesign>());
            map.get(t.getId()).add(t);
        }
        if(ids.size()<1){
            return null;
        }
        cc1.addInParam("parentId", ids);            
        cc1.addNotEqParam("id", 0L);
        cc1.addSetOrderColum("indexOne", "asc");
        List<XsTitleDesign> list1=titleDao.queryForListNoPage(cc1);
        for(XsTitleDesign t:list1){
            map.get(t.getParentId()).add(t);
        }
        List<XsTitleDesign> l1=new ArrayList<XsTitleDesign>();
        for(Long teger: map.keySet()){
            l1.addAll(map.get(teger));
        }
        return l1;
    }

    /**
     * 根据父id查询列表
     * @param parentId
     * @return
     */
    public List<XsTitleDesign> queryListByParentId(Long parentId) {
	    ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0);
        cc.addEqParam("parentId", parentId);
        cc.addSetOrderColum("id", "asc");
        List<XsTitleDesign> queryList=titleDao.queryForListNoPage(cc);
        return queryList;
    }

    /**
     * 查询书名列表
     * @param parentId
     * @return
     */
    public List<TitlethcVO> queryDocList(Long parentId){
        ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0L);
        cc.addEqParam("parentId", parentId);
        cc.addSetOrderColum("id", "asc");
        List<XsTitleDesign> queryList=titleDao.queryForListNoPage(cc);
        List<TitlethcVO> voList=new ArrayList<TitlethcVO>();
        for(XsTitleDesign t:queryList){
            String sql="select count(*) num from "+
                    "( select id from xs_title_design where parent_id="+t.getId()+") l1 , xs_title_design l2 "+
                    "where  l2.parent_id=l1.id ";
            List<Map<String, Object>> countL=titleDao.querySqlByParamForMap(sql, null);
            TitlethcVO vo=new TitlethcVO();
            vo.setId(t.getId());
            vo.setIndexOne(t.getIndexOne());
            vo.setParentId(t.getParentId());
            vo.setTitle(t.getTitle());
            vo.setTotal(Integer.parseInt(countL.get(0).get("num").toString()));
            vo.setType(t.getType());
            vo.setContextDesc(t.getContextDesc());
            voList.add(vo);
        }
        return voList;
    }
    
    /**
     * 根据id检索出标题
     * @param id
     * @return
     */
	public XsTitleDesign queryNameById(Long id) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("id", id);
        List<XsTitleDesign> queryList=titleDao.queryForListNoPage(cc);
		return queryList.get(0);
	}

	/**
	 * 保存标题
	 * @param title
	 */
	public void saveTitle(XsTitleDesign title){
		titleDao.save(title);
	}

	/**
	 * 根据id修改标题
	 * @param title
	 */
	public void updateTitleById(XsTitleDesign title) {
		titleDao.update(title);
	}

	/**
	 * 删除标题
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteTitleById(Long[] ids ,Long userId, Map<String, Object> map) {
		int num = titleDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			LogCommon.saveLog(userId, "删除", "消息表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("title", "请检查数据是否正确，再进行操作！");
		}
	}

	/**
	 * 根据id删除标题
	 * @param id
	 */
	public void deleteTitleById(Long id) {
		titleDao.delTRealById(id);		
	}

	
	

}
