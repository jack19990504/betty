package com.activity.entity;

import java.sql.Timestamp;

import com.activity.util.DateUtil;

public class Registration {
	
	private Integer AInum;
	
	private String member_Email;
	
	private Integer activity_Id;
	
	private String registrationRemark;
	
	private Integer registrationMeal;
	
	private Integer isSignIn;
	
	private Integer isSignOut;
	
	private Timestamp cancelRegistration;
	
	//program control
	
	private String cancelRegistrationString;
	
	private Member member;
	
	private Activity activity;

	

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Member getMember() {
		if(member == null)
			member = new Member();
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getRegistrationRemark() {
		return registrationRemark;
	}

	public void setRegistrationRemark(String registrationRemark) {
		this.registrationRemark = registrationRemark;
	}

	public Integer getRegistrationMeal() {
		return registrationMeal;
	}

	public void setRegistrationMeal(Integer registrationMeal) {
		this.registrationMeal = registrationMeal;
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

	

	public Integer getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(Integer isSignIn) {
		this.isSignIn = isSignIn;
	}

	public Integer getIsSignOut() {
		return isSignOut;
	}

	public void setIsSignOut(Integer isSignOut) {
		this.isSignOut = isSignOut;
	}

	public Timestamp getCancelRegistration() {
		return cancelRegistration;
	}

	public void setCancelRegistration(Timestamp cancelRegistration) {
		this.cancelRegistration = cancelRegistration;
	}

	public String getCancelRegistrationString() {
		
		this.cancelRegistrationString = DateUtil.getDateFromTimestamp(cancelRegistration);
		return cancelRegistrationString;
	}

	public void setCancelRegistrationString(String cancelRegistrationString) {
		this.cancelRegistrationString = cancelRegistrationString;
	}

	
	

}
