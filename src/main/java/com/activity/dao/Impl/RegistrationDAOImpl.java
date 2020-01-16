package com.activity.dao.Impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.activity.dao.RegistrationDAO;
import com.activity.entity.Member;
import com.activity.entity.Registration;;
@Repository
public class RegistrationDAOImpl implements RegistrationDAO{

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Registration> getUserRegistration(String UserLineId) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registerList = new ArrayList<>();
		final String sql = "SELECT r.* FROM Registration r JOIN member m "
				+ "ON r.member_Email = m.memberEmail where m.memberLineId = ? ;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, UserLineId);
			rs = smt.executeQuery();
			Registration register;
			while (rs.next()) {
				register = new Registration();
				register.setActivityName(rs.getString("activityName"));
				registerList.add(register);
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
		return registerList;
	}
	
	public Registration get(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `registration` where member_Email = ? && where activity_Id = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			rs = smt.executeQuery();
			registration = new Registration();
			if (rs.next()) {
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRemark(rs.getString("remark"));
				registration.setMeal(rs.getInt("meal"));
				registration.setActivityName(rs.getString("activityName"));
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
		return registration;
	}
	
	public List<Registration> getList(){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registrationList = new ArrayList<Registration>();
		final String sql = "SELECT * FROM registration;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Registration registration;
			while (rs.next()) {
				registration = new Registration();
				registration.setMember_Email(rs.getString("member_email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRemark(rs.getString("remark"));
				registration.setMeal(rs.getInt("meal"));

				registrationList.add(registration);
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
		return registrationList;
	}
	
	public void insert(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO registration(member_Email, activity_Id, remark, meal, activityName) "
				+ "VALUES(? , ? ,? , ? , ? )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			smt.setString(3, registration.getRemark());
			smt.setInt(4, registration.getMeal());
			smt.setString(5, registration.getActivityName());
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

	public void update(Registration oldRegistration, Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE member SET " + " remark = ?, " + "meal = ? ," + "activityName = ? ," + "where member_Email = ? && activity_Id = ? ";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, registration.getRemark() != null ? registration.getRemark(): oldRegistration.getRemark());
			smt.setInt(2,registration.getMeal() != null ? registration.getMeal() : oldRegistration.getMeal());
			smt.setString(3,registration.getActivityName() != null ? registration.getActivityName() : oldRegistration.getActivityName());
			smt.setString(4,registration.getMember_Email() != null ? registration.getMember_Email() : oldRegistration.getMember_Email());
			smt.setInt(5,registration.getActivity_Id() != null ? registration.getActivity_Id() : oldRegistration.getActivity_Id());
			
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

	public void delete(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM member WHERE memberEmail = ? && activity_Id = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
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

}
