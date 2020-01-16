package com.activity.dao;

import java.util.List;

import com.activity.entity.Registration;

public interface RegistrationDAO {

	public List<Registration> getUserRegistration(String UserLineId);
	
	public Registration get(Registration registration);
	
	public List<Registration> getList();
	
	public void insert(Registration registration);

	public void update(Registration oldRegistration, Registration registration);

	public void delete(Registration registration);
	
}
