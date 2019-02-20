package com.njq.tbk.service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.ConstantsCommon.Doc_Type;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.LogCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.dao.TbkDocJpaRepository;
import com.njq.common.model.po.TbkDoc;
import com.njq.common.model.po.TbkDocPicConfig;
import com.njq.common.model.po.TbkDocTipConfig;
import com.njq.common.model.po.TbkPic;
import com.njq.common.model.po.TbkRecommendDocView;
import com.njq.common.model.po.TbkTip;
import com.njq.common.model.po.TbkType;
import com.njq.common.model.po.TbkTypeDocConfig;
import com.njq.common.model.po.TbkTypePicConfig;
import com.njq.common.util.rely.APIUtil;
import com.njq.common.util.string.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TbkDocService {

    @Resource
    private DaoCommon<TbkDoc> tbkdocDao;
    @Resource
    private DaoCommon<TbkType> tbktypeDao;
    @Resource
    private DaoCommon<TbkTip> tbktipDao;
    @Resource
    private DaoCommon<TbkPic> tbkpicDao;
    @Resource
    private DaoCommon<TbkDocPicConfig> tbkdocpicconfigDao;
    @Resource
    private DaoCommon<TbkDocTipConfig> tbkdoctipconfigDao;
    @Resource
    private DaoCommon<TbkTypeDocConfig> tbktypedocconfigDao;
    @Resource
    private DaoCommon<TbkTypePicConfig> tbktypepicconfigDao;
    @Resource
    private DaoCommon<TbkRecommendDocView> tbkRecommendDocViewDao;
    @Resource
    private TbkTipService tbktipService;
    @Resource
    private TbkDocJpaRepository tbkDocJpaRepository;

    /**
     * 查询所有文档
     *
     * @return
     */
    public List<TbkDoc> queryAllDoc() {
        return tbkdocDao.queryTByParam(null);
    }

    /**
     * 查询所有文档信息（分页）
     *
     * @param paramMap
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PageList<TbkDoc> queryAllDoc(Map<String, Object> paramMap, Long userId, int page,
                                        int size) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
        if (!ConstantsCommon.Oper_User.ADMIN.equals(userId)) {
            cc.addEqParam("createBy", userId);
        }
        if (paramMap.get("value") != null) {
            cc.addMoreColumLikeParam(paramMap.get("value").toString(), "account", "userName", "tel");
        }
        cc.addSetOrderColum("id", "desc");
//		APIUtil.requestForCheck(cc);
        return tbkdocDao.queryForPage(cc);
    }

    /**
     * 根据id查询文章
     *
     * @param id
     * @return
     */
    public TbkDoc queryDocById(Long id) {
        return tbkdocDao.queryTById(id);
    }

    /**
     * 根据id查询文章，但需要区分url地址
     *
     * @param id
     * @return
     */
    public TbkDoc queryDocByUrlAndId(Long id) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("id", id);
        requestForCheck(cc);
        return tbkdocDao.queryTByParamForOne(cc);
    }

    /**
     * 保存文章
     *
     * @param doc
     * @param typeId
     * @param picId
     * @param tips
     * @param userId
     * @param map
     */
    public void saveDoc(TbkDoc doc, Long typeId, Long picId, String[] tips, Long userId,
                        Map<String, Object> map) {
        Random random = new Random();
        doc.setReadnums(random.nextInt(200));
        //保存文章
        tbkdocDao.save(doc);
        TbkDocPicConfig docpic = new TbkDocPicConfig();
        docpic.setDocId(doc.getId());
        docpic.setPicId(picId);
        docpic.setCreateBy(userId);
        docpic.setModiBy(userId);
        docpic.setCreateDate(new Date());
        //保存文章图片关联内容
        tbkdocpicconfigDao.save(docpic);
        TbkTypeDocConfig typedoc = new TbkTypeDocConfig();
        typedoc.setDocId(doc.getId());
        typedoc.setTypeId(typeId);
        typedoc.setCreateBy(userId);
        typedoc.setModiBy(userId);
        typedoc.setCreateDate(new Date());
        tbktypedocconfigDao.save(typedoc);
        for (String tipName : tips) {
            if (tipName == null || "".equals(tipName)) {
                continue;
            }
            TbkTip tip = tbktipService.queryTbktipByName(tipName);
            if (tip == null) {
                tip = new TbkTip();
                tip.setName(tipName);
                tip.setCreateBy(userId);
                tip.setModiBy(userId);
                tip.setInTurn(1L);
                tip.setCreateDate(new Date());
                tbktipDao.save(tip);
            }
            TbkDocTipConfig config = new TbkDocTipConfig();
            config.setTipId(tip.getId());
            config.setDocId(doc.getId());
            config.setCreateBy(userId);
            config.setModiBy(userId);
            config.setCreateDate(new Date());
            tbkdoctipconfigDao.save(config);
        }
        //日志记录
        LogCommon.saveLog(userId, "新增", "tbk文章表", "对行" + doc.getId() + "进行增加");
        MessageCommon.getSuccessMap(map);
    }


    /**
     * 修改文章
     *
     * @param doc
     * @param typeId
     * @param picId
     * @param tips
     * @param userId
     * @param map
     */
    public void updateDoc(TbkDoc doc, Long typeId, Long picId, String[] tips,
                          Long userId, Map<String, Object> map) {
        TbkDoc d = tbkdocDao.queryTById(doc.getId());
        d.setGeneral(doc.getGeneral());
        d.setText(doc.getText());
        d.setTitle(doc.getTitle());
        d.setReadtype(doc.getReadtype());
        //保存修改的文章
        tbkdocDao.update(d);
        if (picId != null) {
            ConditionsCommon cc = new ConditionsCommon();
            cc.addEqParam("docId", doc.getId());
            TbkDocPicConfig picFig = tbkdocpicconfigDao.queryTByParamForOne(cc);
            if (!picFig.getPicId().equals(picId)) {
                picFig.setPicId(picId);
                //保存修改的图片
                tbkdocpicconfigDao.update(picFig);
            }
        }
        ConditionsCommon cc1 = new ConditionsCommon();
        cc1.addEqParam("docId", doc.getId());
        TbkTypeDocConfig typeFig = tbktypedocconfigDao.queryTByParamForOne(cc1);
        if (!typeFig.getId().equals(typeId)) {
            typeFig.setTypeId(typeId);
            //保存修改的类型
            tbktypedocconfigDao.update(typeFig);
        }
        //先批量删除文章所对应的原标签
        tbkdoctipconfigDao.delT(new Long[]{doc.getId()}, "docId");
        //再批量生成新的标签
        for (String tipName : tips) {
            if (tipName == null || "".equals(tipName)) {
                continue;
            }
            TbkTip tip = tbktipService.queryTbktipByName(tipName);
            if (tip == null) {
                tip = new TbkTip();
                tip.setName(tipName);
                tip.setCreateBy(userId);
                tip.setModiBy(userId);
                tip.setInTurn(1L);
                tip.setCreateDate(new Date());
                tbktipDao.save(tip);
            }
            TbkDocTipConfig config = new TbkDocTipConfig();
            config.setTipId(tip.getId());
            config.setDocId(doc.getId());
            config.setCreateBy(userId);
            config.setModiBy(userId);
            config.setCreateDate(new Date());
            tbkdoctipconfigDao.save(config);
        }
        MessageCommon.getSuccessMap(map);
    }

    /**
     * 根据id修改文章，阅读数+1
     *
     * @param id
     * @param map
     */
    public void updateDocById(Long id,
                              Map<String, Object> map) {
        tbkDocJpaRepository.updateForAddNum(id);
    }

    /**
     * 删除文章
     *
     * @param ids
     * @param userId
     * @param map
     */
    @SuppressWarnings("unused")
    public void deleteDocById(Long[] ids, Long userId,
                              Map<String, Object> map) {
        int num = tbkdocDao.delT(ids);
        int picNum = tbkdocpicconfigDao.delT(ids, "docId");
        int docNum = tbkdoctipconfigDao.delT(ids, "docId");
        int typeNum = tbktypedocconfigDao.delT(ids, "docId");
        if (num > 0) {
            MessageCommon.getSuccessMap(map);
            //日志记录
            LogCommon.saveLog(userId, "删除", "tbk文章表", "对行" + StringUtil.LongToString(ids) + "进行删除");
        } else {
            MessageCommon.getFalseMap(map);
            map.put("message", "请检查数据是否正确，再进行操作！");
        }
    }


    /**
     * 查询推荐的文章
     *
     * @param paramMap
     * @param page
     * @param size
     * @return
     */
    public List<TbkRecommendDocView> queryRecommendDoc(Map<String, Object> paramMap,
                                                       int page, int size) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        cc.addSetOrderColum("readnums", "desc");
        cc.addSetOrderColum("id", "desc");
        requestForCheck(cc);
        return tbkRecommendDocViewDao.queryForPage(cc).getList();
    }


    /**
     * 模糊搜索文章(返回分页)
     *
     * @param page
     * @param size
     * @param searchValue
     * @return
     */
    public PageList<TbkRecommendDocView> querySearchDoc(int page, int size,
                                                        String[] searchValue) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String str = "";
        for (String s : searchValue) {
            str += "%" + s + "%";
        }
        String hql = "select new com.njq.common.model.po.TbkRecommendDocView(id,title,general,readnums,createDate,typeName,url,tips,reprint) from "
                + "TbkRecommendDocView where ( general like :general or title like :title or tips like :tips )";
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        paramMap.put("general", str);
        paramMap.put("title", str);
        paramMap.put("tips", str);
        /*
         *	根据请求类型查询对应的文章
         */
        if (APIUtil.checkRequestUri()) {
            hql += " and readtype=:readType ";
            paramMap.put("readType", Doc_Type.NORMAL);
        }
        PageList<TbkRecommendDocView> pageList = (PageList<TbkRecommendDocView>) tbkRecommendDocViewDao.queryHqlByParamForPage(hql, cc, paramMap);
        for (TbkRecommendDocView view : pageList.getList()) {
            view.setSearchValue(searchValue);
        }
        return pageList;
    }

    /**
     * 模糊搜索文章(返回集合)
     *
     * @param page
     * @param size
     * @param searchValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TbkRecommendDocView> querySearchDocForList(int page, int size,
                                                           String[] searchValue) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String str = "";
        for (String s : searchValue) {
            str += "%" + s + "%";
        }
        String hql = "select new com.njq.common.model.po.TbkRecommendDocView(id,title,general,readnums,createDate,typeName,url,tips,reprint,userName) from "
                + "TbkRecommendDocView where ( general like :general or title like :title ) ";
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        paramMap.put("general", str);
        paramMap.put("title", str);
        /*
         *	根据请求类型查询对应的文章
         */
        if (APIUtil.checkRequestUri()) {
            hql += " and readtype=:readType ";
            paramMap.put("readType", Doc_Type.NORMAL);
        }
        List<TbkRecommendDocView> list = (List<TbkRecommendDocView>) tbkRecommendDocViewDao.queryHqlByParamForLimit(hql, cc, paramMap);
        for (TbkRecommendDocView view : list) {
            view.setSearchValue(searchValue);
        }
        return list;
    }

    /**
     * 根据类型搜索文章
     *
     * @param page
     * @param size
     * @param typeId
     * @return
     */
    public PageList<TbkRecommendDocView> queryDocForType(int page, int size,
                                                         Long typeId) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        if (StringUtil.IsNotEmpty(typeId)) {
            cc.addEqParam("typeId", typeId);
        }
        cc.addSetOrderColum("id", "desc");
        requestForCheck(cc);
        return tbkRecommendDocViewDao.queryForPage(cc);
    }


    /**
     * 根据类型搜索文章
     *
     * @param page
     * @param size
     * @param typeName
     * @return
     */
    public List<TbkRecommendDocView> queryDocForTypeList(int page, int size,
                                                         String typeName) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        if (StringUtil.IsNotEmpty(typeName)) {
            cc.addEqParam("typeName", typeName);
        }
        cc.addSetOrderColum("id", "desc");
        requestForCheck(cc);
        return tbkRecommendDocViewDao.queryForListNoPage(cc);
    }

    /**
     * 查询对应类型所包含的文章数目
     *
     * @param page
     * @param size
     * @return
     */
    public List<Map<String, Object>> queryTypeVO(int page, int size) {
        String sql = " select t.id,t.name , count(c.typeId) num from tbk_type t left join tbk_type_doc_config c on t.id=c.typeId where t.status=1 and c.status=1 group by t.id ,t.name order by num desc,t.id   ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        ConditionsCommon cc = new ConditionsCommon();
        cc.addPageParam(page, size);
        List<Map<String, Object>> list = tbkRecommendDocViewDao.querySqlByParamForMap(sql, paramMap, cc);
        return list;
    }

    /**
     * 查询推荐的文章(不包含当前文章)
     *
     * @param paramMap
     * @param docId
     * @param page
     * @param size
     * @return
     */
    public List<TbkRecommendDocView> queryRecommendDoc(
            Map<String, Object> paramMap, Long docId, int page, int size) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addNotEqParam("id", docId);
        cc.addPageParam(page, size);
        cc.addSetOrderColum("readnums", "desc");
        cc.addSetOrderColum("id", "desc");
        requestForCheck(cc);
        return tbkRecommendDocViewDao.queryForPage(cc).getList();
    }


    public static void requestForCheck(ConditionsCommon cc) {
        if (APIUtil.getUrl()) {
            cc.addEqParam("readtype", ConstantsCommon.Doc_Type.NORMAL);
        }
    }

}
