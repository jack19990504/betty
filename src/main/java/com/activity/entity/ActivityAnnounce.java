package com.activity.entity;

import java.sql.Timestamp;

public class ActivityAnnounce {
	
	private Integer AInum;

	private Integer activityId;
	
	private String announceTitle;
	
	private String announceContent;
	
	private Timestamp announceTime;
	
	private String announceTimeString;
	
	
	public Integer getAInum() {
		return AInum;
	}

	public void setAInum(Integer aInum) {
		AInum = aInum;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getAnnounceTitle() {
		return announceTitle;
	}

	public void setAnnounceTitle(String announceTitle) {
		this.announceTitle = announceTitle;
	}

	public String getAnnounceContent() {
		return announceContent;
	}

	public void setAnnounceContent(String announceContent) {
		this.announceContent = announceContent;
	}

	public Timestamp getAnnounceTime() {
		return announceTime;
	}

	public void setAnnounceTime(Timestamp announceTime) {
		this.announceTime = announceTime;
	}

	public String getAnnounceTimeString() {
		return announceTimeString;
	}

	public void setAnnounceTimeString(String announceTimeString) {
		this.announceTimeString = announceTimeString;
	}
	
	
}
