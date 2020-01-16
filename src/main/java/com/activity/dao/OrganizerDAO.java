package com.activity.dao;

import java.util.List;

import com.activity.entity.Organizer;

public interface OrganizerDAO {

	public void insert(Organizer organizer);

	public void update(Organizer oldOrganizer, Organizer organizer);

	public void delete(Organizer organizer);

	public List<Organizer> getList();

	public Organizer get(Organizer organizer);
	
}
