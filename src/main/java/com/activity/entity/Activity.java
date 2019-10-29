package com.activity.entity;

import java.sql.Date;

public class Activity {
	
	private Integer activityId;
	
	private String activityName;
	
	private String activityOrganizer;
	
	private String activityInfo;
	
	private Integer attendpeople;
	
	private String activitySpace;
	
	private Date startSignUpDate;
	
	private Date endSignUpDate;
	
	private Date activityStart;
	
	private Date activityEnd;
	
	private String organizerTel;
	
	private String organizerContactInfo;
	
	private Integer activityMeal;

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

	public Integer getAttendpeople() {
		return attendpeople;
	}

	public void setAttendpeople(Integer attendpeople) {
		this.attendpeople = attendpeople;
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

	public Date getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(Date activityStart) {
		this.activityStart = activityStart;
	}

	public Date getActivityEnd() {
		return activityEnd;
	}

	public void setActivityEnd(Date activityEnd) {
		this.activityEnd = activityEnd;
	}

	public String getOrganizerTel() {
		return organizerTel;
	}

	public void setOrganizerTel(String organizerTel) {
		this.organizerTel = organizerTel;
	}

	public String getOrganizerContactInfo() {
		return organizerContactInfo;
	}

	public void setOrganizerContactInfo(String organizerContactInfo) {
		this.organizerContactInfo = organizerContactInfo;
	}

	public Integer getActivityMeal() {
		return activityMeal;
	}

	public void setActivityMeal(Integer activityMeal) {
		this.activityMeal = activityMeal;
	}
	
	
}
