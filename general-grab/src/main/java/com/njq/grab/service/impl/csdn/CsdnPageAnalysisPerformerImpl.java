package com.njq.grab.service.impl.csdn;

import com.njq.common.model.po.BaseTitle;
import com.njq.grab.service.PageAnalysisPerformer;
import org.springframework.stereotype.Component;

/**
 * @author: nijiaqi
 * @date: 2019/2/20
 */
@Component
public class CsdnPageAnalysisPerformerImpl implements PageAnalysisPerformer {

    @Override
    public void loadPage(Long docId) {

    }

    @Override
    public void login() {

    }

    @Override
    public void loadMenu(String url, Long typeId) {

    }

    @Override
    public Long grabAndSave(String url, BaseTitle baseTitle) {
        return null;
    }

    @Override
    public Long saveLoadingDoc(String url, BaseTitle baseTitle) {
        return null;
    }

    @Override
    public Long saveDoc(String doc, String title) {
        return null;
    }

    @Override
    public Long updateDoc(String doc, String title, Long id) {
        return null;
    }

    @Override
    public String loginAndAnalysisPage(String url, Long typeId) {
        return null;
    }

    @Override
    public String analysisPage(String url, Long typeId) {
        return null;
    }
}
