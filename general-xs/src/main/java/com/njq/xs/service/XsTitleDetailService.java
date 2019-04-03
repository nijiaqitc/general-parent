package com.njq.xs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.XsDocGeneralInfo;
import com.njq.common.model.po.XsTitleDetail;
import com.njq.common.model.vo.TitlethcVO;
import com.njq.common.model.vo.XsListVO;
import com.njq.common.model.vo.xs.XsTitleDetailVO;
import com.njq.common.util.string.StringUtil;
@Service
public class XsTitleDetailService {

	@Resource
	private DaoCommon<XsTitleDetail> titleDetailDao;
	@Resource
	public XsDocGeneralInfoService docGeneralInfoService;
	
	/**
	 * 查询所有章节
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<XsTitleDetail> queryAllName(Map<String, Object> paramMap, int page , int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return titleDetailDao.queryForPage(cc);
	}

	/**
	 * 查询所有章节
	 * @param docId
	 * @return
	 */
	public List<XsTitleDetail> queryAllName(Long docId) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addNotEqParam("id", 0);
		cc.addEqParam("parentId", docId);
		cc.addSetOrderColum("orderIndex", "asc");
		List<XsTitleDetail> queryList=titleDetailDao.queryForListNoPage(cc);
		Map<String, List<XsTitleDetail>> map=new HashMap<String, List<XsTitleDetail>>();
		List<XsTitleDetail> l;
		List<Long> indexList=new  ArrayList<Long>();
		for(int i=0;i<queryList.size();i++){
			if(queryList.get(i).getType()==0){
				if(map.get(queryList.get(i).getType().toString())==null){
					l=new ArrayList<XsTitleDetail>();
					l.add(queryList.get(i));
					map.put(queryList.get(i).getId().toString(), l);
					indexList.add(queryList.get(i).getId());
				}
			}else{
				map.get(queryList.get(i).getParentId().toString()).add(queryList.get(i));
			}
		}
		List<XsTitleDetail> l1=new ArrayList<XsTitleDetail>();
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
    public List<XsTitleDetail> queryDocTitleList(Long docId) {
	    ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0L);
        cc.addEqParam("parentId", docId);
        cc.addSetOrderColum("orderIndex", "asc");
        List<XsTitleDetail> queryList=titleDetailDao.queryForListNoPage(cc);
        ConditionsCommon cc1=new ConditionsCommon();
        List<Long> ids=new ArrayList<Long>();
        Map<Long, List<XsTitleDetail>> map=new TreeMap<Long, List<XsTitleDetail>>();
        for(XsTitleDetail t:queryList){
            ids.add(t.getId());
            map.put(t.getId(), new ArrayList<XsTitleDetail>());
            map.get(t.getId()).add(t);
        }
        if(ids.size()<1){
            return null;
        }
        cc1.addInParam("parentId", ids);            
        cc1.addNotEqParam("id", 0L);
        cc1.addSetOrderColum("orderIndex", "asc");
        List<XsTitleDetail> list1=titleDetailDao.queryForListNoPage(cc1);
        for(XsTitleDetail t:list1){
            map.get(t.getParentId()).add(t);
        }
        List<XsTitleDetail> l1=new ArrayList<XsTitleDetail>();
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
    public List<XsTitleDetail> queryListByParentId(Long parentId) {
	    ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0L);
        cc.addEqParam("parentId", parentId);
        cc.addSetOrderColum("id", "asc");
        List<XsTitleDetail> queryList=titleDetailDao.queryForListNoPage(cc);
        return queryList;
    }

    /**
     * 查询书名列表
     * @param parentId
     * @return
     */
    public List<TitlethcVO> queryDocList(Long parentId,boolean sourceType){
        ConditionsCommon cc=new ConditionsCommon();
        cc.addNotEqParam("id", 0L);
        cc.addEqParam("parentId", parentId);
        if(sourceType) {
        	cc.addEqParam("isShow", ConstantsCommon.Finish_Status.STARTING);        	
        }
        cc.addSetOrderColum("id", "asc");
        List<XsTitleDetail> queryList=titleDetailDao.queryForListNoPage(cc);
        List<TitlethcVO> voList=new ArrayList<TitlethcVO>();
        for(XsTitleDetail t:queryList){
            TitlethcVO vo=new TitlethcVO();
            vo.setId(t.getId());
            vo.setParentId(t.getParentId());
            vo.setTitle(t.getTitle());
            vo.setType(t.getType());
            vo.setContextDesc(t.getContextDesc());
            vo.setIsShow(t.getIsShow());
            XsDocGeneralInfo gi = docGeneralInfoService.queryByTitleId(t.getId());
            vo.setTotal(gi.getFontNum());
            vo.setGoodNum(gi.getGoodNum());
            vo.setBadNum(gi.getBadNum());
            vo.setFinishStatus(t.getFinishStatus()==null?"0":t.getFinishStatus());
            voList.add(vo);
        }
        return voList;
    }
    
    /**
     * 根据id检索出标题
     * @param id
     * @return
     */
	public XsTitleDetail queryById(Long id) {
        return titleDetailDao.queryTById(id);
	}

	public XsTitleDetail queryByDocId(Long docId) {
		ConditionsCommon condition = new ConditionsCommon();
		condition.addEqParam("docId", docId);
		return titleDetailDao.queryTByParamForOne(condition);
	}
	
	/**
	 * 保存标题
	 * @param title
	 */
	public void saveTitle(XsTitleDetail title){
		titleDetailDao.save(title);
	}

	/**
	 * 根据id修改标题
	 * @param title
	 */
	public void updateTitleById(XsTitleDetail title) {
		titleDetailDao.updateByPrimaryKeySelective(title);
	}

	/**
	 * 删除标题
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteTitleById(Long[] ids ,Long userId, Map<String, Object> map) {
		int num = titleDetailDao.delT(ids);
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
		titleDetailDao.delTRealById(id);		
	}

	public List<XsListVO> queryAllTitleGroupJuan(Long bookId,boolean isShow){
		List<XsListVO> voList = new ArrayList<>();
		List<XsTitleDetail> juanList = this.queryJuanList(bookId,2,null,isShow);
		for (int i = 0; i < juanList.size(); i++) {
			List<XsTitleDetail> menuList = this.queryJuanList(null,3,juanList.get(i).getId(),isShow);
			XsTitleDetail detail = juanList.get(i); 
			XsListVO vo = new XsListVO();
			vo.setJuanDetail(detail);
			vo.setpId(detail.getId());
			vo.setTotal(menuList.size());
			vo.setList(menuList);
			voList.add(vo);
		}
		return voList;
	}
	
	
	public List<XsTitleDetail> queryJuanList(Long bookId , Integer type,Long pId,boolean isShow){
		ConditionsCommon condition = new ConditionsCommon();
		if(bookId != null) {
			condition.addEqParam("bookId", bookId);			
		}
		if(type != null) {
			condition.addEqParam("type", type);			
		}
		if(pId != null) {
			condition.addEqParam("parentId", pId);
		}
		if(isShow) {
			condition.addEqParam("isShow", ConstantsCommon.Finish_Status.STARTING);     
		}
		condition.addSetOrderColum("id", "asc");
		return titleDetailDao.queryColumnForList(condition);
	}

	/**
	 * 查询所有章节标题列表
	 * @param parentId
	 * @return
	 */
	public List<XsTitleDetailVO> queryAllTitleListByDocId(Long parentId) {
		List<XsTitleDetail> list=this.queryListByParentId(parentId);
		List<Long> ids=new ArrayList<Long>();
		TreeMap<Long, List<XsTitleDetailVO>> trmap=new TreeMap<Long, List<XsTitleDetailVO>>();
		List<XsTitleDetailVO> l;
		for(XsTitleDetail detail:list){
			ids.add(detail.getId());
			l=new ArrayList<XsTitleDetailVO>();
			XsTitleDetailVO vo = new XsTitleDetailVO();
			BeanUtils.copyProperties(detail, vo);
			l.add(vo);
			Map<String, Object> mp = this.queryMaxNum(detail.getId());
			vo.setMaxTitleIndex(mp.get("titleIndex")==null?0:Integer.valueOf(mp.get("titleIndex").toString()));
			vo.setMaxOrderIndex(mp.get("orderIndex")==null?0:Integer.valueOf(mp.get("orderIndex").toString()));
			trmap.put(detail.getId(), l);
		}
		if(ids.size()>0){
			ConditionsCommon cc=new ConditionsCommon();
			cc.addInParam("parentId", ids);
			cc.addSetOrderColum("orderIndex", "asc");
			List<XsTitleDetail> queryList=titleDetailDao.queryForListNoPage(cc);
			for(XsTitleDetail d:queryList){
				XsTitleDetailVO vo = new XsTitleDetailVO();
				BeanUtils.copyProperties(d, vo);
				trmap.get(d.getParentId()).add(vo);
			}
		}
        List<XsTitleDetailVO> l1=new ArrayList<>();
        for(Long key :trmap.keySet()){
        	l1.addAll(trmap.get(key));
        }
		return l1;
	}

	
	
    /**
     * 查询最大章节数
     * @param bookId
     * @return
     */
    public Map<String, Object> queryMaxNum(Long parentId) {
        String sql="select max(title_index) titleIndex, max(order_index) orderIndex from xs_title_detail  where parent_id=:parentId";
        Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("parentId", parentId);
        List<Map<String, Object>> m=titleDetailDao.querySqlByParamForMap(sql, paramMap);
        return m.get(0);
    }

    /**
     * 查询章节标题信息
     * @param dd
     * @return
     */
    public XsTitleDetail queryDetalByOrderIndex(XsTitleDetail dd) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("title", dd.getTitle());
        cc.addEqParam("orderIndex", dd.getOrderIndex());
        return titleDetailDao.queryTByParamForOne(cc);
    }

    /**
     * 设置显示或不显示
     * @param id
     * @param isShow
     */
    public void updateShowType(Long id, String isShow) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("id", id);
        cc.addsetObjectParam("isShow", isShow);
        titleDetailDao.update(cc);
    }

	/**
	 * 更新数据
	 * @param dd
	 */
	public void updateObject(XsTitleDetail dd) {
		titleDetailDao.update(dd);
	}

	/**
	 * 图表统计
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryDocAccountMonth(String start ,String end,Long userId){
        String ss=" select count(id) as num from xs_title_detail where create_date>='"+start+"' and create_date <'"+end+"' and type=3  ";
        List<Map<String, Object>> ll=titleDetailDao.querySqlByParamForMap(ss, null);
        return ll.get(0);
    }
	
	
	public Map<String, Object> queryBeforeAndNextNo(Long genId){
		ConditionsCommon cc1=new ConditionsCommon();
		cc1.addEqParam("id", genId);
		List<XsTitleDetail> detailList=titleDetailDao.queryForListNoPage(cc1);
		XsTitleDetail detail=detailList.get(0);
		
		ConditionsCommon cc2=new ConditionsCommon();
		cc2.addEqParam("bookId", detail.getBookId());
		cc2.addGtParam("id", genId);
		cc2.addEqParam("type", ConstantsCommon.Novel_Type.TYPE_TITLE);
		cc2.addSetOrderColum("id", "asc");
		cc2.addPageParam(1, 1);
		List<XsTitleDetail> detailList1=titleDetailDao.queryForListNoPage(cc2);
		
		ConditionsCommon cc3=new ConditionsCommon();
		cc3.addEqParam("bookId", detail.getBookId());
		cc3.addLtParam("id", genId);
		cc3.addPageParam(1, 1);
		cc3.addEqParam("type", ConstantsCommon.Novel_Type.TYPE_TITLE);
		cc3.addSetOrderColum("id", "desc");
		List<XsTitleDetail> detailList2=titleDetailDao.queryForListNoPage(cc3);
		Map<String, Object> m=new HashMap<String, Object>();
		if(detailList1!=null&&detailList1.size()>0){
			m.put("next",detailList1.get(0).getId());			
		}
		if(detailList2!=null&&detailList2.size()>0){
			m.put("pre",detailList2.get(0).getId());			
		}
		m.put("cn", detail);
		return m;
	}

	
	public Long saveNovel(String title , String contextDesc,Long userId,String finishStatus){
		XsTitleDetail detail = new XsTitleDetail();
		detail.setTitle(title);
		detail.setContextDesc(contextDesc);
		detail.setCreateDate(new Date());
		detail.setOrderIndex(1);
		detail.setType(1);
		detail.setParentId(0L);
		detail.setIsShow("1");
		detail.setUserId(userId);
		detail.setFinishStatus(finishStatus);
		this.saveTitle(detail);
		XsDocGeneralInfo info = new XsDocGeneralInfo();
		info.setBadNum(0);
		info.setGoodNum(0);
		info.setCreateDate(new Date());
		info.setFontNum(0);
		info.setTitleId(detail.getId());
		info.setViewNum(0);
		docGeneralInfoService.saveObject(info);
		return detail.getId();
	}
	
	public Long updateNovel(Long id , String title , String contextDesc,String finishStatus){
		XsTitleDetail detail = new XsTitleDetail();
		detail.setId(id);
		detail.setTitle(title);
		detail.setContextDesc(contextDesc);
		detail.setFinishStatus(finishStatus);
		this.updateTitleById(detail);
		return detail.getId();
	}
	
	
	
}
