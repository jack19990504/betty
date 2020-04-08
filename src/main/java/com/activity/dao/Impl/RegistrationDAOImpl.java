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
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;;

@Repository
public class RegistrationDAOImpl implements RegistrationDAO {

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<String> getUserRegistration(String UserLineId) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<String> registerList = new ArrayList<>();
		final String sql = "SELECT r.* FROM Registration r JOIN member m " + "ON r.member_Email = m.memberEmail"
				+ " JOIN activity a ON r.activity_Id = a.activityId " + " where m.memberLineId = ? ;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, UserLineId);
			rs = smt.executeQuery();
			String activityName = "";
			while (rs.next()) {

				activityName = rs.getString("activityName");
				registerList.add(activityName);
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

	public Registration get(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `registration`  where AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, registration.getAInum());
			rs = smt.executeQuery();
			registration = new Registration();
			if (rs.next()) {
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));
				registration.setCancelRegistration(rs.getTimestamp("cancelRegistration"));
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
		return registration;
	}

	public List<Registration> getList() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registrationList = new ArrayList<Registration>();
		final String sql = "SELECT * FROM registration;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			rs = smt.executeQuery();
			Registration registration;
			while (rs.next()) {
				registration = new Registration();
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));

				registrationList.add(registration);
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
		return registrationList;
	}

	public void insert(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "INSERT INTO registration(member_Email, activity_Id, registrationRemark, registrationMeal, isSignIn , isSignOut) "
				+ "VALUES(? , ? ,? , ? , 0 , 0)";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			smt.setString(3, registration.getRegistrationRemark());
			smt.setInt(4, registration.getRegistrationMeal());
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

	public void update(Registration oldRegistration, Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET " + " registrationRemark = ?, " + "registrationMeal = ? ,"
				+ " isSignIn = ? , isSignOut = ?" + " where AInum = ? ";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setString(1, registration.getRegistrationRemark() != null ? registration.getRegistrationRemark()
					: oldRegistration.getRegistrationRemark());
			smt.setInt(2, registration.getRegistrationMeal() != null ? registration.getRegistrationMeal()
					: oldRegistration.getRegistrationMeal());
			smt.setInt(3,
					registration.getIsSignIn() != null ? registration.getIsSignIn() : oldRegistration.getIsSignIn());
			smt.setInt(4,
					registration.getIsSignOut() != null ? registration.getIsSignOut() : oldRegistration.getIsSignOut());
			smt.setInt(5, registration.getAInum());

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

	public void delete(int id) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "DELETE FROM registration WHERE AInum = ?;";
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

	@Override
	public List<Registration> getActivityList(int id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registrationList = new ArrayList<Registration>();
		final String sql = "SELECT * FROM registration r JOIN activity a on "
				+ "r.activity_Id = a.activityId where a.activityId = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, id);
			rs = smt.executeQuery();
			Registration registration;
			while (rs.next()) {
				registration = new Registration();
				registration.setMember_Email(rs.getString("member_email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));

				registrationList.add(registration);
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
		return registrationList;
	}

	@Override
	public void updateCancelTime(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET cancelRegistration = NOW() where AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);

			smt.setInt(1, registration.getAInum());

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
	public Integer getUserCancelTimeInMonth(Member member) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT  COUNT(cancelRegistration) as 'canceltime' "
				+ "FROM registration WHERE cancelRegistration BETWEEN date_sub(NOW(),INTERVAL 30 DAY) and  NOW()"
				+ " and member_Email = ?;";
		Integer cancelTime = 0;
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getMemberEmail());
			rs = smt.executeQuery();

			if (rs.next()) {
				cancelTime = rs.getInt("canceltime");
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
		return cancelTime;
	}

	@Override
	public Registration getOneRegistration(Registration registration) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM `registration`  " + " where member_Email = ? and activity_Id = ? "
				+ " and cancelRegistration IS NULL";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			rs = smt.executeQuery();
			registration = new Registration();
			while (rs.next()) {
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));
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
		return registration;
	}

	@Override
	public Integer getNoCancelAndNoAttend(Member member) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT COUNT(isSignIn) as 'notAttend' " + "FROM registration r join activity a "
				+ "WHERE a.activityStartDate BETWEEN date_sub(NOW(),INTERVAL 30 DAY) and NOW() "
				+ "and r.activity_Id = a.activityId and isSignIn = 0 " + "and member_Email = ? ;";
		Integer notAttendTime = 0;
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, member.getMemberEmail());
			rs = smt.executeQuery();

			if (rs.next()) {
				notAttendTime = rs.getInt("notAttend");
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
		return notAttendTime;
	}

	@Override
	public List<Registration> getListWithMemberInformation(int id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registrationList = new ArrayList<Registration>();
		final String sql = "SELECT r.* , m.* FROM registration r " + " JOIN member m on r.member_Email = m.memberEmail "
				+ " where r.activity_Id = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, id);
			rs = smt.executeQuery();
			Registration registration;
			while (rs.next()) {
				registration = new Registration();
				registration.setMember_Email(rs.getString("member_email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));

				registration.getMember().setEmergencyContact(rs.getString("emergencyContact"));
				registration.getMember().setEmergencyContactPhone(rs.getString("emergencyContactPhone"));
				registration.getMember().setEmergencyContactRelation(rs.getString("emergencyContactRelation"));
				registration.getMember().setMemberGender(rs.getString("memberGender"));
				registration.getMember().setMemberName(rs.getString("memberName"));
				registration.getMember().setMemberAddress(rs.getString("memberAddress"));
				registration.getMember().setMemberPassword(rs.getString("memberPassword"));
				registration.getMember().setMemberBirthday(rs.getTimestamp("memberBirthday"));
				registration.getMember().setMemberLineId(rs.getString("memberLineId"));
				registration.getMember().setMemberID(rs.getString("memberID"));
				registration.getMember().setMemberBloodType(rs.getString("memberBloodType"));
				registration.getMember().setMemberLineId(rs.getString("memberLineId"));

				registrationList.add(registration);
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
		return registrationList;
	}

	@Override
	public Integer checkAttendPeople(Registration registration) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		final String sql = "SELECT COUNT(member_Email) as 'registrationPeople' " + "FROM registration"
				+ "WHERE activity_Id = ?;";
		Integer attendPeople = 0;
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, registration.getActivity_Id());
			rs = smt.executeQuery();

			if (rs.next()) {
				attendPeople = rs.getInt("registrationPeople");
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
		return attendPeople;
	}

	@Override
	public List<Registration> getMemberList(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		List<Registration> RegistrationList = new ArrayList<>();
		final String sql = "SELECT * FROM `registration` where member_Email = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			rs = smt.executeQuery();
			
			while (rs.next()) {
				registration = new Registration();
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));

				RegistrationList.add(registration);
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
		return RegistrationList;
	}

}
