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
				
				activity.setActivityStartDate(rs.getTimestamp("activityStartDate"));
				activity.setActivityEndDate(rs.getTimestamp("activityEndDate"));
				activity.setStartSignUpDate(rs.getTimestamp("startSignUpDate"));
				activity.setEndSignUpDate(rs.getTimestamp("endSignUpDate"));

				//program control
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
			
				
				
				activity.setActivityMeal(rs.getInt("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));

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
				
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				//program control
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				
				activity.setActivityMeal(rs.getInt("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));
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
				+ "activityInfo = ?," + "attendPeople = ? ," + "activitySpace = ? ," + "startSignUpDate = ?," 
				+ "endSignUpDate = ?, " + "activityStartDate = ?," + "activityEndDate = ?," + "activityMeal = ?, " 
				+ "activityCover = ?, " + "activityLinkName = ?, " + "activityLink = ?, " + "activitySummary = ?,"
				+ "activityMoreContent = ?," + "activityPrecautions = ?" + " where activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,activity.getActivityName() != null ? activity.getActivityName() : oldActivity.getActivityName());
			smt.setString(2, activity.getActivityOrganizer() != null ? activity.getActivityOrganizer(): oldActivity.getActivityOrganizer());
			smt.setString(3,activity.getActivityInfo() != null ? activity.getActivityInfo() : oldActivity.getActivityInfo());
			smt.setInt(4,activity.getAttendPeople() != null ? activity.getAttendPeople() : oldActivity.getAttendPeople());
			smt.setString(5,activity.getActivitySpace() != null ? activity.getActivitySpace() : oldActivity.getActivitySpace());
			smt.setTimestamp(6,activity.getStartSignUpDate() != null ? activity.getStartSignUpDate() : oldActivity.getStartSignUpDate());
			smt.setTimestamp(7,activity.getEndSignUpDate() != null ? activity.getEndSignUpDate() : oldActivity.getEndSignUpDate());
			smt.setTimestamp(8,activity.getActivityStartDate() != null ? activity.getActivityStartDate() : oldActivity.getActivityStartDate());
			smt.setTimestamp(9,activity.getActivityEndDate() != null ? activity.getActivityEndDate() : oldActivity.getActivityEndDate());
			smt.setInt(10,activity.getActivityMeal() != null ? activity.getActivityMeal() : oldActivity.getActivityMeal());
			smt.setString(11,activity.getActivityCover() != null ? activity.getActivityCover() : oldActivity.getActivityCover());
			smt.setString(12,activity.getActivityLinkName() != null ? activity.getActivityLinkName() : oldActivity.getActivityLinkName());
			smt.setString(13,activity.getActivityLink() != null ? activity.getActivityLink() : oldActivity.getActivityLink());
			smt.setString(14,activity.getActivitySummary() != null ? activity.getActivitySummary() : oldActivity.getActivitySummary());
			smt.setString(15,activity.getActivityMoreContent() != null ? activity.getActivityMoreContent() : oldActivity.getActivityMoreContent());
			smt.setString(16,activity.getActivityPrecautions() != null ? activity.getActivityPrecautions() : oldActivity.getActivityPrecautions());
			smt.setInt(17, activity.getActivityId());
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
				+ "activityEndDate , activityMeal , activityLinkName, activityLink, activitySummary, "
				+ "activityMoreContent, activityPrecautions,activityCover)"
				+ " VALUES(? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?, ?, ?, ? , ?)";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, activity.getActivityName());
			smt.setString(2, activity.getActivityOrganizer());
			smt.setString(3, activity.getActivityInfo());
			smt.setInt(4, activity.getAttendPeople());
			smt.setString(5, activity.getActivitySpace());
			smt.setTimestamp(6, activity.getStartSignUpDate());
			smt.setTimestamp(7, activity.getEndSignUpDate());
			smt.setTimestamp(8, activity.getActivityStartDate());
			smt.setTimestamp(9, activity.getActivityEndDate());
			smt.setInt(10, activity.getActivityMeal());
			smt.setString(11,activity.getActivityLinkName());
			smt.setString(12,activity.getActivityLink());
			smt.setString(13,activity.getActivitySummary());
			smt.setString(14,activity.getActivityMoreContent());
			smt.setString(15,activity.getActivityPrecautions());
			smt.setString(16, activity.getActivityCover());

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

	@Override
	public List<Activity> getListByType(String... strings) {
		// TODO Auto-generated method stub
		List<Activity> activityList = new ArrayList<Activity>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		String stats ="";
		for(String a : strings)
		{
			stats = stats + a + " or act.activityType = ";
		}
		stats = stats.substring(0, stats.length()-23);
		final String sql = "SELECT * FROM `activitytypes` act " + 
				"join activity a on " + 
				"act.activityId = a.activityId " + 
				"where act.activityType = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, stats);
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
				
				activity.setActivityStartDate(rs.getTimestamp("activityStartDate"));
				activity.setActivityEndDate(rs.getTimestamp("activityEndDate"));
				activity.setStartSignUpDate(rs.getTimestamp("startSignUpDate"));
				activity.setEndSignUpDate(rs.getTimestamp("endSignUpDate"));
				//program control
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				
				activity.setActivityMeal(rs.getInt("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));

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
	public void updateCover(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE activity SET activityCover = ? where activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, activity.getActivityCover());
			smt.setInt(2, activity.getActivityId());
			

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
	public List<Activity> getOrganizerActivityList(Activity activity) {
		// TODO Auto-generated method stub
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Activity> activityOrganizerList = new ArrayList<Activity>();
		final String sql = "SELECT * FROM activity WHERE activityOrganizer = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, activity.getActivityOrganizer());
			rs = smt.executeQuery();
			while (rs.next()) {
				activity = new Activity();
				activity.setActivityId(rs.getInt("activityId"));
				activity.setActivityName(rs.getString("activityName"));
				activity.setActivityOrganizer(rs.getString("activityOrganizer"));
				activity.setActivityInfo(rs.getString("activityInfo"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setActivitySpace(rs.getString("activitySpace"));
				
				activity.setActivityStartDate(rs.getTimestamp("activityStartDate"));
				activity.setActivityEndDate(rs.getTimestamp("activityEndDate"));
				activity.setStartSignUpDate(rs.getTimestamp("startSignUpDate"));
				activity.setEndSignUpDate(rs.getTimestamp("endSignUpDate"));
				
				//program control
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				
				
				activity.setActivityMeal(rs.getInt("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));

				activityOrganizerList.add(activity);
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
		return activityOrganizerList;
	}

	@Override
	public Activity getActivityByCols(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM activity WHERE activityName = ? and " + 
		" activityOrganizer = ? and "+ "activityInfo = ? and " + "attendPeople = ? and " + 
		"activitySpace = ? and "  + "activityMeal = ? and " +
		"activityCover = ? and " + "activityLinkName = ? ORDER BY activityId DESC LIMIT 1";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1,activity.getActivityName());
			smt.setString(2, activity.getActivityOrganizer());
			smt.setString(3,activity.getActivityInfo());
			smt.setInt(4,activity.getAttendPeople());
			smt.setString(5,activity.getActivitySpace());
			smt.setInt(6,activity.getActivityMeal() );
			smt.setString(7,activity.getActivityCover());
			smt.setString(8,activity.getActivityLinkName());
			rs = smt.executeQuery();

			activity = new Activity();
			if (rs.next()) {
				activity.setActivityId(rs.getInt("activityId"));
				activity.setActivityName(rs.getString("activityName"));
				activity.setActivityOrganizer(rs.getString("activityOrganizer"));
				activity.setActivityInfo(rs.getString("activityInfo"));
				activity.setAttendPeople(rs.getInt("attendPeople"));
				activity.setActivitySpace(rs.getString("activitySpace"));
				
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				//program control
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString() : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString() : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString() : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString() : "");
				
				activity.setActivityMeal(rs.getInt("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));
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
	

	
	
}
