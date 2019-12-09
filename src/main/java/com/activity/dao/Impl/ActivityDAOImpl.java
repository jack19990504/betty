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
}
