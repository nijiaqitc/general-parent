package com.njq.grab.service;

import com.njq.common.model.po.BaseTitle;

public interface PageAnalysisPerformer {
    /**
     * 重新加载文章
     *
     * @param docId
     */
    public void loadPage(Long docId);

    /**
     * 模拟登陆
     */
    public void login();

    /**
     * 加载菜单
     *
     * @param url
     */
    public void loadMenu(String url, Long typeId);

    /**
     * 保存下载的文章
     *
     * @param url
     * @param baseTitle
     * @return
     */
    public Long saveLoadingDoc(String url, BaseTitle baseTitle);

    /**
     * 保存文章
     *
     * @param url
     * @param title
     * @return
     */
    public Long saveDoc(String url, String title);

    /**
     * 修改文章
     *
     * @param url
     * @param title
     * @param id
     * @return
     */
    public Long updateDoc(String url, String title, Long id);

    /**
     * 登录然后解析页面
     *
     * @param url
     * @return
     */
    public String loginAndAnalysisPage(String url);

    /**
     * 页面解析
     *
     * @param url
     */
    public String analysisPage(String url);

}
