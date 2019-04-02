package com.njq.xs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.model.po.XsDocUserOp;
@Service
public class XsDocUserOpService {

    @Resource
    private DaoCommon<XsDocUserOp> docUserOpDao;

    /**
     * 查询列表（分页）
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public PageList<XsDocUserOp> queryPageList(Map<String, Object> paramMap, int page, int size) {
        return null;
    }

    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<XsDocUserOp> queryList(Map<String, Object> paramMap) {
        return null;
    }

    /**
     * 根据id查询对象
     * @param docId
     * @return
     */
    public XsDocUserOp queryById(Long docId) {
        return docUserOpDao.queryTById(docId);
    }

    public XsDocUserOp queryByCon(ConditionsCommon con) {
    	return docUserOpDao.queryTByParamForOne(con);
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
    public int saveObject(XsDocUserOp t) {
        docUserOpDao.save(t);
        return 1;
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
     * 查询文章的用户操作数
     * @param docId
     * @return
     */
    public Map<Integer, Integer> queryDocOpNum(Long docId) {
        String sql="select count(id) num,op from xs_doc_user_op where doc_id='"+docId+"'  group by op";
        List<Map<String, Object>> list=docUserOpDao.querySqlByParamForMap(sql,null);
        Map<Integer, Integer> r=new HashMap<Integer, Integer>();
        for(Map<String, Object> m : list){
            r.put(Integer.parseInt(m.get("op").toString()), Integer.parseInt(m.get("num").toString()));
        }
        return r;
    }

    /**
     * 查询用户的操作信息数
     * @param op
     * @param ip
     * @param docId
     * @param appId
     * @return
     */
    public int queryCount(String op, String ip, Long docId,String appId) {
        String sql="select count(id) num from xs_doc_user_op where doc_id='"+docId+"' and user_ip='"+ip+"' and op='"+op+"' and app_id='"+appId+"'";
        List<Map<String, Object>> list=docUserOpDao.querySqlByParamForMap(sql,null);
        return Integer.parseInt(list.get(0).get("num").toString());
    }

    /**
     * 查询用户对当前文章的操作
     * @param ip
     * @param docId
     * @return
     */
    public List<XsDocUserOp> queryByDocIdAndIp(String ip, Long docId) {
        ConditionsCommon cc=new ConditionsCommon();
        cc.addEqParam("userIp", ip);
        cc.addEqParam("docId", docId);
        return docUserOpDao.queryForListNoPage(cc);
    }
    
}
