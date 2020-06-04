package com.activity.dao;

import java.util.List;

import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;

public interface RegistrationDAO {
	//利用lineid尋找已報名活動
	public List<Registration> getUserRegistration(String UserLineId);
	
	public Registration get(Registration registration);
	
	//獲取單一使用者所報名的所有活動
	public List<Registration> getMemberList(Registration registration);
	//獲取所有報名清單
	public List<Registration> getList();
	//獲取此活動的報名清單
	public List<Registration> getActivityList(int id);
	
	public void insert(Registration registration);

	public void update(Registration oldRegistration, Registration registration);

	public void delete(int id);
	//更新取消時間
	public void updateCancelTime(Registration registration);
	//取得此使用者近一個月取消報名次數
	public Integer getUserCancelTimeInMonth(Member member);
	
	public Integer getNoCancelAndNoAttend(Member member);
	
	public Registration getOneRegistration(Registration registration);
	
	public List<Registration> getListWithMemberInformation(int id);
	
	public Integer checkAttendPeople(Registration registration);
	//手動簽到、簽退
	public void signInByMemberEmail(Registration registration);
	
	public void signOutByMemberEmail(Registration registration);
	//檢查此使用者是否在同天內已有其他報名的活動
	public List<Activity> getUserIsSignUpSameDay(Activity activity,String memberEmail);
	//QRCODE簽到、簽退
	public void signInByAINum(Registration registration);
	
	public void signOutByAINum(Registration registration);
	//
}
