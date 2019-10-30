package com.activity.dao;

import java.util.List;

import com.activity.entity.Activity;

public interface ActivityDAO {

	public void insert(Activity activity);

	public void update(Activity oldActivity, Activity activity);

	public void delete(Activity activity);

	public List<Activity> getList();

	public Activity get(Activity activity);

	 public Activity findOne(int id);
}
