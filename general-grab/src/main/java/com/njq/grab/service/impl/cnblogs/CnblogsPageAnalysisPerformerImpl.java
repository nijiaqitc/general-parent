package com.njq.grab.service.impl.cnblogs;

import org.springframework.stereotype.Component;

import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.PageAnalysisPerformer;

@Component("cgblogsPageAnalysis")
public class CnblogsPageAnalysisPerformerImpl implements PageAnalysisPerformer{

	@Override
	public void loadPageJobTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadPage(Long docId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMenu(String url,Long typeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long saveDoc(String url, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String analysisPage(String url) {
		String doc = HtmlGrabUtil
				.build("cgblogs")
				.getContext(url);
		System.out.println(doc);
		return doc;
	}

	@Override
	public Long updateDoc(String url, String title, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
