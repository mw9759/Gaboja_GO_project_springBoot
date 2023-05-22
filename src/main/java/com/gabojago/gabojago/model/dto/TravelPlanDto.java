package com.gabojago.gabojago.model.dto;

public class TravelPlanDto {

	private String userId;
	private String planName;
	private String content;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPlanName() {
		return planName;
	}
	
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "TravelPlanDto [userId=" + userId + ", planName=" + planName + ", content=" + content + "]";
	}
}
