package com.activity.dao;

import java.util.List;

import com.activity.entity.Member;

public interface MemberDAO {

	public void insert(Member member);

	public void update(Member oldMember, Member member);

	public void delete(Member member);

	public List<Member> getList();

	public Member get(Member member);
	
	public void UpdateLineUserId(String LineUserId,String account ,String password);
	
	public void resetLineUserId(String account);
	
	public Member check(Member member);

	public void updateMemberPassword(Member member);
	

}
