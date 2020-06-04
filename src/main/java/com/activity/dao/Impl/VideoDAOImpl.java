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

import com.activity.dao.VideoDAO;
import com.activity.entity.Video;
@Repository
public class VideoDAOImpl implements VideoDAO {

	
	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void insert(Video video) {
		
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT into video (videoId , activity_Id) VALUES ( ?,?);";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, video.getVideoId().substring(video.getVideoId().lastIndexOf("assets")));
			smt.setInt(2, video.getActivity_Id());

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
	public List<Video> getList(Integer activityId) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		List<Video> videoList = new ArrayList<>();
		final String sql = "SELECT * from video WHERE activity_Id = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setInt(1, activityId);

			rs = smt.executeQuery();
			Video video ;
			while(rs.next())
			{
				video = new Video();
				video.setVideoId(rs.getString("videoId"));
				video.setActivity_Id(rs.getInt("activity_Id"));
				videoList.add(video);
			}
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
		return	videoList;
	
	}
	}

	


