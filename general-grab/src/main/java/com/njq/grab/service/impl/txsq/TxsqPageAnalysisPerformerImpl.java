package com.njq.grab.service.impl.txsq;

import com.njq.basis.service.SaveTitlePerformer;
import com.njq.basis.service.impl.BaseFileService;
import com.njq.basis.service.impl.BaseTipService;
import com.njq.basis.service.impl.BaseTitleService;
import com.njq.common.enumreg.channel.ChannelType;
import com.njq.common.exception.BaseKnownException;
import com.njq.common.exception.ErrorCodeConstant;
import com.njq.common.model.ro.AnalysisPageRequest;
import com.njq.common.model.vo.LeftMenu;
import com.njq.common.util.grab.HtmlDecodeUtil;
import com.njq.common.util.grab.HtmlGrabUtil;
import com.njq.grab.service.impl.AbstractPageAnalysisPerformer;
import com.njq.grab.service.impl.CommonTipAnalysisPerformer;
import com.njq.grab.service.impl.GrabConfig;
import com.njq.grab.service.impl.GrabConfigBuilder;
import com.njq.grab.service.impl.GrabUrlInfoFactory;
import com.njq.grab.service.operation.GrabDocSaveOperation;
import com.njq.grab.service.operation.GrabDocUpdateOperation;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/12/9
 */
@Component("txsqPageAnalysisPerformer")
public class TxsqPageAnalysisPerformerImpl extends AbstractPageAnalysisPerformer {
	private static final Logger logger = LoggerFactory.getLogger(TxsqPageAnalysisPerformerImpl.class);
    private final TxsqPreHandler txsqPreHandler;
    private final BaseFileService baseFileService;
    private final BaseTipService baseTipService;
    
    public TxsqPageAnalysisPerformerImpl(BaseTitleService baseTitleService, SaveTitlePerformer grabSaveTitlePerformer,
			GrabDocUpdateOperation grabDocUpdateOperation, GrabDocSaveOperation grabDocSaveOperation,
			TxsqPreHandler txsqPreHandler, BaseFileService baseFileService, BaseTipService baseTipService) {
		super(baseTitleService, grabSaveTitlePerformer, grabDocUpdateOperation, grabDocSaveOperation);
		this.txsqPreHandler = txsqPreHandler;
		this.baseFileService = baseFileService;
		this.baseTipService = baseTipService;
		super.setChannelType(ChannelType.TXSQ);
	}
    
    @Override
    public void loadMenu(String url, Long typeId) {
        List<LeftMenu> list = txsqPreHandler.loadAllMenu(url);
        if (list == null) {
            return;
        }
        list.parallelStream().forEach(n -> {
            try {
                this.saveTitle(n, typeId, null);
            } catch (Exception e) {
                logger.error("保存标题出错！" + e.getMessage(), e);
            }
        });
    }

    @Override
    public String analysisPage(AnalysisPageRequest request) {
        String grabUrl = GrabUrlInfoFactory.getUrlInfo(ChannelType.TXSQ).getPageIndex();
        String url = request.getUrl();
        url = url.startsWith("http") ? url : grabUrl + url;
        Document doc = HtmlGrabUtil
                .build(ChannelType.TXSQ.getValue())
                .getDoc(url);
        if (doc == null) {
            throw new BaseKnownException(ErrorCodeConstant.UN_LOAD_DOC_CODE, ErrorCodeConstant.UN_LOAD_DOC_MSG + url);
        }

        GrabConfig config = new GrabConfigBuilder()
                .ofBaseFileService(baseFileService)
                .ofBaseTipService(baseTipService)
                .ofBaseTitle(request.getBaseTitle())
                .ofGrabUrl(grabUrl)
                .ofUrl(url)
                .ofType(request.getType())
                .build();
        String body = new TxsqBodyAnalysisPerformerImpl(config).analysis(doc);
        if (request.getType()) {
            new CommonTipAnalysisPerformer(config).analysis(doc, "col-tag");
        }
        return HtmlDecodeUtil.decodeHtml(body, GrabUrlInfoFactory.getDecodeJsPlace(), "decodeStr");
    }
}
