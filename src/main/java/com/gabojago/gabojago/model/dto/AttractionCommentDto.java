package com.gabojago.gabojago.model.dto;

public class AttractionCommentDto {
	private int commentId;
	private int contentId;
	private String userId;
	private String content;
	private String registerTime;
	private int isModify;
	private String profileImg;
	
	
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
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
	public int getIsModify() {
		return isModify;
	}
	public void setIsModify(int isModify) {
		this.isModify = isModify;
	}
	@Override
	public String toString() {
		return "attractionCommentDto [commentId=" + commentId + ", contentId=" + contentId + ", userId=" + userId
				+ ", content=" + content + ", registerTime=" + registerTime + ", isModify=" + isModify + "]";
	}
	
}
