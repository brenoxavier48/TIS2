package model.dao;

import java.util.List;

import model.entities.User;
import model.entities.Task;

public interface TaskDao {

	void insert(Task obj);
	void update(Task obj);
	void deleteById(Integer id);
	Task findById(Integer id);
	List<Task> findByUser(User user);
}
