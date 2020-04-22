package com.activity.entity;

public class Photo {

	private String photoId;
	
	private Integer activityId;
	private Integer AINum;
	
	private String memberEmail;

	public Integer getAINum() {
		return AINum;
	}

	public void setAINum(Integer aINum) {
		AINum = aINum;
	}



	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	

}
