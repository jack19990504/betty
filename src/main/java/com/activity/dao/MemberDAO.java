package com.activity.dao;

import com.activity.entity.Member;

public interface MemberDAO {
	
	public Member get(Member member);
	
	public void insert(Member member);
	
	public void UpdateLineUserId(String LineUserId,String account ,String password);
	
	public void resetLineUserId(String account);
	
	public Member check(Member member);

}
