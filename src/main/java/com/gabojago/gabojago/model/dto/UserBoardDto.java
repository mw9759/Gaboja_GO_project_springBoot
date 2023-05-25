package com.gabojago.gabojago.model.dto;

public class UserBoardDto {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private String photoUrl;
	private int hit;
	private String registerTime;
	private String imgsIsExist;
	private int imgSlideNum;
	private int commentCnt;
	private int likeCnt;
	private String profileImg;
	
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	public int getImgSlideNum() {
		return imgSlideNum;
	}
	public void setImgSlideNum(int imgSlideNum) {
		this.imgSlideNum = imgSlideNum;
	}
	public String getImgsIsExist() {
		return imgsIsExist;
	}
	public void setImgsIsExist(String imgsIsExist) {
		this.imgsIsExist = imgsIsExist;
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
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "UserBoardDto [articleNo=" + articleNo + ", userId=" + userId + ", subject=" + subject + ", content="
				+ content + ", photoUrl=" + photoUrl + ", hit=" + hit + ", registerTime=" + registerTime
				+ ", imgsIsExist=" + imgsIsExist + ", imgSlideNum=" + imgSlideNum + ", commentCnt=" + commentCnt
				+ ", likeCnt=" + likeCnt + ", profileImg=" + profileImg + "]";
	}
	
}
