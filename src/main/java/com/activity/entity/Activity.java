package com.activity.entity;

import java.sql.Date;

public class Activity {
	
	private Integer id;
	//activityName , activityOrganizer , activityInfo , attendPeople , activitySpace,startSignUpDate ,
	//endSignUpDate, activityStartDate, activityEndDate , organizerTel , organizerContactInfo , activityMeal
	private String name;
	
	private String organizer_Id;
	
	private String type;
	
	private String info;
	
	private Integer attendPeople;
	
	private String space;
	
	private Date startSignUpDate;
	
	private Date endSignUpDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer meal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizerId() {
		return organizer_Id;
	}

	public void setOrganizerId(String organizer) {
		this.organizer_Id = organizer;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
	public Integer getAttendPeople() {
		return attendPeople;
	}

	public void setAttendPeople(Integer attendPeople) {
		this.attendPeople = attendPeople;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
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

	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Integer getMeal() {
		return meal;
	}

	public void setMeal(Integer meal) {
		this.meal = meal;
	}
	
	
}
