package com.gabojago.gabojago.model.dto;

public class TravelPlanDto {

	private String userId;
	private String planName;
	
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
	
	@Override
	public String toString() {
		return "TravelPlanDto [userId=" + userId + ", planName=" + planName + "]";
	}
	
}
