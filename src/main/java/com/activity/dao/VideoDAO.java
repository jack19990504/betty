package com.activity.dao;

import java.util.List;

import com.activity.entity.Video;

public interface VideoDAO {
	
	public void insert(Video video);
	
	public List<Video> getList(Integer activityId);
	
	public void delete(Video video);

}
