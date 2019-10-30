package com.activity.dao;

import java.util.List;

import com.activity.entity.ActivityMember;

public interface ActivityMemberDAO {

	public void insert(ActivityMember activityMember);

	public void update(ActivityMember oldActivityMember, ActivityMember activityMember);

	public void delete(ActivityMember activityMember);

	public List<ActivityMember> getList();

	public ActivityMember get(ActivityMember adctivityMember);

	 public ActivityMember findOne(int id);
	
}
