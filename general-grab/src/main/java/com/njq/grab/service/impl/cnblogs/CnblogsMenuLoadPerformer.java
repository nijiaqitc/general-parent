package com.njq.grab.service.impl.cnblogs;

import org.jsoup.nodes.Element;

public interface CnblogsMenuLoadPerformer {

	
	void loadMenu(Element element, Long typeId);
}
