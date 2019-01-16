package com.njq.basis.service;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.request.SaveTitleRequest;
import com.njq.common.model.po.BaseTitle;

import java.util.List;

public interface SaveTitlePerformer {
    /**
     * 保存标题
     *
     * @param request
     * @return
     */
    public BaseTitle saveTitle(SaveTitleRequest request);

    /**
     * 修改标题
     *
     * @param request
     * @return
     */
    public BaseTitle updateTitle(SaveTitleRequest request);

    /**
     * 加载成功后修改标题对应的值
     *
     * @param docId
     * @param id
     * @return
     */
    public void updateTitleOnLoadSuccess(Long docId, Long id);

    /**
     * 根据id获取标题
     *
     * @param id
     * @return
     */
    public BaseTitle getTitleById(Long id);

    /**
     * 根据docId获取标题
     * @param docId
     * @return
     */
    public BaseTitle getTitleByDocId(Long docId);

    /**
     * 获取标题列表
     *
     * @param docId
     * @return
     */
    public List<BaseTitle> getTitleList(Long docId, String channel);

    /**
     * 获取子菜单数量
     *
     * @param docId
     * @param channel
     * @return
     */
    public int getChildrenCount(Long docId, String channel);

    /**
     * 根据条件修改标题
     * @param conditionsCommon
     */
    public void updateByParam(ConditionsCommon conditionsCommon);
}
