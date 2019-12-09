package com.activity.entity;

public class Registration {
	
	private Integer AInum;
	
	private String member_Email;
	
	private Integer activity_Id;
	
	private String remark;
	
	private Integer meal;
	
	private String activityName;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getAInum() {
		return AInum;
	}

	public void setAInum(Integer aInum) {
		AInum = aInum;
	}

	public String getMember_Email() {
		return member_Email;
	}

	public void setMember_Email(String member_Email) {
		this.member_Email = member_Email;
	}

	public Integer getActivity_Id() {
		return activity_Id;
	}

	public void setActivity_Id(Integer activity_Id) {
		this.activity_Id = activity_Id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMeal() {
		return meal;
	}

	public void setMeal(Integer meal) {
		this.meal = meal;
	}
	

}
