package com.gabojago.gabojago.model.dto;

public class MemberDto {

	private String userId;
	private String userName;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String userPhonNum;
	private String salt;
	private String slogun;
	private String profileImg;
	private String likeBoards;
	
	public String getLikeBoards() {
		return likeBoards;
	}

	public void setLikeBoards(String likeBoards) {
		this.likeBoards = likeBoards;
	}

	public String getSlogun() {
		return slogun;
	}

	public void setSlogun(String slogun) {
		this.slogun = slogun;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getUserPhonNum() {
		return userPhonNum;
	}

	public void setUserPhonNum(String userPhonNum) {
		this.userPhonNum = userPhonNum;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", emailId="
				+ emailId + ", emailDomain=" + emailDomain + ", joinDate=" + joinDate + ", userPhonNum=" + userPhonNum
				+ ", salt=" + salt + ", slogun=" + slogun + ", profileImg=" + profileImg + ", likeBoards=" + likeBoards
				+ "]";
	}

	
}