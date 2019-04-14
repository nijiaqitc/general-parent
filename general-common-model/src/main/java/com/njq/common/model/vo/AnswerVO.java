package com.njq.common.model.vo;

import java.io.Serializable;

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
		return columDesc;
	}
	public void setColumDesc(String columDesc) {
		this.columDesc = columDesc;
	}
	
	
	
}
