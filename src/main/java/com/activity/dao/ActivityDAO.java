package com.activity.dao;
import java.util.List;

import com.activity.entity.Activity;
public interface ActivityDAO {
		
	public List<Activity> getList();
	
	public String getActivityNames();

	
}
