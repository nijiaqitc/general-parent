package com.njq.grab.service.impl.novel;

public interface NovelLoadPerformer {

	public String search(String str);
	
	public void loadDetail();
	
	public void loadMenu(String str);
	
	public void loadDoc();
	
}
