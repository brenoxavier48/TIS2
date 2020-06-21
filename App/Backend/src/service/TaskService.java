package service;

import java.util.List;

import com.google.gson.Gson;

import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.entities.Project;
import model.entities.Task;
import model.entities.User;
import spark.Request;
import spark.Response;

public class TaskService {
	
	TaskDao taskDao;
	
	public List<Task> findProjectTasks(Request req, Response resp){
		
		Gson gson = new Gson();
		taskDao = DaoFactory.createTaskDao();
		
		Project project = gson.fromJson(req.body(), Project.class);
		System.out.println(project);
		List<Task> listReturn = taskDao.findByProject(project);
		
		return listReturn;	
	}
	
	public void insertTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		Gson gson = new Gson();
		User user = new User();
		Project project = new Project();
		
		if(req.queryParams("userid") != null){
			user.setId(Integer.parseInt(req.queryParams("userid")));
		}else{
			user.setId(0);
		}
		
		project.setId(Integer.parseInt(req.queryParams("projectid")));
		
		Task task = gson.fromJson(req.body(), Task.class);
		
		task.setUser(user);
		task.setProject(project);
		
		int id = taskDao.insert(task);
		
		if(id > 0){
			resp.body("{'id': '"+id+"'}");
			resp.status(201);
		}
		else{
			resp.status(500);
		}
	}
	
	public void deleteTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		
		taskDao.deleteById(Integer.parseInt(req.params(":id")));
		
		resp.status(200);
	}
	
	public void updateTask(Request req, Response resp){
		
		taskDao = DaoFactory.createTaskDao();
		Gson gson = new Gson();
		User user = new User();
		if(req.queryParams("userid") != null){
			user.setId(Integer.parseInt(req.queryParams("userid")));
		}else{
			user.setId(0);
		}
		
		Task task = gson.fromJson(req.body(), Task.class);
		
		task.setId(Integer.parseInt(req.params(":id")));
		task.setUser(user);
		
		taskDao.update(task);
		resp.status(200);
		
	}
	
}
