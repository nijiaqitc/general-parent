package com.njq.yxl.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njq.common.base.dao.DaoCommon;
import com.njq.common.model.po.YxlStudyAnswer;
import com.njq.common.model.po.YxlStudyTitle;
import com.njq.common.model.po.YxlType;

@SuppressWarnings("unchecked")
@Service
public class YxlReviseService {

	@Autowired
    private DaoCommon<YxlStudyTitle> yxlStudyTitleDao;
	@Autowired
    private DaoCommon<YxlStudyAnswer> yxlStudyAnswerDao;
	@Resource
    private DaoCommon<YxlType> yxlTypeDao;
	
	
	
	
}
