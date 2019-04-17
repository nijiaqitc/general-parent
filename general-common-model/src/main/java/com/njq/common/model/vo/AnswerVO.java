package com.njq.common.model.vo;

import java.io.Serializable;

import com.njq.common.util.string.StringUtil;

public class AnswerVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String answer;
	private String columDesc;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getColumDesc() {
		if(StringUtil.IsNotEmpty(columDesc)) {
			return columDesc.trim();			
		}else {
			return null;
		}
	}
	public void setColumDesc(String columDesc) {
		this.columDesc = columDesc;
	}
	
	
	
}
