package com.activity.dao.Impl;

import com.activity.dao.MemberDAO;
import com.activity.entity.Activity;
import com.activity.entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private DataSource dataSource;
	
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void insert(Member member) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO member(email, password, name, gender, birthday, tel , phone, address , lineId, type) "
				+ "VALUES(? , ? ,? , ? , ? ,? , ?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getEmail());
			smt.setString(2, member.getPassword());
			smt.setString(3, member.getName());
			smt.setString(4, member.getGender());
			smt.setDate(5, member.getBirthday());
			smt.setString(6, member.getTel());
			smt.setString(7, member.getPhone());
			smt.setString(8, member.getAddress());
			smt.setString(9, member.getLineId());
			smt.setString(10, member.getType());
			smt.executeUpdate();
			smt.close();

		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public void UpdateLineUserId(String LineUserId, String account, String password) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "Update member SET lineId = ?" + "where email = ?" + "AND password = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, LineUserId);
			smt.setString(2, account);
			smt.setString(3, password);
			smt.executeUpdate();
			smt.close();
			
		} catch (SQLException e) {
		
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	@Override
	public Member get(Member member) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `member` where email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getEmail());
			rs = smt.executeQuery();
			member = new Member();
			if (rs.next()) {
				member.setName(rs.getString("name"));
				member.setAddress(rs.getString("address"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setBirthday(rs.getDate("birthday"));
				member.setTel(rs.getString("tel"));
				member.setPhone(rs.getString("phone"));
				member.setLineId(rs.getString("lineId"));
				member.setGender(rs.getString("gender"));
				member.setType(rs.getString("type"));
			}
			smt.close();
			rs.close();
		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return member;
	}
	@Override
	public Member check(Member member) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `member` where lineId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getLineId());
			rs = smt.executeQuery();
			member = new Member();
			if (rs.next()) {
				member.setName(rs.getString("name"));
				member.setAddress(rs.getString("address"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setLineId(rs.getString("lineId"));
				member.setGender(rs.getString("gender"));
			}
			smt.close();
			rs.close();
		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return member;
	}
	@Override
	public void resetLineUserId(String account) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "Update member SET lineId = ?" + "where email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, "");
			smt.setString(2, account);
			smt.executeUpdate();
			smt.close();
			
		} catch (SQLException e) {
		
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	@Override
	public void update(Member oldMember, Member member) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE member SET " + "password = ? ," +"name = ? ," + " gender = ?, " + "birthday = ? ,"
				+ "tel = ? ," + "phone = ? ," + "address = ? ," + "lineId = ? ," + "type = ? ,"
				+ " where email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,member.getPassword() != null ? member.getPassword() : oldMember.getPassword());
			smt.setString(2,member.getName() != null ? member.getName() : oldMember.getName());
			smt.setString(3, member.getGender() != null ? member.getGender(): oldMember.getGender());
			smt.setDate(4,member.getBirthday() != null ? member.getBirthday() : oldMember.getBirthday()); //有需要嗎
			smt.setString(5,member.getTel() != null ? member.getTel() : oldMember.getTel());
			smt.setString(6,member.getPhone() != null ? member.getPhone() : oldMember.getPhone());
			smt.setString(7,member.getAddress() != null ? member.getAddress() : oldMember.getAddress());
			smt.setString(8,member.getLineId() != null ? member.getLineId() : oldMember.getLineId());
			smt.setString(9,member.getType() != null ? member.getType() : oldMember.getType());
			smt.setString(10,member.getEmail());
			
			smt.executeUpdate();
			smt.close();

		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	@Override
	public void delete(Member member) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM member WHERE memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getEmail());
			smt.executeUpdate();
			smt.close();

		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	@Override
	public List<Member> getList() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Member> memberList = new ArrayList<Member>();
		final String sql = "SELECT * FROM member";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Member member;
			while (rs.next()) {
				member = new Member();
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setBirthday(rs.getDate("birthday")); //不確定
				member.setTel(rs.getString("tel"));
				member.setPhone(rs.getString("phone"));
				member.setAddress(rs.getString("address"));
				member.setLineId(rs.getString("lineId"));

				memberList.add(member);
			}
			rs.close();
			smt.close();

		} catch (SQLException e) {

			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return memberList;
	}

}

