package com.activity.entity;

import java.sql.Timestamp;

import com.activity.util.DateUtil;

public class Member {

	private String memberEmail;
	
	private String memberPassword;
	
	private String memberName;
	
	private String memberGender;
	
	private Timestamp memberBirthday;
	
	//program control
	
	private String memberBirthdayString;
	
	
	
	private String memberPhone;
	
	private String memberAddress;
	
	private String memberEncodedPassword;
	
	private Integer memberEnabled;
	
	private String memberLineId;
	
	private Integer memberType;
	
	private String memberID;
	
	private String memberBloodType;
	
	private String emergencyContact;
	
	private String emergencyContactRelation;
	
	private String emergencyContactPhone;

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public Timestamp getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(Timestamp memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public String getMemberBirthdayString() {
		
		return memberBirthdayString;
	}

	public void setMemberBirthdayString(String memberBirthdayString) {
		this.memberBirthdayString = memberBirthdayString;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberLineId() {
		return memberLineId;
	}

	public void setMemberLineId(String memberLineId) {
		this.memberLineId = memberLineId;
	}

	public String getMemberEncodedPassword() {
		return memberEncodedPassword;
	}

	public void setMemberEncodedPassword(String memberEncodedPassword) {
		this.memberEncodedPassword = memberEncodedPassword;
	}

	public Integer getMemberEnabled() {
		return memberEnabled;
	}

	public void setMemberEnabled(Integer memberEnabled) {
		this.memberEnabled = memberEnabled;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberBloodType() {
		return memberBloodType;
	}

	public void setMemberBloodType(String memberBloodType) {
		this.memberBloodType = memberBloodType;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}

	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}

	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	
	
	
	
}