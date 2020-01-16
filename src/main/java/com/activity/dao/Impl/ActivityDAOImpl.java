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
import com.activity.dao.ActivityDAO;
import com.activity.entity.Activity;

@Repository
public class ActivityDAOImpl implements ActivityDAO {

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Activity> getList() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Activity> activityList = new ArrayList<Activity>();
		final String sql = "SELECT * FROM activity;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Activity activity;
			while (rs.next()) {
				activity = new Activity();
				activity.setId(rs.getInt("id"));
				activity.setName(rs.getString("name"));
				activity.setOrganizerId(rs.getString("organizer_Id"));
				activity.setType(rs.getString("type"));
				activity.setInfo(rs.getString("info"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setSpace(rs.getString("space"));
//				activity.setStartDate(rs.getDate("startDate"));
//				activity.setEndDate(rs.getDate("endDate"));
//				activity.setStartSignUpDate(rs.getDate("startSignUpDate"));
//				activity.setEndSignUpDate(rs.getDate("endSignUpDate"));
				activity.setMeal(rs.getInt("meal"));

				activityList.add(activity);
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
		return activityList;
	}

	@Override
	public String getActivityNames() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		String activityNames = "";
		final String sql = "SELECT * FROM activity;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Activity activity;
			while (rs.next()) {
				activity = new Activity();
				activity.setName(rs.getString("name"));

				activityNames = activityNames + rs.getString("name");
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
		return activityNames;
	}

	@Override
	public Activity get(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM activity WHERE activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, activity.getId());
			rs = smt.executeQuery();

			activity = new Activity();
			if (rs.next()) {
				activity.setId(rs.getInt("id"));
				activity.setName(rs.getString("name"));
				activity.setOrganizerId(rs.getString("organizer_Id"));
				activity.setType(rs.getString("type"));
				activity.setInfo(rs.getString("info"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setSpace(rs.getString("space"));
//				activity.setStartDate(rs.getDate("startDate"));
//				activity.setEndDate(rs.getDate("endDate"));
//				activity.setStartSignUpDate(rs.getDate("startSignUpDate"));
//				activity.setEndSignUpDate(rs.getDate("endSignUpDate"));
				activity.setMeal(rs.getInt("meal"));
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
		return activity;
	}

	@Override
	public void update(Activity oldActivity, Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE activity SET " + "name = ? ," + " organizer_Id = ?, " + "type = ?,"
				+ "info = ?," + "attendPeople = ? ," + "space = ? ," + "activityMeal = ? " + " where activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,activity.getName() != null ? activity.getName() : oldActivity.getName());
			smt.setString(2,activity.getOrganizerId() != null ? activity.getOrganizerId(): oldActivity.getOrganizerId());
			smt.setString(3,activity.getType() != null ? activity.getType(): oldActivity.getType());
			smt.setString(4,activity.getInfo() != null ? activity.getInfo() : oldActivity.getInfo());
			smt.setInt(5,activity.getAttendPeople() != null ? activity.getAttendPeople() : oldActivity.getAttendPeople());
			smt.setString(6,activity.getSpace() != null ? activity.getSpace() : oldActivity.getSpace());
			smt.setInt(7,activity.getMeal() != null ? activity.getMeal() : oldActivity.getMeal());
			smt.setInt(8, activity.getId() != null ? activity.getId() : oldActivity.getId());
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
	public void delete(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM activity WHERE activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, activity.getId());
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
	public void insert(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO activity(name , organizer_Id , type , info , "
				+ "attendPeople , space, startSignUpDate , endSignUpDate, startDate, "
				+ "endDate , meal)"
				+ " VALUES(? ,? ,? ,? ,? ,? , NOW() ,NOW() ,NOW() ,NOW() ,?  )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, activity.getName());
			smt.setString(2, activity.getOrganizerId());
			smt.setString(3, activity.getType());
			smt.setString(4, activity.getInfo());
			smt.setInt(5, activity.getAttendPeople());
			smt.setString(6, activity.getSpace());
			smt.setInt(8, activity.getMeal());

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
