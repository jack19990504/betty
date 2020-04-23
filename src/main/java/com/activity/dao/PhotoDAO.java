package com.activity.dao;

import java.util.List;

import com.activity.engine.entity.Face;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Photo;

public interface PhotoDAO {
	
	public void writePhoto(Face face);

	public void writePhoto(Integer activityId, String photoPath);
	
	public List<Photo> getMemberPhoto(Member member,Integer activityId);
	
	public List<Photo> getActivityPhoto(Activity activity);
	
	public void deletePhoto(Photo photo);
	
	public void deleteMemberPhoto(Photo photo);
}
