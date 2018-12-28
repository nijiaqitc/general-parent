package com.njq.yxl.service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.YxlColumnDefine;
import com.njq.common.model.po.YxlColumnStore;
import com.njq.common.model.po.YxlTypeName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class YxlColumnService {
    @Resource
    private DaoCommon<YxlTypeName> yxlTypeNameDao;
    @Resource
    private DaoCommon<YxlColumnDefine> yxlColumnDefineDao;
    @Resource
    private DaoCommon<YxlColumnStore> yxlColumnStoreDao;

    public void saveDefine(List<String> columns, String typeName, Long parentId) {
        YxlTypeName type = new YxlTypeName();
        type.setName(typeName);
        type.setParentId(parentId);
        type.setCreateDate(new Date());
        type.setStatus(1);
        type.setType(2);
        yxlTypeNameDao.save(type);

        String st;
        YxlColumnDefine define;
        List<YxlColumnDefine> defineList = new ArrayList<YxlColumnDefine>();
        for (int i = 0; i < columns.size(); i++) {
            define = new YxlColumnDefine();
            st = columns.get(i);
            define.setDefineName(st);
            define.setRecordType(type.getId());
            define.setStoreName("col" + i);
            define.setCreateDate(new Date());
            defineList.add(define);
        }
        yxlColumnDefineDao.saveList(defineList);

    }


    public void saveStore(YxlColumnStore store, Long recordType, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            switch (i + 1) {
                case 1:
                    store.setCol1(strings[i]);
                    break;
                case 2:
                    store.setCol2(strings[i]);
                    break;
                case 3:
                    store.setCol3(strings[i]);
                    break;
                case 4:
                    store.setCol4(strings[i]);
                    break;
                case 5:
                    store.setCol5(strings[i]);
                    break;
                case 6:
                    store.setCol6(strings[i]);
                    break;
                case 7:
                    store.setCol7(strings[i]);
                    break;
                case 8:
                    store.setCol8(strings[i]);
                    break;
                case 9:
                    store.setCol9(strings[i]);
                    break;
                case 10:
                    store.setCol10(strings[i]);
                    break;
                default:
                    break;
            }
            store.setCreateDate(new Date());
            store.setRecordType(recordType);
        }
        yxlColumnStoreDao.update(store);
    }

    public List<YxlColumnStore> queryStoreList(Long type) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("record_type", type);
        cc.addSetOrderColum("id", "desc");
        return yxlColumnStoreDao.queryForListNoPage(cc);
    }


    public List<YxlTypeName> queryNameList(Long parentId) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("parent_id", parentId);
        cc.addSetOrderColum("id", "desc");
        return yxlTypeNameDao.queryForListNoPage(cc);
    }

    public List<YxlColumnDefine> queryDefineList(Long type) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("record_type", type);
        return yxlColumnDefineDao.queryForListNoPage(cc);
    }


    public int deleteStore(Long id) {
        return yxlColumnStoreDao.delTRealById(id);
    }

    public Map<String, Object> deleteDefine(Long id) {
        ConditionsCommon cc = new ConditionsCommon();
        cc.addEqParam("record_type", id);
        int storeNum = yxlColumnStoreDao.queryForCount(cc);
        if (storeNum > 0) {
            return MessageCommon.getFalseMap("有记录，不能删除！");
        }
        storeNum = yxlColumnDefineDao.deleteBycc(cc);
        if (storeNum == 0) {
            return MessageCommon.getFalseMap("删除失败，请刷新！");
        }
        storeNum = yxlTypeNameDao.delTRealById(id);
        if (storeNum == 0) {
            return MessageCommon.getFalseMap("删除失败，请刷新！");
        }
        return MessageCommon.getSuccessMap();

    }

}
