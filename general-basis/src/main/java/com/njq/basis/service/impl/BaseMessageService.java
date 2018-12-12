package com.njq.basis.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.njq.common.base.dao.ConditionsCommon;
import com.njq.common.base.dao.ConstantsCommon;
import com.njq.common.base.dao.DaoCommon;
import com.njq.common.base.dao.PageList;
import com.njq.common.base.other.MessageCommon;
import com.njq.common.model.po.BaseMessage;
import com.njq.common.util.string.StringUtil;

public class BaseMessageService {

	@Resource
	private DaoCommon<BaseMessage> messageDao;
	@Resource
	private BaseLogService logService;
	
	/**
	 * 查询所有消息(分页)
	 * @param paramMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageList<BaseMessage> queryAllMessage(Map<String, Object> paramMap,
			int page, int size) {
		ConditionsCommon cc=new ConditionsCommon();
		cc.addPageParam(page, 10);
		cc.addEqParam("status", ConstantsCommon.Del_Status.YES);
		cc.addEqParam("parentId", ConstantsCommon.Org_Id.FIRST_ORG_ID);
		return messageDao.queryForPage(cc);
	}

	/**
	 * 根据id检索出消息
	 * @param id
	 * @return
	 */
	public BaseMessage queryMessageById(Long id) {
		return messageDao.queryTById(id);
	}

	/**
	 * 保存消息
	 * @param message
	 * @param userId
	 * @param isSend
	 * @param map
	 */
	public void saveMessage(BaseMessage message, Long userId, int isSend,
			Map<String, Object> map) {
		/*
		 * 如果是发送邮件，那么代表消息已经阅读了 
		 */
		if(isSend==ConstantsCommon.Email_Type.TO_SEND){
			message.setIsRead(ConstantsCommon.Email_Type.READ);
		}
		messageDao.save(message);
	}

	/**
	 * 根据id修改消息
	 * @param message
	 * @param userId
	 * @param map
	 */
	public void updateMessageById(BaseMessage message, Long userId,
			Map<String, Object> map) {
		BaseMessage m = messageDao.queryTById(message.getId());
		m.setTitle(message.getTitle());
		m.setContext(message.getContext());
		messageDao.update(m);
		//日志记录
		logService.saveLog(userId, "修改", "消息表", "对行"+m.getId()+"进行修改");
		MessageCommon.getSuccessMap(map);
		
	}

	/**
	 * 删除消息
	 * @param ids
	 * @param userId
	 * @param map
	 */
	public void deleteMessageById(Long[] ids, Long userId,
			Map<String, Object> map) {
		int num = messageDao.delT(ids);
		if(num>0){
			MessageCommon.getSuccessMap(map);
			//日志记录
			logService.saveLog(userId, "删除", "消息表", "对行"+StringUtil.LongToString(ids)+"进行删除");
		}else{
			MessageCommon.getFalseMap(map);
			map.put("message", "请检查数据是否正确，再进行操作！");
		}
	}

}
