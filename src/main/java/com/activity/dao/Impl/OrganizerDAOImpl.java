package com.activity.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.activity.dao.OrganizerDAO;
import com.activity.entity.Member;
import com.activity.entity.Organizer;

public class OrganizerDAOImpl implements OrganizerDAO{
	@Autowired
	private DataSource dataSource;
	
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void insert(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO organizer(member_Email, name, phone, info, email, address) "
				+ "VALUES(? , ? ,? , ? , ? ,? )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMember_Email());
			smt.setString(2, organizer.getName());
			smt.setString(3, organizer.getPhone());
			smt.setString(4, organizer.getInfo());
			smt.setString(5, organizer.getEmail());
			smt.setString(6, organizer.getAddress());
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

	public void update(Organizer oldOrganizer, Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE organizer SET "+"name = ? ," + " phone = ?, " + "info = ? ,"
				+ "email = ? ," + "address = ? ," 
				+ " where member_Email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,organizer.getName() != null ? organizer.getName() : oldOrganizer.getName());
			smt.setString(2,organizer.getPhone() != null ? organizer.getPhone() : oldOrganizer.getPhone());
			smt.setString(3, organizer.getInfo() != null ? organizer.getInfo(): oldOrganizer.getInfo());
			smt.setString(4,organizer.getEmail() != null ? organizer.getEmail() : oldOrganizer.getEmail()); //有需要嗎
			smt.setString(5,organizer.getAddress() != null ? organizer.getAddress() : oldOrganizer.getAddress());
			smt.setString(6,organizer.getMember_Email());
			
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

	public void delete(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM organizer WHERE member_Email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMember_Email());
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

	public List<Organizer> getList(){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Organizer> organizerList = new ArrayList<Organizer>();
		final String sql = "SELECT * FROM organizer";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Organizer organizer;
			while (rs.next()) {
				organizer = new Organizer();
				organizer.setMember_Email(rs.getString("member_Email"));
				organizer.setName(rs.getString("name"));
				organizer.setPhone(rs.getString("phone"));
				organizer.setInfo(rs.getString("info"));
				organizer.setEmail(rs.getString("email"));
				organizer.setAddress(rs.getString("address"));
				
				organizerList.add(organizer);
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
		return organizerList;
	}

	public Organizer get(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `organizer` where member_Email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMember_Email());
			rs = smt.executeQuery();
			organizer = new Organizer();
			if (rs.next()) {
				organizer.setName(rs.getString("name"));
				organizer.setPhone(rs.getString("phone"));
				organizer.setInfo(rs.getString("info"));
				organizer.setEmail(rs.getString("email"));
				organizer.setAddress(rs.getString("address"));
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
		return organizer;
	}
	
}
