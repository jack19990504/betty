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
		final String sql = "INSERT INTO member(memberEmail, memberPassword, memberName, memberGender, memberTel , memberPhone, memberAddress) "
				+ "VALUES(? , ? ,? , ? , ? ,? , ?)";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getMemberEmail());
			smt.setString(2, member.getMemberPassword());
			smt.setString(3, member.getMemberName());
			smt.setString(4, member.getMemberGender());
			smt.setString(5, member.getMemberTel());
			smt.setString(6, member.getMemberPhone());
			smt.setString(7, member.getMemberAddress());
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
		final String sql = "Update member SET memberLineId = ?" + "where memberEmail = ?" + "AND memberPassword = ?";
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
		final String sql = "SELECT * FROM `member` where memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getMemberEmail());
			rs = smt.executeQuery();
			member = new Member();
			if (rs.next()) {
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberBirthday(rs.getDate("memberBirthday"));
				member.setMemberTel(rs.getString("memberTel"));
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setLineId(rs.getString("memberLineId"));
				member.setMemberGender(rs.getString("memberGender"));
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
		final String sql = "SELECT * FROM `member` where memberLineId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getLineId());
			rs = smt.executeQuery();
			member = new Member();
			if (rs.next()) {
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setLineId(rs.getString("memberLineId"));
				member.setMemberGender(rs.getString("memberGender"));
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
		final String sql = "Update member SET memberlineId = ?" + "where memberEmail = ?";
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
		final String sql = "UPDATE member SET " + "memberPassword = ? ," +"memberName = ? ," + " memberGender = ?, "
				+ "memberTel = ? ," + "memberPhone = ? ," + "memberAddress = ? ,"
				+ " where memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,member.getMemberPassword() != null ? member.getMemberPassword() : oldMember.getMemberPassword());
			smt.setString(2,member.getMemberName() != null ? member.getMemberName() : oldMember.getMemberName());
			smt.setString(3, member.getMemberGender() != null ? member.getMemberGender(): oldMember.getMemberGender());
			smt.setDate(4,member.getMemberBirthday() != null ? member.getMemberBirthday() : oldMember.getMemberBirthday()); //有需要嗎
			smt.setString(5,member.getMemberTel() != null ? member.getMemberTel() : oldMember.getMemberTel());
			smt.setString(6,member.getMemberPhone() != null ? member.getMemberPhone() : oldMember.getMemberPhone());
			smt.setString(7,member.getMemberAddress() != null ? member.getMemberAddress() : oldMember.getMemberAddress());
			
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
			smt.setString(1, member.getMemberEmail());
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
		final String sql = "SELECT * FROM member;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Member member;
			while (rs.next()) {
				member = new Member();
				member.setMemberEmail(rs.getString("memberEmail"));
//				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberName(rs.getString("memberName"));
				member.setMemberGender(rs.getString("memberGender"));
				member.setMemberBirthday(rs.getDate("memberBirthday")); //不確定
				member.setMemberTel(rs.getString("memberTel"));
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setLineId(rs.getString("memberLineId"));

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

