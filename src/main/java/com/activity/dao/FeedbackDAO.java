package com.activity.dao;

import java.util.List;

import com.activity.entity.Feedback;

public interface FeedbackDAO {
	
	public List<Feedback> getActivityFeedback(int id);
	
	public Feedback get(Feedback feedback);
	
	public void insert(Feedback feedback);
	
}
