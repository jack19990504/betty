package com.activity.entity;

public class ActivityAnnounce {
	
	private Integer AInum;

	private Integer activityId;
	
	private String announceTitle;
	
	private String announceContent;
	
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
}
