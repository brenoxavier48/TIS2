package model.dao;

import java.util.List;

import model.entities.Project;
import model.entities.Task;

public interface TaskDao {

	int insert(Task obj);
	void update(Task obj);
	void deleteById(Integer id);
	Task findById(Integer id);
	List<Task> findByProject(Project project);
}
