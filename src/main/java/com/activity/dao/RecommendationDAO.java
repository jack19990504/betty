package com.activity.dao;

import java.util.List;

import com.activity.entity.Activity;

public interface RecommendationDAO {

	public List<Activity> getRecommendList(String type);
}
