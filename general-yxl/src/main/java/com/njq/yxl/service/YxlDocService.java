package com.njq.yxl.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlDoc;
import com.njq.common.model.po.YxlDocSearch;
import com.njq.common.model.po.YxlDocTipConfig;
import com.njq.common.model.po.YxlTip;
import com.njq.common.model.po.YxlType;
import com.njq.common.util.string.StringUtil;
@Service
public class YxlDocService {

    @Resource
    private DaoCommon<YxlDoc> yxlDocDao;
    @Resource
    private DaoCommon<YxlType> yxlTypeDao;
    @Resource
    private DaoCommon<YxlDocSearch> yxlDocSearchDao;
    @Resource
    private DaoCommon<YxlTip> yxlTipDao;
    @Resource
    private DaoCommon<YxlDocTipConfig> yxlDocTipConfigDao; 
    @Resource
    private YxlDocSearchService yxlDocSearchService;
    
    /**
     * 查询标签集合
     * @return
     */
    public List<YxlTip> queryTipList(){
        ConditionsCommon cc=new ConditionsCommon();
        cc.addSetOrderColum("id", "asc");
        return yxlTipDao.queryForListNoPage(cc);
    }
    
    /**
     * 查询文章对应的标签集合
     * @return
     */
    public List<YxlTip> queryDocTipList(Long docId){
        ConditionsCommon cc=new ConditionsCommon();
        cc.addSetOrderColum("id", "asc");
        String str="select t.id,t.tip_name as tipName from yxl_doc_tip_config c left join yxl_tip t on c.tip_id=t.id where c.doc_id="+docId;
        List<Map<String, Object>>  mapList=yxlDocSearchDao.querySqlByParamForMap(str, null);
        List<YxlTip> tipList=new ArrayList<YxlTip>();
        for(Map<String, Object> m:mapList){
            YxlTip tip=new YxlTip();
            tip.setId(Long.parseLong(m.get("id").toString()));
            tip.setTipName(m.get("tipName").toString());
            tipList.add(tip);
        }
        return tipList;
    }
    
    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<YxlDoc> queryPageList(Map<String, Object> paramMap, int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<YxlDoc> queryList(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public YxlDoc queryById(Long id) {
        return yxlDocDao.queryTById(id);
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
     */
    public void saveObject(YxlDoc t) {
        yxlDocDao.save(t);
    }

    /**
     * 根据id删除对象
     * @param ids
     * @return
     */
    public int deleteById(Long ids) {
        return yxlDocDao.delTRealById(ids);
    }

    /**
     * 根据id集合删除对象集合
     * @param ids
     * @return
     */
    public int deleteByIds(Long[] ids) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addInParam("docId", ids);
        yxlDocSearchDao.deleteBycc(cc);
        ConditionsCommon ccon=new ConditionsCommon();
        ccon.addInParam("docId", ids);
        yxlDocTipConfigDao.deleteBycc(ccon);
        return yxlDocDao.delT(ids);
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
     * 查询对应类型下的所有标题
     * @param typeId
     * @return
     */
    public List<YxlDoc> queryTitleList(Long typeId) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addSeleParam("id", "id");
        cc.addSeleParam("title", "title");
        cc.addEqParam("typeId", typeId);
        List<YxlDoc> docList =yxlDocDao.queryForListNoPage(cc);
        return docList;
    }

    /**
     * 保存文章
     * @param name
     * @param t
     */
    public Map<String, Object> saveDoc(String name,Long typeId,YxlDoc t,String isShow,String[] tips,
            Long userId,String general,String specalType) {
        Map<String, Object> m=new HashMap<String, Object>();
        Date day=new Date();
    	t.setCreateDate(day);
    	t.setStatus(ConstantsCommon.Del_Status.YES);
    	t.setUserId(userId);
    	//保存文章
    	yxlDocDao.save(t);
    	m.put("docId", t.getId());
    	//针对类型的操作
    	if(name!=null&&name!=""){
    	    ConditionsCommon c1=new ConditionsCommon();
    	    c1.addEqParam("name", name);
    	    List<YxlType> typeList=yxlTypeDao.queryForListNoPage(c1);
    	    if(typeList.size()>0){
    	        typeId=typeList.get(0).getId();
    	    }else{
    	        YxlType type=new YxlType();
    	        type.setName(name);
    	        type.setStatus(ConstantsCommon.Del_Status.YES);
    	        type.setCreateDate(day);
    	        //类型
    	        yxlTypeDao.save(type);
    	        typeId=type.getId();    	        
    	    }
    	}
    	
    	YxlDocSearch search=new YxlDocSearch();
    	search.setIsShow(isShow);
    	search.setDocId(t.getId());
    	//概要内容
    	if(general.length()>180){
    	    search.setGeneral(general.substring(0, 180));
    	}else{
    	    search.setGeneral(general);    	    
    	}
    	search.setCreateDate(day);
    	search.setView(0);
    	search.setTypeId(typeId);
    	search.setTitle(t.getTitle());
    	search.setUserId(userId);
    	if(specalType==null){
    	    search.setSpecalType("0");    	    
    	}else{
    	    search.setSpecalType(specalType);
    	}
    	//保存查询类型
    	yxlDocSearchDao.save(search);
    	//针对标签的操作
    	if(tips.length>0){
    	    ConditionsCommon cc=new ConditionsCommon();
    	    cc.addInParam("tipName", tips);
    	    List<YxlTip> tipList=yxlTipDao.queryForListNoPage(cc);
    	    List<YxlTip> saveList=new ArrayList<YxlTip>(); 
    	    boolean flag=false;
    	    for(String tipName:tips){
    	        for(YxlTip tempTip:tipList){
    	            if(tipName.equals(tempTip.getTipName())){
    	                flag=true;
    	                break;
    	            }
    	        }
    	        if(flag==false){
    	            YxlTip tip=new YxlTip();
    	            tip.setCreateDate(day);
    	            tip.setTipName(tipName);
    	            saveList.add(tip);
    	        }else{
    	            flag=false;    	            
    	        }
    	    }
    	    if(saveList.size()>0){
    	        //保存标签
    	        yxlTipDao.saveList(saveList);
    	    }
    	    saveList.addAll(tipList);
    	    for(YxlTip tt:saveList){
    	        YxlDocTipConfig config=new YxlDocTipConfig();
    	        config.setCreateDate(day);
    	        config.setDocId(t.getId());
    	        config.setTipId(tt.getId());
    	        //保存关联
    	        yxlDocTipConfigDao.save(config);
    	    }
    	}
    	MessageCommon.getSuccessMap(m);
    	return m;
    }

    /**
     * 修改文章内容
     * @param name
     * @param typeId
     * @param t
     * @param isShow
     * @param tips
     * @param userId
     * @param general
     * @return
     */
    public Map<String, Object> updateDoc(String name,Long typeId,YxlDoc t,String isShow,
            String[] tips,Long userId,String general,String specalType) {
        Map<String, Object> m=new HashMap<String, Object>();
        YxlDoc oldDoc=yxlDocDao.queryTById(t.getId());
        oldDoc.setCss(t.getCss());
        oldDoc.setText(t.getText());
        oldDoc.setTitle(t.getTitle());
        oldDoc.setModiDate(new Timestamp(System.currentTimeMillis()));
        //先修改文章内容
        yxlDocDao.update(oldDoc);
        Date day=new Date();
        ConditionsCommon conc=new ConditionsCommon();
        conc.addEqParam("docId", oldDoc.getId());
        YxlDocSearch search=yxlDocSearchDao.queryTByParamForOne(conc);
        m.put("docId", t.getId());
        //针对类型的操作
        if(StringUtil.IsNotEmpty(name)){
            ConditionsCommon c1=new ConditionsCommon();
            c1.addEqParam("name", name);
            List<YxlType> typeList=yxlTypeDao.queryForListNoPage(c1);
            if(typeList.size()>0){
                typeId=typeList.get(0).getId();
            }else{
                YxlType type=new YxlType();
                type.setName(name);
                type.setStatus(ConstantsCommon.Del_Status.YES);
                type.setCreateDate(day);
                //类型
                yxlTypeDao.save(type);
                typeId=type.getId();  
            }
        }
        //概要内容
        if(general.length()>180){
            search.setGeneral(general.substring(0, 180));
        }else{
            search.setGeneral(general);         
        }
        if(specalType==null){
            search.setSpecalType("0");            
        }else{
            search.setSpecalType(specalType);
        }
        search.setIsShow(isShow);
        search.setTypeId(typeId);
        search.setTitle(t.getTitle());
        search.setModiDate(new Timestamp(System.currentTimeMillis()));
        //修改查询类型
        yxlDocSearchDao.update(search);
        //先将关联的tip标签全部删除掉
        ConditionsCommon ccon=new ConditionsCommon();
        ccon.addEqParam("docId", oldDoc.getId());
        yxlDocTipConfigDao.deleteBycc(ccon);
        //针对标签的操作
        if(tips.length>0){
            ConditionsCommon cc=new ConditionsCommon();
            cc.addInParam("tipName", tips);
            List<YxlTip> tipList=yxlTipDao.queryForListNoPage(cc);
            List<YxlTip> saveList=new ArrayList<YxlTip>(); 
            boolean flag=false;
            for(String tipName:tips){
                for(YxlTip tempTip:tipList){
                    if(tipName.equals(tempTip.getTipName())){
                        flag=true;
                        break;
                    }
                }
                if(flag==false){
                    YxlTip tip=new YxlTip();
                    tip.setCreateDate(day);
                    tip.setTipName(tipName);
                    saveList.add(tip);
                }else{
                    flag=false;                 
                }
            }
            if(saveList.size()>0){
                //保存标签
                yxlTipDao.saveList(saveList);
            }
            saveList.addAll(tipList);
            for(YxlTip tt:saveList){
                YxlDocTipConfig config=new YxlDocTipConfig();
                config.setCreateDate(day);
                config.setDocId(t.getId());
                config.setTipId(tt.getId());
                //保存关联
                yxlDocTipConfigDao.save(config);
            }
        }
        MessageCommon.getSuccessMap(m);
        return m;
    }
	/**
	 * 查询类型的标题
	 * @return
	 */
	public List<YxlType> queryTypeTitleList() {
		ConditionsCommon cc=new ConditionsCommon();
//        cc.addSeleParam("id", "id");
//        cc.addSeleParam("name", "name");
        cc.addSetOrderColum("id", "asc");
		return yxlTypeDao.queryTByParam(cc);
	}

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public YxlType queryTypeById(Long id){
	    return yxlTypeDao.queryTById(id);
	}
    
}
