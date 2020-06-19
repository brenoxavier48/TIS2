package model.dao;

import java.util.List;

import model.entities.Project;
import model.entities.User;

public interface ProjectDao {
	
	int insert(Project project);
	void update(Project project);
	void deleteById(Integer id);
	List<Project> findByUser(User user);
}
