package com.activity.dao;

import java.util.List;

import com.activity.entity.Registration;

public interface RegistrationDAO {

	public List<Registration> getUserRegistration(String UserLineId);

	
}
