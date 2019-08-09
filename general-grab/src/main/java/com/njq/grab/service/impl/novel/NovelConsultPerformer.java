package com.njq.grab.service.impl.novel;

import java.util.List;

public interface NovelConsultPerformer {
	
	String search(String name); 
	
	List<String> loadMenu(Long menuId);
}
