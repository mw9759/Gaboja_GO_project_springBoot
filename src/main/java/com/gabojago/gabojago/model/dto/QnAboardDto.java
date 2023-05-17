package com.gabojago.gabojago.model.dto;

public class QnAboardDto {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int isAnswer;
	private String registerTime;

	public int getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(int isAnswer) {
		this.isAnswer = isAnswer;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "BoardDto [articleNo=" + articleNo + ", userId=" + userId + ", subject=" + subject + ", content="
				+ content + ", hit=" + isAnswer + ", registerTime=" + registerTime + "]";
	}

}
