package com.njq.grab.service;

import com.njq.common.model.ro.AnalysisPageRequest;

public interface PageAnalysisPerformer {
    /**
     * 重新加载文章
     *
     * @param docId
     */
    void loadPage(Long docId);

    /**
     * 模拟登陆
     */
    void login();

    /**
     * 加载菜单
     *
     * @param url
     */
    void loadMenu(String url, Long typeId);

    /**
     * 获取然后保存
     *
     * @param request
     * @return
     */
    Long grabAndSave(AnalysisPageRequest request);

    /**
     * 保存下载的文章
     *
     * @param request
     * @return
     */
    Long saveLoadingDoc(AnalysisPageRequest request);

    /**
     * 保存文章
     *
     * @param doc
     * @param title
     * @return
     */
    Long saveDoc(String doc, String title);

    /**
     * 修改文章
     *
     * @param doc
     * @param title
     * @param id
     * @return
     */
    Long updateDoc(String doc, String title, Long id);

    /**
     * 登录然后解析页面
     *
     * @param request
     * @return
     */
    String loginAndAnalysisPage(AnalysisPageRequest request);

    /**
     * 页面解析
     *
     * @param request
     * @return
     */
    String analysisPage(AnalysisPageRequest request);

}
