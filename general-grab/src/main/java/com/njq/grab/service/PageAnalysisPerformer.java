package com.njq.grab.service;

public interface PageAnalysisPerformer {
	/**
	 * 定时加载页面
	 */
	public void loadPageJobTask();

	/**
	 * 重新加载文章
	 * @param docId
	 */
	public void loadPage(Long docId);
	
	/**
	 * 模拟登陆
	 */
	public void login();
	
	/**
	 * 加载菜单
	 * @param url
	 */
	public void loadMenu(String url,Long typeId);

	/**
	 * 保存文章
	 * @param url
	 * @param title
	 * @return
	 */
	public Long saveDoc(String url,String title);
	
	/**
	 * 修改文章
	 * @param url
	 * @param title
	 * @param id
	 * @return
	 */
	public Long updateDoc(String url,String title,Long id);

	/**
	 * 页面解析
	 * @param url
	 */
	public String analysisPage(String url);
	
}
