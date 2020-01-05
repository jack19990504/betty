package com.activity.dao;

import com.activity.engine.entity.Face;
import com.activity.entity.Member;

public interface FaceDAO {
	
	public Member getMemberByFace(Face face);

}
