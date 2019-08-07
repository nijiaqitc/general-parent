package com.njq.grab.service.impl.novel;

import java.util.List;

import com.njq.common.model.po.GrabNovelMenu;

public interface NovelLoadPerformer {

	public String search(String str);
	
	public void loadDetail();
	
	public List<GrabNovelMenu> loadMenu(String str,Long parentId);
	
	public void loadDoc();
	
}
