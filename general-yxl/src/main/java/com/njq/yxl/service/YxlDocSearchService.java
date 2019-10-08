package com.njq.yxl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.vo.DocListVO;
import com.njq.common.model.vo.DocVO;
import com.njq.common.util.date.DateUtil;
import com.njq.common.util.string.StringUtil;


@Service
public class YxlDocSearchService {

    @Resource
    private DaoCommon<YxlDocSearch> yxlDocSearchDao;
    @Resource
    private YxlDocService yxlDocService;
    @Resource
    private DaoCommon<YxlTip> yxlTipDao;
    
    public PageList<YxlDocSearch> queryPageList(Map<String, Object> paramMap, int page, int size) {
    	ConditionsCommon cc=new ConditionsCommon();
    	cc.addPageParam(page, size);
    	cc.addSetOrderColum("id", "desc");
        return yxlDocSearchDao.queryForPage(cc);
    }

    
    public List<YxlDocSearch> queryList(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    
    public YxlDocSearch queryById(Long id) {
        return yxlDocSearchDao.queryTById(id);
    }

    /**
     * 根据文章id查询对象
     * @param docId
     * @return
     */
    public YxlDocSearch queryByDocId(Long docId) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("docId", docId);
        return yxlDocSearchDao.queryTByParamForOne(cc);
    }
    
