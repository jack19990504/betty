package com.activity.dao;
import java.util.List;


import com.activity.entity.Activity;


public interface ActivityDAO {
		
	public List<Activity> getList();
	
	public String getActivityNames();
	
	public Activity get(Activity activity);
	
	public List<Activity> getOrganizerActivityList(Activity activity);
	
	public void update(Activity oldActivity,Activity activity);
	
	public void delete(Activity activity);
	
	public void insert(Activity activity);
	
	public void getActivityTypes(Activity activity);
	
	public List<Activity> getListByType(String...strings );
	
	public void updateCover(Activity activity);
	
	public Activity getActivityByCols(Activity activity);


	

}
