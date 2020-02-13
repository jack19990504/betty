package com.activity.entity;

import java.sql.Date;

public class Member {

	private String memberEmail;
	
	private String memberPassword;
	
	private String memberName;
	
	private String memberGender;
	
	private Date memberBirthday;
	
	private String memberTel;
	
	private String memberPhone;
	
	private String memberAddress;
	
	private String memberEncodedPassword;
	
	private Integer memberEnabled;
	
	private String memberLineId;
	
	private Integer memberType;

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

	public Date getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(Date memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
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

	
	
	
	
}