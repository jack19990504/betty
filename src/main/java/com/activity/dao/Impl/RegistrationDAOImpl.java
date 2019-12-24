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
import com.activity.dao.RegistrationDAO;
import com.activity.entity.Registration;;
@Repository
public class RegistrationDAOImpl implements RegistrationDAO{

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Registration> getUserRegistration(String UserLineId) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registerList = new ArrayList<>();
		final String sql = "SELECT r.* FROM Registration r JOIN member m "
				+ "ON r.member_Email = m.memberEmail where m.memberLineId = ? ;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, UserLineId);
			rs = smt.executeQuery();
			Registration register;
			while (rs.next()) {
				register = new Registration();
				register.setActivityName(rs.getString("activityName"));
				registerList.add(register);
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
		return registerList;
	}
	

}
