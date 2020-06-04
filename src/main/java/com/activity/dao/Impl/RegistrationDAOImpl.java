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
	public List<Registration> getUserRegistration(String UserLineId) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Registration> registerList = new ArrayList<>();
		final String sql = "SELECT * FROM Registration r JOIN member m " + "ON r.member_Email = m.memberEmail"
				+ " JOIN activity a ON r.activity_Id = a.activityId " + " where m.memberLineId = ?  and a.activityEndDate > NOW();";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, UserLineId);
			rs = smt.executeQuery();
			
			Registration registration;
			Member member;
			Activity activity;
			while (rs.next()) {
				
				member = new Member();
				activity = new Activity();
				registration = new Registration();
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));
				
				
				//member
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberBirthday(rs.getTimestamp("memberBirthday"));

				//program control
				member.setMemberBirthdayString(rs.getTimestamp("memberBirthday") != null ? rs.getTimestamp("memberBirthday").toString().substring(0,10) : "");
				
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberLineId(rs.getString("memberLineId"));
				member.setMemberGender(rs.getString("memberGender"));
				member.setMemberID(rs.getString("memberID"));
				member.setMemberBloodType(rs.getString("memberBloodType"));
				member.setEmergencyContact(rs.getString("emergencyContact"));
				member.setEmergencyContactRelation(rs.getString("emergencyContactRelation"));
				member.setEmergencyContactPhone(rs.getString("emergencyContactPhone"));
				
				
				//activity
				
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
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString().substring(0,16) : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString().substring(0,16) : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString().substring(0,16) : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString().substring(0,16) : "");
				
				registration.setMember(member);
				registration.setActivity(activity);
				
				registerList.add(registration);
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
		final String sql = "SELECT * FROM `registration` r join member m on r.member_Email = m.memberEmail where AInum = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, registration.getAInum());
			rs = smt.executeQuery();
			registration = new Registration();
			Member member = new Member();
			if (rs.next()) {
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));
				registration.setCancelRegistration(rs.getTimestamp("cancelRegistration"));
				//member
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberBirthday(rs.getTimestamp("memberBirthday"));

				//program control
				member.setMemberBirthdayString(rs.getTimestamp("memberBirthday") != null ? rs.getTimestamp("memberBirthday").toString().substring(0,10) : "");
				
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberLineId(rs.getString("memberLineId"));
				member.setMemberGender(rs.getString("memberGender"));
				member.setMemberID(rs.getString("memberID"));
				member.setMemberBloodType(rs.getString("memberBloodType"));
				member.setEmergencyContact(rs.getString("emergencyContact"));
				member.setEmergencyContactRelation(rs.getString("emergencyContactRelation"));
				member.setEmergencyContactPhone(rs.getString("emergencyContactPhone"));
				
				
				
				registration.setMember(member);
				
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
			smt.setInt(4, registration.getRegistrationMeal()!=null ?registration.getRegistrationMeal() : 0);
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
				+ " r.activity_Id = a.activityId join member m on m.memberEmail "
				+ " = r.member_Email where a.activityId = ?;";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setInt(1, id);
			rs = smt.executeQuery();
			Registration registration;
			Member member;
			while (rs.next()) {
				member = new Member();
				registration = new Registration();
				member = new Member();
				
				
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberBirthday(rs.getTimestamp("memberBirthday"));
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberLineId(rs.getString("memberLineId"));
				
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));

				registration.setMember(member);
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
		final String sql = "SELECT * FROM registration r " + " JOIN member m on r.member_Email = m.memberEmail "
				+ " join activity a on a.activityId = r.activity_Id where r.activity_Id = ?;";
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
				registration.getMember().setMemberPhone(rs.getString("memberPhone"));
				registration.getMember().setMemberPassword(rs.getString("memberPassword"));
				registration.getMember().setMemberBirthday(rs.getTimestamp("memberBirthday"));
				registration.getMember().setMemberLineId(rs.getString("memberLineId"));
				registration.getMember().setMemberID(rs.getString("memberID"));
				registration.getMember().setMemberBloodType(rs.getString("memberBloodType"));
				registration.getMember().setMemberLineId(rs.getString("memberLineId"));
				registration.getMember().setMemberPhone(rs.getString("memberPhone"));
				
				registration.getActivity().setActivityName(rs.getString("activityName"));

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
		final String sql = "SELECT COUNT(member_Email) as 'registrationPeople' " + " FROM registration "
				+ " WHERE activity_Id = ?;";
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
		final String sql = "SELECT * FROM `registration` r JOIN member m ON r.member_Email = m.memberEmail join activity a on r.activity_Id = a.activityId where member_Email = ? ";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, registration.getMember_Email());
			rs = smt.executeQuery();
			Member member;
			Activity activity;
			while (rs.next()) {
				member = new Member();
				activity = new Activity();
				registration = new Registration();
				registration.setAInum(rs.getInt("AInum"));
				registration.setMember_Email(rs.getString("member_Email"));
				registration.setActivity_Id(rs.getInt("activity_Id"));
				registration.setRegistrationRemark(rs.getString("registrationRemark"));
				registration.setRegistrationMeal(rs.getInt("registrationMeal"));
				registration.setIsSignIn(rs.getInt("isSignIn"));
				registration.setIsSignOut(rs.getInt("isSignOut"));
				registration.setCancelRegistration(rs.getTimestamp("cancelRegistration"));
				
				
				//member
				member.setMemberName(rs.getString("memberName"));
				member.setMemberAddress(rs.getString("memberAddress"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberPassword(rs.getString("memberPassword"));
				member.setMemberBirthday(rs.getTimestamp("memberBirthday"));

				//program control
				member.setMemberBirthdayString(rs.getTimestamp("memberBirthday") != null ? rs.getTimestamp("memberBirthday").toString().substring(0,10) : "");
				
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberLineId(rs.getString("memberLineId"));
				member.setMemberGender(rs.getString("memberGender"));
				member.setMemberID(rs.getString("memberID"));
				member.setMemberBloodType(rs.getString("memberBloodType"));
				member.setEmergencyContact(rs.getString("emergencyContact"));
				member.setEmergencyContactRelation(rs.getString("emergencyContactRelation"));
				member.setEmergencyContactPhone(rs.getString("emergencyContactPhone"));
				
				
				//activity
				
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
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString().substring(0,16) : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString().substring(0,16) : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString().substring(0,16) : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString().substring(0,16) : "");
				
				
				activity.setActivityMeal(rs.getString("activityMeal"));
				activity.setActivityCover(rs.getString("activityCover"));
				activity.setActivityLinkName(rs.getString("activityLinkName"));
				activity.setActivityLink(rs.getString("activityLink"));
				activity.setActivitySummary(rs.getString("activitySummary"));
				activity.setActivityMoreContent(rs.getString("activityMoreContent"));
				activity.setActivityPrecautions(rs.getString("activityPrecautions"));

				registration.setMember(member);
				registration.setActivity(activity);
				
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

	@Override
	public void signInByMemberEmail(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET " + " isSignIn = 1 where member_Email = ? and activity_Id = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			
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
	public void signOutByMemberEmail(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET isSignOut = 1 where member_Email = ? and activity_Id = ?";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			
			smt.setString(1, registration.getMember_Email());
			smt.setInt(2, registration.getActivity_Id());
			
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
	public List<Activity> getUserIsSignUpSameDay(Activity activity,String memberEmail) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement smt = null;
		List<Activity> activityList = new ArrayList<Activity>();
		final String sql = "SELECT  r.*, a.* " + 
				"FROM activity a JOIN registration r ON a.activityId = r.activity_Id " + 
				"where DATE(a.activityStartDate) = DATE(?) and r.member_Email = ? and r.cancelRegistration is null";
		try {
			conn = dataSource.getConnection();
			smt = conn.prepareStatement(sql);
			smt.setString(1, activity.getActivityStartDateString());
			smt.setString(2,memberEmail);
			System.out.println(memberEmail);
			System.out.println(activity.getActivityStartDateString());
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
				activity.setActivityStartDateString(rs.getTimestamp("activityStartDate") != null ? rs.getTimestamp("activityStartDate").toString().substring(0,16) : "");
				activity.setActivityEndDateString(rs.getTimestamp("activityEndDate") != null ? rs.getTimestamp("activityEndDate").toString().substring(0,16) : "");
				activity.setStartSignUpDateString(rs.getTimestamp("startSignUpDate") != null ? rs.getTimestamp("startSignUpDate").toString().substring(0,16) : "");
				activity.setEndSignUpDateString(rs.getTimestamp("endSignUpDate") != null ? rs.getTimestamp("endSignUpDate").toString().substring(0,16) : "");
				
				
				activity.setActivityMeal(rs.getString("activityMeal"));
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
	public void signInByAINum(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET " + " isSignIn = 1 where AInum = ?";
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
	public void signOutByAINum(Registration registration) {
		Connection conn = null;
		PreparedStatement smt = null;
		final String sql = "UPDATE registration SET " + " isSignOut = 1 where AInum = ?";
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
	
}
