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

import com.activity.dao.FeedbackDAO;
import com.activity.entity.Feedback;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO{

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Feedback> getActivityFeedback(int id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		final String sql = "SELECT * FROM feedback where activity_Id = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, id);
			rs = smt.executeQuery();
			Feedback feedback;
			while (rs.next()) {
				feedback = new Feedback();
				feedback.setAInum(rs.getInt("AInum"));
				feedback.setMember_Email(rs.getString("member_email"));
				feedback.setActivity_Id(rs.getInt("activity_Id"));
				feedback.setPlaceFeedback(rs.getInt("placeFeedback"));
				feedback.setScheduleFeedback(rs.getInt("scheduleFeedback"));
				feedback.setProcessFeedback(rs.getInt("processFeedback"));
				feedback.setContentFeedback(rs.getInt("contentFeedback"));
				feedback.setStaffFeedback(rs.getInt("staffFeedback"));
				feedback.setOverallFeedback(rs.getInt("overallFeedback"));
				feedback.setSuggestFeedback(rs.getString("suggestFeedback"));
				
				feedbackList.add(feedback);
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
		return feedbackList;
	}

	@Override
	public Feedback get(Feedback feedback) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM feedback where AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, feedback.getAInum());
			rs = smt.executeQuery();
			feedback = new Feedback();
			while (rs.next()) {
				feedback.setAInum(rs.getInt("AInum"));
				feedback.setMember_Email(rs.getString("member_Email"));
				feedback.setActivity_Id(rs.getInt("activity_Id"));
				feedback.setPlaceFeedback(rs.getInt("placeFeedback"));
				feedback.setScheduleFeedback(rs.getInt("scheduleFeedback"));
				feedback.setProcessFeedback(rs.getInt("processFeedback"));
				feedback.setContentFeedback(rs.getInt("contentFeedback"));
				feedback.setStaffFeedback(rs.getInt("staffFeedback"));
				feedback.setOverallFeedback(rs.getInt("overallFeedback"));
				feedback.setSuggestFeedback(rs.getString("suggestFeedback"));
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
		return feedback;
	}
	
	@Override
	public Feedback getOne(Feedback feedback) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM feedback where member_Email = ? and activity_Id = ? ";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, feedback.getMember_Email());
			smt.setInt(2, feedback.getActivity_Id());
			rs = smt.executeQuery();
			feedback = new Feedback();
			if (rs.next()) {
				feedback.setAInum(rs.getInt("AInum"));
				feedback.setMember_Email(rs.getString("member_Email"));
				feedback.setActivity_Id(rs.getInt("activity_Id"));
				feedback.setPlaceFeedback(rs.getInt("placeFeedback"));
				feedback.setScheduleFeedback(rs.getInt("scheduleFeedback"));
				feedback.setProcessFeedback(rs.getInt("processFeedback"));
				feedback.setContentFeedback(rs.getInt("contentFeedback"));
				feedback.setStaffFeedback(rs.getInt("staffFeedback"));
				feedback.setOverallFeedback(rs.getInt("overallFeedback"));
				feedback.setSuggestFeedback(rs.getString("suggestFeedback"));
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
		return feedback;
	}

	@Override
	public void insert(Feedback feedback) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO feedback(member_Email, activity_Id, placeFeedback, "
				+ "scheduleFeedback, processFeedback, contentFeedback, "
				+ "staffFeedback, overallFeedback, suggestFeedback) "
				+ "VALUES(? , ? , ? , ? , ? , ? , ? , ? , ?)";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, feedback.getMember_Email());
			smt.setInt(2, feedback.getActivity_Id());
			smt.setInt(3, feedback.getPlaceFeedback());
			smt.setInt(4, feedback.getScheduleFeedback());
			smt.setInt(5, feedback.getProcessFeedback());
			smt.setInt(6, feedback.getContentFeedback());
			smt.setInt(7, feedback.getStaffFeedback());
			smt.setInt(8, feedback.getOverallFeedback());
			smt.setString(9, feedback.getSuggestFeedback());
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
