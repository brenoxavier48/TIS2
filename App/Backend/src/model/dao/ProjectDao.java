package model.dao;

import java.util.List;

import model.entities.Project;

public interface ProjectDao {
	
	void insert(Project project);
	void update(Project project);
	void deleteById(Integer id);
	Project findById(Integer id);
	List<Project> findByUser(Project project);
}
