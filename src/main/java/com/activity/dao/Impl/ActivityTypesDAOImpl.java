package com.activity.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.activity.dao.ActivityTypesDAO;
import com.activity.entity.ActivityTypes;

@Repository
public class ActivityTypesDAOImpl implements ActivityTypesDAO{

	
	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertActivityTypes(ActivityTypes activityTypes) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO activitytypes (activityId, activityType) VALUES (? , ?);";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setInt(1, activityTypes.getActivityId());
			smt.setString(2, activityTypes.getActivityType());

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
	public void deleteActivityTypes(Integer id) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM activitytypes WHERE activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, id);
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
