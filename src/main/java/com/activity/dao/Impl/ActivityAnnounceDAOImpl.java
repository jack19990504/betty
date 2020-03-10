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

import com.activity.dao.ActivityAnnounceDAO;
import com.activity.entity.ActivityAnnounce;

@Repository
public class ActivityAnnounceDAOImpl implements ActivityAnnounceDAO{

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<ActivityAnnounce> getList() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<ActivityAnnounce> activityAnnounceList = new ArrayList<ActivityAnnounce>();
		final String sql = "SELECT * FROM activityannounce;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			ActivityAnnounce activityAnnounce;
			while (rs.next()) {
				activityAnnounce = new ActivityAnnounce();
				activityAnnounce.setAInum(rs.getInt("AInum"));
				activityAnnounce.setActivityId(rs.getInt("activityId"));
				activityAnnounce.setAnnounceTitle(rs.getString("announceTitle"));
				activityAnnounce.setAnnounceContent(rs.getString("announceContent"));

				activityAnnounceList.add(activityAnnounce);
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
		return activityAnnounceList;
	}

	@Override
	public ActivityAnnounce get(ActivityAnnounce activityAnnounce) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM activityannounce WHERE AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, activityAnnounce.getAInum());
			rs = smt.executeQuery();

			activityAnnounce = new ActivityAnnounce();
			if (rs.next()) {
				activityAnnounce.setAInum(rs.getInt("AInum"));
				activityAnnounce.setActivityId(rs.getInt("activityId"));
				activityAnnounce.setAnnounceTitle(rs.getString("announceTitle"));
				activityAnnounce.setAnnounceContent(rs.getString("announceContent"));
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
		return activityAnnounce;
	}

	@Override
	public void insert(ActivityAnnounce activityAnnounce) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO activityannounce(activityId, announceTitle, announceContent)"
				+ " VALUES(? ,? ,? )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setInt(1, activityAnnounce.getActivityId());
			smt.setString(2, activityAnnounce.getAnnounceTitle());
			smt.setString(3, activityAnnounce.getAnnounceContent());

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
	public void update(ActivityAnnounce oldActivityAnnounce, ActivityAnnounce activityAnnounce) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE activityannounce SET " + "announceTitle= ? ," + " announceContent = ?"
				+ " where AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,activityAnnounce.getAnnounceTitle() != null ? activityAnnounce.getAnnounceTitle() : oldActivityAnnounce.getAnnounceTitle());
			smt.setString(2, activityAnnounce.getAnnounceContent() != null ? activityAnnounce.getAnnounceContent(): oldActivityAnnounce.getAnnounceContent());
			smt.setInt(3,activityAnnounce.getAInum());
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
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM activityannounce WHERE AInum = ?";
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
