package com.activity.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.activity.dao.MemberDAO;
import com.activity.entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insert(Member member) {
		
	}

	public void update(Member oldMember, Member member) {
		
	}

	public void delete(Member member) {
		
	}

	public List<Member> getList(){
		return null;
		
	}

	public Member get(Member member) {
		return member;
		
	}

	public Member findOne(int id) {
		return null;
		
	}

}
