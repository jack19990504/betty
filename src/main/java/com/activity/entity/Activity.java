package com.activity.entity;

import java.sql.Date;

public class Activity {
	
	private Integer activityId;
	//activityName , activityOrganizer , activityInfo , attendPeople , activitySpace,startSignUpDate ,
	//endSignUpDate, activityStartDate, activityEndDate , organizerTel , organizerContactInfo , activityMeal
	private String activityName;
	
	private String activityOrganizer;
	
	private String activityInfo;
	
	private Integer attendPeople;
	
	private String activitySpace;
	
	private Date startSignUpDate;
	
	private Date endSignUpDate;
	
	private Date activityStartDate;
	
	private Date activityEndDate;
	
	private Integer activityMeal;
	
	private String activityType;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityOrganizer() {
		return activityOrganizer;
	}

	public void setActivityOrganizer(String activityOrganizer) {
		this.activityOrganizer = activityOrganizer;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}

	
	public Integer getAttendPeople() {
		return attendPeople;
	}

	public void setAttendPeople(Integer attendPeople) {
		this.attendPeople = attendPeople;
	}

	public String getActivitySpace() {
		return activitySpace;
	}

	public void setActivitySpace(String activitySpace) {
		this.activitySpace = activitySpace;
	}

	public Date getStartSignUpDate() {
		return startSignUpDate;
	}

	public void setStartSignUpDate(Date startSignUpDate) {
		this.startSignUpDate = startSignUpDate;
	}

	public Date getEndSignUpDate() {
		return endSignUpDate;
	}

	public void setEndSignUpDate(Date endSignUpDate) {
		this.endSignUpDate = endSignUpDate;
	}

	
	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public Date getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public Integer getActivityMeal() {
		return activityMeal;
	}

	public void setActivityMeal(Integer activityMeal) {
		this.activityMeal = activityMeal;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	
	
}