    public int queryCount(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    public void saveObject(YxlDocSearch t) {
        // TODO Auto-generated method stub
        
    }

    
    public int deleteById(Long ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    public int deleteByIds(Long[] ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    public int updateById(Long id, Map<String, Object> map) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * 查询系列标题
     * @param typeId
     * @return
     */
    public List<YxlDocSearch> queryTitleList(Long docId){
    	ConditionsCommon cc1=new ConditionsCommon();
		cc1.addEqParam("docId", docId);
		YxlDocSearch ll=yxlDocSearchDao.queryTByParamForOne(cc1);
    	if(ll!=null && ll.getTypeId() != null){
    		ConditionsCommon cc=new ConditionsCommon();
    		cc.addSeleParam("id","id");
    		cc.addSeleParam("docId","docId");    		
    		cc.addSeleParam("title","title");
    		cc.addEqParam("typeId", ll.getTypeId());
    		return yxlDocSearchDao.queryColumnForList(cc);    		
    	}else{
    		return new ArrayList<YxlDocSearch>();
    	}
    	
    }
    
    public Pair<YxlDocSearch, YxlDocSearch> getlrTitle(Long titleId) {
        ConditionsCommon conditionsCommon = new ConditionsCommon();
        conditionsCommon.addLtParam("id", titleId);
        conditionsCommon.addSetOrderColum("id", "desc");
        conditionsCommon.addPageParam(1, 1);
        List<YxlDocSearch> leftList = yxlDocSearchDao.queryForListNoPage(conditionsCommon);
        conditionsCommon = new ConditionsCommon();
        conditionsCommon.addGtParam("id", titleId);
        conditionsCommon.addPageParam(1, 1);
        List<YxlDocSearch> rightList = yxlDocSearchDao.queryForListNoPage(conditionsCommon);
        return Pair.of(CollectionUtils.isEmpty(leftList) ? null : leftList.get(0), CollectionUtils.isEmpty(rightList) ? null : rightList.get(0));
    }
    
    /**
     * 查询文章
     * @param flag true:系列文章  false：不是系列文章
     * @return
     */
    public List<YxlDocSearch> queryTitleYxlList(boolean flag){
        ConditionsCommon cc=new ConditionsCommon();
        if(flag==true){
            cc.addIsNotNullParam("typeId");
            cc.addSetOrderColum("id", "asc");
        }else{
            cc.addIsNullParam("typeId");
            cc.addSetOrderColum("id", "desc");
        }
        cc.addEqParam("isShow", ConstantsCommon.Use_Type.USED);
        cc.addPageParam(1, 6);
        return yxlDocSearchDao.queryForListNoPage(cc);
    }
    
    /**
     * 查询首页文章列表视图
     * @return
     */
    public List<DocVO> queryTitlelist(){
    	List<YxlDocSearch> docList=queryTitleYxlList(false);
    	List<DocVO> list=new ArrayList<DocVO>();
    	for(YxlDocSearch search:docList){
    		DocVO vo=new DocVO();
    		vo.setId(search.getDocId());
    		vo.setTitle(search.getTitle());
    		String str="select t.id,t.tip_name as tipName from yxl_doc_tip_config c left join yxl_tip t on c.tip_id=t.id where c.doc_id="+search.getDocId();
    		List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
    		vo.setTipList(mapList);
    		list.add(vo);
    	}
    	return list;
    }
    
    /**
     * 查询一系列的类型及数量
     * @return
     */
    public List<Map<String, Object>> queryyxlType(){
    	String str="select t.id, t.name name,count(0) num from yxl_type t left join yxl_doc_search s on s.type_id=t.id where s.specal_type !='1' group by t.name , t.id order by t.id";
    	List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
    	return mapList;
    }
    public List<Map<String, Object>> queryyxlTypeList(String name,Integer pageNo,Integer pageSize){
    	String str="select s.id,s.title,s.general  from yxl_doc_search s left join yxl_type y on s.type_id=y.id where y.name='"+name+"' order by s.id ";
    	if(pageNo!=null&&pageNo>0){
    		str+=" limit "+(pageNo-1)*pageSize+", "+pageSize;
    	}
    	List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
    	return mapList;
    }
    
    
    
    public List<Map<String, Object>> queryListTitle(){
    	String str="select t.id,t.name,s.doc_id docId,s.type_id typeId,s.title from yxl_type t left join yxl_doc_search s on t.id=s.type_id where t.parent_id=71 order by s.id ";
    	List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
    	return mapList;
    }
    
    public List<Map<String, Object>> queryListType(Integer parentId){
    	String str="select * from yxl_type ";
    	if(parentId!=null){
    		str+=" where parent_id="+parentId;
    	}
    	str+=" order by id ";
    	List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
    	return mapList;
    }
    
    
    /**
     * 查询文章列表视图
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<DocListVO> queryDocList(Map<String, Object> paramMap, int page, int size,String[] tipName,String searchValue) {
        PageList<Map<String, Object>> plist;
        String ss;
        if(tipName!=null&&tipName.length>0){
            ss="select s.id,s.doc_id docId,s.title,s.general,s.type_id typeId,s.is_show isShow,s.create_date createDate from yxl_doc_tip_config c"+ 
                    " left join yxl_tip t on c.tip_id=t.id "+
                    " left join yxl_doc_search s on c.doc_id=s.doc_id"+
                    " where s.is_show='1' and s.specal_type='0' and t.tip_name in ";
            String tips="";
            for(String t:tipName){
            	tips+="'"+t+"',";
            }
            tips=tips.substring(0, tips.length()-1);
            ss+=" ("+tips+") ";
            
            if(StringUtil.IsNotEmpty(searchValue)){
                ss+=" and s.general like '%"+searchValue+"%'";
            }
            ss+=" order by s.id desc ";
        }else{
            ss="select s.id,s.doc_id docId,s.title,s.general,s.type_id typeId,s.is_show isShow,s.create_date createDate from yxl_doc_search s where s.is_show='1' and s.specal_type='0'";
            if(StringUtil.IsNotEmpty(searchValue)){
                ss+=" and s.general like '%"+searchValue+"%'";
            }
            ss+=" order by s.id desc ";
        }
        plist=yxlDocSearchDao.querySqlForPage(ss, paramMap, page, size);
        DocListVO vo;
        String sql;
        List<Map<String, Object>>  tipNameList;
        List<String> tipList;
        List<DocListVO> voList=new ArrayList<DocListVO>();
        for(Map<String, Object> search:plist.getList()){
            vo=new DocListVO();
            vo.setId(Long.parseLong(search.get("id").toString()));
            vo.setCreateDate(DateUtil.toStringString1(search.get("createDate").toString()));
            vo.setDocId(Long.parseLong(search.get("docId").toString()));
            vo.setGeneral(search.get("general")==null?"":search.get("general").toString());
            vo.setTitle(search.get("title").toString());
            vo.setTypeId(search.get("typeId")==null?null:Long.parseLong(search.get("typeId").toString()));
            vo.setUserId(search.get("userId")==null?null:Long.parseLong(search.get("userId").toString()));
            vo.setView(0);
            sql="select t.tip_name as tipName from yxl_doc_tip_config c left join yxl_tip t on t.id=c.tip_id where c.doc_id="+Long.parseLong(search.get("docId").toString());
            tipNameList=yxlTipDao.querySqlByParamForMap(sql, null);
            tipList=new ArrayList<String>();
            for(Map<String, Object> m:tipNameList){
                tipList.add(m.get("tipName").toString());
            }
            vo.setTipList(tipList);
            voList.add(vo);
        }
        PageList<DocListVO> voPage=new PageList<DocListVO>();
        voPage.setTotal(plist.getTotal());
        voPage.setList(voList);
        return voPage;
    }
    
    /**
     * 图表计算
     * @param start 当月的第一天
     * @param end 下月的第一天
     * @param userId
     * @return
     */
    public Map<String, Object> queryDocAccountMonth(String start ,String end,Long userId){
        String str="select a.num as uxl,b.num as xl from "+
                "(select count(id) as num from yxl_doc_search where create_date>='"+start+"' and create_date <'"+end+"' and user_id="+userId+" and type_id is null ) a ,"+
                "(select count(id) as num from yxl_doc_search where create_date>='"+start+"' and create_date <'"+end+"' and user_id="+userId+" and type_id is not null ) b";
        List<Map<String, Object>> ll=yxlDocSearchDao.querySqlByParamForMap(str, null);
        return ll.get(0);
    }
    
    /**
     * 批量设置展示或不展示
     * @param isShow
     * @param ids
     * @return
     */
    public int updateSearchSetShow(String isShow,Long[] ids){
        ConditionsCommon cc=new ConditionsCommon();
        cc.addInParam("id", ids);
        if(ConstantsCommon.Use_Type.USED.equals(isShow)){
            cc.addsetObjectParam("isShow",ConstantsCommon.Use_Type.USED);            
        }else{
            cc.addsetObjectParam("isShow",ConstantsCommon.Use_Type.UN_USE);
        }
        return yxlDocSearchDao.update(cc);
    }
    
    
    
    
    
}
