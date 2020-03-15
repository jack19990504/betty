package com.activity.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.activity.dao.PhotoDAO;
import com.activity.engine.entity.Face;

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
		final String sql = "INSERT INTO memberphoto (photoId , memberEmail) VALUES "
				+ "(? , ? );";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, face.getImageSourcePath());
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

}
