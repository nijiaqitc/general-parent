package com.njq.grab.service.impl.custom;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.base.constants.ChannelType;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.exception.BaseKnownException;
import com.njq.common.base.exception.ErrorCodeConstant;
import com.njq.common.model.po.BaseTitle;
import com.njq.common.model.po.GrabDoc;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.common.util.grab.UrlChangeUtil;

@Component
public class CustomAnalysisPerformer {
	@Value("${image.url}")
    private String imgUrl;
    @Value("${image.place}")
    private String imagePlace;
    @Value("${file.url}")
    private String docUrl;
    @Value("${file.place}")
    private String docPlace;
    @Value("${decode.js.place}")
    private String decodeJsPlace;
    @Autowired
    private BaseTitleService baseTitleService;
    @Autowired
    private  DaoCommon<GrabDoc> grabDocDao;
    
    public Long saveLoadingDoc(String url,String name,int type,BaseTitle baseTitle) {
		String doc = this.analysisPage(url, name, type);
        Long docId =  this.saveDoc(doc, baseTitle.getTitle());
        baseTitleService.updateLoadSuccess(ChannelType.CUSTOM,
        		docId,
                baseTitle.getId());
        return docId;
    }
    
	public String analysisPage(String url,String name,int type) {
		Document doc = HtmlGrabUtil
                .build(ChannelType.CUSTOM.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG);
        }
        Element enode =null;
        switch (type) {
		case 1:
			enode = doc.getElementById(name);
			break;
		case 2:
			Elements es = doc.getElementsByTag(name); 
			if(es != null) {
				enode = es.get(0);				
			}
			break;
		case 3:
			Elements es1 = doc.getElementsByClass(name);
			if(es1 != null) {
				enode = es1.get(0);				
			}
			break;
		default:
			break;
		}
        if(enode == null) {
        	throw new BaseKnownException("无法获取节点元素"+name);
        }
        enode.getElementsByTag("img").forEach(n -> {
            n.attr("src", imgUrl + UrlChangeUtil.changeSrcUrl("", n.attr("src"), ChannelType.CUSTOM.getValue(), imagePlace));
        });
        return HtmlDecodeUtil.decodeHtml(enode.html(), decodeJsPlace, "decodeStr");
	}
	
	public Long saveDoc(String doc, String title) {
        GrabDoc grabDoc = new GrabDoc();
        grabDoc.setChannel(ChannelType.YH_WIKI.getValue());
        grabDoc.setCreateDate(new Date());
        grabDoc.setDoc(doc);
        grabDoc.setTitle(title);
        grabDocDao.save(grabDoc);
        return grabDoc.getId();
    }
}
