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
				activity.setActivityId(rs.getInt("activityId"));
				activity.setActivityName(rs.getString("activityName"));
				activity.setActivityOrganizer(rs.getString("activityOrganizer"));
				activity.setActivityInfo(rs.getString("activityInfo"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setActivitySpace(rs.getString("activitySpace"));
//				activity.setActivityStartDate(rs.getString("activityStartDate"));
//				activity.setActivityEndDate(rs.getString("activityEndDate"));
//				activity.setStartSignUpDate(rs.getString("startSignUpDate"));
//				activity.setEndSignUpDate(rs.getString("endSignUpDate"));
				activity.setActivityMeal(rs.getInt("activityMeal"));

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
				activity.setActivityName(rs.getString("activityName"));

				activityNames = activityNames + rs.getString("activityName");
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
			smt.setInt(1, activity.getActivityId());
			rs = smt.executeQuery();

			activity = new Activity();
			if (rs.next()) {
				activity.setActivityId(rs.getInt("activityId"));
				activity.setActivityName(rs.getString("activityName"));
				activity.setActivityOrganizer(rs.getString("activityOrganizer"));
				activity.setActivityInfo(rs.getString("activityInfo"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setActivitySpace(rs.getString("activitySpace"));
//				activity.setActivityStartDate(rs.getString("activityStartDate"));
//				activity.setActivityEndDate(rs.getString("activityEndDate"));
//				activity.setStartSignUpDate(rs.getString("startSignUpDate"));
//				activity.setEndSignUpDate(rs.getString("endSignUpDate"));
				activity.setActivityMeal(rs.getInt("activityMeal"));
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
		final String sql = "UPDATE activity SET " + "activityName = ? ," + " activityOrganizer = ?, "
				+ "activityInfo = ?," + "attendPeople = ? ," + "activitySpace = ? ," + "activityMeal = ? " + " where activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,activity.getActivityName() != null ? activity.getActivityName() : oldActivity.getActivityName());
			smt.setString(2, activity.getActivityOrganizer() != null ? activity.getActivityOrganizer(): oldActivity.getActivityOrganizer());
			smt.setString(3,activity.getActivityInfo() != null ? activity.getActivityInfo() : oldActivity.getActivityInfo());
			smt.setInt(4,activity.getAttendPeople() != null ? activity.getAttendPeople() : oldActivity.getAttendPeople());
			smt.setString(5,activity.getActivitySpace() != null ? activity.getActivitySpace() : oldActivity.getActivitySpace());
			smt.setInt(6,activity.getActivityMeal() != null ? activity.getActivityMeal() : oldActivity.getActivityMeal());
			smt.setInt(7, activity.getActivityId() != null ? activity.getActivityId() : oldActivity.getActivityId());
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
			smt.setInt(1, activity.getActivityId());
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
		final String sql = "INSERT INTO activity(activityName , activityOrganizer , activityInfo , "
				+ "attendPeople , activitySpace, startSignUpDate , endSignUpDate, activityStartDate, "
				+ "activityEndDate , activityMeal)"
				+ " VALUES(? ,? ,? ,? ,? ,NOW() ,NOW() ,NOW() ,NOW() ,? )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, activity.getActivityName());
			smt.setString(2, activity.getActivityOrganizer());
			smt.setString(3, activity.getActivityInfo());
			smt.setInt(4, activity.getAttendPeople());
			smt.setString(5, activity.getActivitySpace());
			smt.setInt(6, activity.getActivityMeal());

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
	public void getActivityTypes(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM  activitytypes WHERE activityId = ? ";
		
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, activity.getActivityId());
			rs = smt.executeQuery();
			
			System.out.println(activity.getActivityId());
			while (rs.next()) {
				activity.getActivityTypes().add(rs.getString("activityType"));
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
		
	}
}
