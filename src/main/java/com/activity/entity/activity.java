package com.activity.entity;

import java.sql.Date;

public class activity {
	
	private Integer activityId;
	
	private String activityName;
	
	private String hostName;
	
	private String activityInformation;
	
	private Integer activityNumber;
	
	private String activityPlace;
	
	private Date activityStartDate;
	
	private Date activityEndDate;
	
	private Date activityStartApplyDate;
	
	private Date activityEndApplyDate;
	
	private String hostPhone ;
	
	private String hostInfomation;
	
	private Integer offerMeal;
	
	
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getActivityInformation() {
		return activityInformation;
	}

	public void setActivityInformation(String activityInformation) {
		this.activityInformation = activityInformation;
	}

	public Integer getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(Integer activityNumber) {
		this.activityNumber = activityNumber;
	}

	public String getActivityPlace() {
		return activityPlace;
	}

	public void setActivityPlace(String activityPlace) {
		this.activityPlace = activityPlace;
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

	public Date getActivityStartApplyDate() {
		return activityStartApplyDate;
	}

	public void setActivityStartApplyDate(Date activityStartApplyDate) {
		this.activityStartApplyDate = activityStartApplyDate;
	}

	public Date getActivityEndApplyDate() {
		return activityEndApplyDate;
	}

	public void setActivityEndApplyDate(Date activityEndApplyDate) {
		this.activityEndApplyDate = activityEndApplyDate;
	}

	public String getHostPhone() {
		return hostPhone;
	}

	public void setHostPhone(String hostPhone) {
		this.hostPhone = hostPhone;
	}

	public String getHostInfomation() {
		return hostInfomation;
	}

	public void setHostInfomation(String hostInfomation) {
		this.hostInfomation = hostInfomation;
	}

	public Integer getOfferMeal() {
		return offerMeal;
	}

	public void setOfferMeal(Integer offerMeal) {
		this.offerMeal = offerMeal;
	}


}
