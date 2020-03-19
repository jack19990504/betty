package com.activity.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.activity.util.DateUtil;

public class Activity {

	private Integer activityId;
	// activityName , activityOrganizer , activityInfo , attendPeople ,
	// activitySpace,startSignUpDate ,
	// endSignUpDate, activityStartDate, activityEndDate , organizerTel ,
	// organizerContactInfo , activityMeal
	private String activityName;

	private String activityOrganizer;

	private String activityInfo;

	private Integer attendPeople;

	private String activitySpace;

	//database
	
	private Timestamp startSignUpDate;

	private Timestamp endSignUpDate;

	private Timestamp activityStartDate;

	private Timestamp activityEndDate;
	
	
	//program control 
	
	private String startSignUpDateString;

	private String endSignUpDateString;

	private String activityStartDateString;

	private String activityEndDateString;
	
	
	

	private Integer activityMeal;

	private String activityCover;

	// private String activityType;

	private List<String> activityTypes = new ArrayList<String>();

	public String getStartSignUpDateString() {
		this.startSignUpDateString = DateUtil.getDateFromTimestamp(startSignUpDate);
		return startSignUpDateString;
	}

	public void setStartSignUpDateString(String startSignUpDateString) {
		this.startSignUpDateString = startSignUpDateString;
	}

	public String getEndSignUpDateString() {
		this.endSignUpDateString = DateUtil.getDateFromTimestamp(endSignUpDate);
		return endSignUpDateString;
	}

	public void setEndSignUpDateString(String endSignUpDateString) {
		this.endSignUpDateString = endSignUpDateString;
	}

	public String getActivityStartDateString() {
		this.activityStartDateString = DateUtil.getDateFromTimestamp(activityStartDate);
		
		return activityStartDateString;
	}

	public void setActivityStartDateString(String activityStartDateString) {
		this.activityStartDateString = activityStartDateString;
	}

	public String getActivityEndDateString() {
		this.activityEndDateString = DateUtil.getDateFromTimestamp(activityEndDate);
		return activityEndDateString;
	}

	public void setActivityEndDateString(String activityEndDateString) {
		
		this.activityEndDateString = activityEndDateString;
	}

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

	public Timestamp getStartSignUpDate() {
		return startSignUpDate;
	}

	public void setStartSignUpDate(Timestamp startSignUpDate) {
		this.startSignUpDate = startSignUpDate;
	}

	public Timestamp getEndSignUpDate() {
		return endSignUpDate;
	}

	public void setEndSignUpDate(Timestamp endSignUpDate) {
		this.endSignUpDate = endSignUpDate;
	}

	public Timestamp getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Timestamp activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public Timestamp getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Timestamp activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public Integer getActivityMeal() {
		return activityMeal;
	}

	public void setActivityMeal(Integer activityMeal) {
		this.activityMeal = activityMeal;
	}

	public List<String> getActivityTypes() {
		return activityTypes;
	}

	public void setActivityTypes(List<String> activityTypes) {
		this.activityTypes = activityTypes;
	}

	public String getActivityCover() {
		return activityCover;
	}

	public void setActivityCover(String activityCover) {
		this.activityCover = activityCover;
	}

}
