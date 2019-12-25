package com.njq.grab.service.operation.impl;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.ro.GrabDocSaveRequest;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.vdurmont.emoji.EmojiParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
@Component
public class GrabDocSaveOperationImpl implements GrabDocSaveOperation {
    private final DaoCommon<GrabDoc> grabDocDao;

    @Autowired
    public GrabDocSaveOperationImpl(DaoCommon<GrabDoc> grabDocDao) {
        this.grabDocDao = grabDocDao;
    }

    @Override
    public GrabDoc saveDoc(GrabDocSaveRequest request) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setChannel(request.getChannel());
        grabDoc.setCreateDate(new Date());
        grabDoc.setDoc(EmojiParser.removeAllEmojis(request.getDoc()));
        grabDoc.setTitle(request.getTitle());
        grabDocDao.save(grabDoc);
        return grabDoc;
    }

}
