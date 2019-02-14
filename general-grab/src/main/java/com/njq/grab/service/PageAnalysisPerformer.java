package com.njq.grab.service;

import com.njq.common.model.po.BaseTitle;

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
     * @param url
     * @param baseTitle
     * @return
     */
    Long grabAndSave(String url, BaseTitle baseTitle);

    /**
     * 保存下载的文章
     *
     * @param url
     * @param baseTitle
     * @return
     */
    Long saveLoadingDoc(String url, BaseTitle baseTitle);

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
     * @param url
     * @return
     */
    String loginAndAnalysisPage(String url);

    /**
     * 页面解析
     *
     * @param url
     */
    String analysisPage(String url);

}
