package service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.entities.Task;
import model.entities.User;
import spark.Request;
import spark.Response;

public class TaskService {
	
	TaskDao taskDao;
	
	public List<Task> findUserTasks(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		User user = new User();
		user.setId(Integer.parseInt(req.queryParams("id")));
		
		List<Task> listReturn = taskDao.findByUser(user);
		
		return listReturn;	
	}
	
	public void insertTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		User user = new User();
		user.setId(Integer.parseInt(req.queryParams("userid")));
		
		Task task = new Task();
		
		task.setNome(req.queryParams("nome"));
		task.setDescricao(req.queryParams("descricao"));
		task.setStatus(req.queryParams("status"));
		task.setUser(user);
		
		taskDao.insert(task);
		
		resp.status(201);
	}
	
	public void deleteTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		taskDao.deleteById(Integer.parseInt(req.params(":id")));
		
		resp.status(200);
	}
	
	public void updateTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		User user = new User();
		user.setId(Integer.parseInt(req.queryParams("userid")));
		
		Task task = new Task();
		
		task.setNome(req.queryParams("nome"));
		task.setDescricao(req.queryParams("descricao"));
		task.setStatus(req.queryParams("status"));
		task.setId(Integer.parseInt(req.params(":id")));
		task.setUser(user);
		
		taskDao.update(task);
		resp.status(200);
		
	}
	
}
