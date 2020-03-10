package com.activity.dao;

import java.util.List;
import com.activity.entity.ActivityAnnounce;

public interface ActivityAnnounceDAO {
	
	public List<ActivityAnnounce> getList();
	
	public ActivityAnnounce get(ActivityAnnounce activityAnnounce);
	
	public void insert(ActivityAnnounce acitivtyAnnounce);
	
	public void update(ActivityAnnounce oldActivityAnnounce, ActivityAnnounce acitivtyAnnounce);
	
	public void delete(int id);
}
