package com.activity.dao;
import java.util.List;

import com.activity.entity.Activity;
public interface ActivityDAO {
		
	public List<Activity> getList();
	
	public String getActivityNames();
	
	public Activity get(Activity activity);
	
	public void update(Activity oldActivity,Activity activity);
	
	public void delete(Activity activity);
	
	public void insert(Activity activity);

	
}
