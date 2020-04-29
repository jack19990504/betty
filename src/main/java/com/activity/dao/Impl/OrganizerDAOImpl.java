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

import com.activity.dao.OrganizerDAO;
import com.activity.entity.Organizer;
import com.activity.entity.Search;
@Repository
public class OrganizerDAOImpl implements OrganizerDAO{
	@Autowired
	private DataSource dataSource;
	
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insert(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO organizer(memberEmail, organizerName, organizerPhone, organizerInfo, organizerEmail, organizerAddress) "
				+ "VALUES(? , ? ,? , ? , ? ,? )";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMemberEmail());
			smt.setString(2, organizer.getOrganizerName());
			smt.setString(3, organizer.getOrganizerPhone());
			smt.setString(4, organizer.getOrganizerInfo());
			smt.setString(5, organizer.getOrganizerEmail());
			smt.setString(6, organizer.getOrganizerAddress());
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
	public void update(Organizer oldOrganizer, Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE organizer SET "+"organizerName = ? ," + " organizerPhone = ?, " + "organizerInfo = ? ,"
				+ "organizerEmail = ? ," + "organizerAddress = ? " 
				+ " where memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1,organizer.getOrganizerName() != null ? organizer.getOrganizerName() : oldOrganizer.getOrganizerName());
			smt.setString(2,organizer.getOrganizerPhone() != null ? organizer.getOrganizerPhone() : oldOrganizer.getOrganizerPhone());
			smt.setString(3,organizer.getOrganizerInfo() != null ? organizer.getOrganizerInfo(): oldOrganizer.getOrganizerInfo());
			smt.setString(4,organizer.getOrganizerEmail() != null ? organizer.getOrganizerEmail() : oldOrganizer.getOrganizerEmail()); //有需要嗎
			smt.setString(5,organizer.getOrganizerAddress() != null ? organizer.getOrganizerAddress() : oldOrganizer.getOrganizerAddress());
			smt.setString(6,organizer.getMemberEmail());
			
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
	public void delete(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM organizer WHERE memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMemberEmail());
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
	public List<Organizer> getList(){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Organizer> organizerList = new ArrayList<Organizer>();
		final String sql = "SELECT * FROM organizer";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Organizer organizer;
			while (rs.next()) {
				organizer = new Organizer();
				organizer.setMemberEmail(rs.getString("memberEmail"));
				organizer.setOrganizerName(rs.getString("organizerName"));
				organizer.setOrganizerPhone(rs.getString("organizerPhone"));
				organizer.setOrganizerInfo(rs.getString("organizerinfo"));
				organizer.setOrganizerEmail(rs.getString("organizerEmail"));
				organizer.setOrganizerAddress(rs.getString("organizerAddress"));
				
				organizerList.add(organizer);
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
		return organizerList;
	}

	@Override
	public Organizer get(Organizer organizer) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `organizer` where memberEmail = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, organizer.getMemberEmail());
			rs = smt.executeQuery();
			organizer = new Organizer();
			if (rs.next()) {
				organizer.setOrganizerName(rs.getString("organizerName"));
				organizer.setOrganizerPhone(rs.getString("organizerPhone"));
				organizer.setOrganizerInfo(rs.getString("organizerInfo"));
				organizer.setOrganizerEmail(rs.getString("organizerEmail"));
				organizer.setOrganizerAddress(rs.getString("organizerAddress"));
				organizer.setMemberEmail(rs.getString("memberEmail"));
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
		return organizer;
	}
	
	@Override
	public List<Organizer> getOrganizerSearch(Search search) {
		// TODO Auto-generated method stub
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Organizer> organizerList = new ArrayList<Organizer>();
		final String sql = "SELECT * FROM organizer where organizerName like ? ";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, "%"+search.getSearch()+"%");
			rs = smt.executeQuery();
			Organizer organizer;
			while (rs.next()) {
				organizer = new Organizer();
				organizer.setMemberEmail(rs.getString("memberEmail"));
				organizer.setOrganizerName(rs.getString("organizerName"));
				organizer.setOrganizerInfo(rs.getString("organizerInfo"));
				organizer.setOrganizerAddress(rs.getString("organizerAddress"));
				organizer.setOrganizerEmail(rs.getString("organizerEmail"));
				organizer.setOrganizerPhone(rs.getString("organizerPhone"));
				
				
				organizerList.add(organizer);
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
		return organizerList;
	}
}
