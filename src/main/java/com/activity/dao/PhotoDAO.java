package com.activity.dao;



import com.activity.engine.entity.Face;

public interface PhotoDAO {
	
	public void writePhoto(Face face);

	public void writePhoto(Integer activityId, String photoPath);
}
