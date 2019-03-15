package com.njq.grab.service.operation;

import com.njq.common.model.po.GrabDoc;
import com.njq.common.model.ro.GrabDocSaveRequest;

/**
 * @author: nijiaqi
 * @date: 2019/3/15
 */
public interface GrabDocUpdateOperation {
    GrabDoc updateDoc(GrabDocSaveRequest request);
}
