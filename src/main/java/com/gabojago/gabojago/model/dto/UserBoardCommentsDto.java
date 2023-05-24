package com.gabojago.gabojago.model.dto;

public class UserBoardCommentsDto {
	private int commentNo;
	private int articleNo;
	private String userId;
	private String content;
	private String registerTime;
	private String profileImg;
	private int isModify;
	
	public int getIsModify() {
		return isModify;
	}
	public void setIsModify(int isModify) {
		this.isModify = isModify;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
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
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	@Override
	public String toString() {
		return "UserBoardCommentsDto [commentNo=" + commentNo + ", articleNo=" + articleNo + ", userId=" + userId
				+ ", content=" + content + ", registerTime=" + registerTime + ", profileImg=" + profileImg
				+ ", isModify=" + isModify + "]";
	}
	
}
