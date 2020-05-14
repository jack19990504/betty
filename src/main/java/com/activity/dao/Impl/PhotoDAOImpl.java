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

import com.activity.dao.PhotoDAO;
import com.activity.engine.entity.Face;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Photo;

@Repository
public class PhotoDAOImpl implements PhotoDAO {

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void writePhoto(Face face) {

		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO memberphoto (photoId , memberEmail) VALUES " + "(? , ? );";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, face.getImageSourcePath().substring(face.getImageSourcePath().lastIndexOf("assets")));
			smt.setString(2, face.getPersonId());

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
	public void writePhoto(Integer activityId, String photoPath) {

		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO photo (photoId , activityId) VALUES " + "(? , ? );";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, photoPath);
			smt.setInt(2, activityId);

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
	public List<Photo> getMemberPhoto(Member member,Integer activityId) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		List<Photo> photoList = new ArrayList<>();
		final String sql = "SELECT * from memberphoto m  JOIN photo p ON m.photoId=p.photoId WHERE memberEmail = ? "
				+ "AND p.activityId = ? ;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, member.getMemberEmail());
			smt.setInt(2, activityId);

			rs = smt.executeQuery();
			Photo memberPhoto ;
			while(rs.next())
			{
				memberPhoto = new Photo();
				memberPhoto.setAINum(rs.getInt("AINum"));
				memberPhoto.setMemberEmail(rs.getString("memberEmail"));
				memberPhoto.setPhotoId(rs.getString("photoId"));
				memberPhoto.setActivityId(rs.getInt("activityId"));
				photoList.add(memberPhoto);
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
		return photoList;
	}

	@Override
	public List<Photo> getActivityPhoto(Activity activity) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		List<Photo> photoList = new ArrayList<>();
		final String sql = "SELECT * from photo WHERE activityId = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setInt(1, activity.getActivityId());

			rs = smt.executeQuery();
			Photo Photo ;
			while(rs.next())
			{
				Photo = new Photo();
				Photo.setActivityId(rs.getInt("activityId"));
				Photo.setPhotoId(rs.getString("photoId"));
				
				photoList.add(Photo);
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
		return photoList;
	}

	@Override
	public void deletePhoto(Photo photo) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM photo WHERE photoId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, photo.getPhotoId());
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
	public void deleteMemberPhoto(Photo photo) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM memberphoto WHERE AINum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, photo.getAINum()	);
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
	public void deleteAllMemberPhoto(Integer activityId) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE memberphoto from memberphoto JOIN photo ON "
				+ " memberphoto.photoId = photo.photoId WHERE activityId = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, activityId);
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
