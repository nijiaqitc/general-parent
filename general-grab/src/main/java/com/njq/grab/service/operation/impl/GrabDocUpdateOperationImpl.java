package com.njq.grab.service.operation.impl;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.ro.GrabDocSaveRequest;
import com.njq.grab.service.operation.GrabDocUpdateOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
@Component
public class GrabDocUpdateOperationImpl implements GrabDocUpdateOperation {
    private final DaoCommon<GrabDoc> grabDocDao;

    @Autowired
    public GrabDocUpdateOperationImpl(DaoCommon<GrabDoc> grabDocDao) {
        this.grabDocDao = grabDocDao;
    }

    @Override
    public GrabDoc updateDoc(GrabDocSaveRequest request) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setId(request.getId());
        grabDoc.setDoc(request.getDoc());
        grabDoc.setTitle(request.getTitle());
        grabDocDao.updateByPrimaryKeySelective(grabDoc);
        return grabDoc;
    }
}
