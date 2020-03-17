package com.activity.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
				
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				ft.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
				Date d1 = new Date(rs.getTimestamp("activityStartDate").getTime());
				Date d2 = new Date(rs.getTimestamp("activityEndDate").getTime());
				Date d3 = new Date(rs.getTimestamp("startSignUpDate").getTime());
				Date d4 = new Date(rs.getTimestamp("endSignUpDate").getTime());
				
				activity.setActivityStartDateString(ft.format(d1));
				activity.setActivityEndDateString(ft.format(d2));
				activity.setStartSignUpDateString(ft.format(d3));
				activity.setEndSignUpDateString(ft.format(d4));
				
				
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
				
				activity.setActivityStartDate(rs.getTimestamp("activityStartDate"));
				activity.setActivityEndDate(rs.getTimestamp("activityEndDate"));
				activity.setStartSignUpDate(rs.getTimestamp("startSignUpDate"));
				activity.setEndSignUpDate(rs.getTimestamp("endSignUpDate"));
				
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				ft.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
				Date d1 = new Date(rs.getTimestamp("activityStartDate").getTime());
				Date d2 = new Date(rs.getTimestamp("activityEndDate").getTime());
				Date d3 = new Date(rs.getTimestamp("startSignUpDate").getTime());
				Date d4 = new Date(rs.getTimestamp("endSignUpDate").getTime());
				
				activity.setActivityStartDateString(ft.format(d1));
				activity.setActivityEndDateString(ft.format(d2));
				activity.setStartSignUpDateString(ft.format(d3));
				activity.setEndSignUpDateString(ft.format(d4));
				
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
				
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				ft.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
				Date d1 = new Date(rs.getTimestamp("activityStartDate").getTime());
				Date d2 = new Date(rs.getTimestamp("activityEndDate").getTime());
				Date d3 = new Date(rs.getTimestamp("startSignUpDate").getTime());
				Date d4 = new Date(rs.getTimestamp("endSignUpDate").getTime());
				
				activity.setActivityStartDateString(ft.format(d1));
				activity.setActivityEndDateString(ft.format(d2));
				activity.setStartSignUpDateString(ft.format(d3));
				activity.setEndSignUpDateString(ft.format(d4));
				
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
	
	
}
