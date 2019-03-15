package com.njq.grab.service.operation;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.ro.GrabDocSaveRequest;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public interface GrabDocSaveOperation {

    GrabDoc saveDoc(GrabDocSaveRequest request);
}
