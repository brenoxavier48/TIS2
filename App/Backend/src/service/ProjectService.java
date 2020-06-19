package service;

import java.util.List;

import com.google.gson.Gson;

import model.dao.DaoFactory;
import model.dao.ProjectDao;
import model.entities.Project;
import model.entities.Task;
import model.entities.User;
import spark.Request;
import spark.Response;

public class ProjectService {
	ProjectDao projectDao;
	
	public List<Project> findUserProjects(Request req, Response resp){
		
		Gson gson = new Gson();
		
		projectDao = DaoFactory.createProjectDao();
		
		User user = gson.fromJson(req.body(), User.class);
		
		System.out.println(user);
		
		List<Project> listReturn = projectDao.findByUser(user);
		
		return listReturn;	
	}

	public void insertProject(Request req, Response resp){
		
		Gson gson = new Gson();
		
		projectDao = DaoFactory.createProjectDao();
		
		User user = new User();
		user.setId(Integer.parseInt(req.queryParams("userid")));
		
		Project project = gson.fromJson(req.body(), Project.class);
		
		project.setUser(user);
		
		int id = projectDao.insert(project);
		
		if(id > 0){
			resp.body("{'id': '"+id+"'}");
			resp.status(201);
		}
		else{
			resp.status(500);
		}
	}
	
	public void deleteProject(Request req, Response resp){
		
		projectDao = DaoFactory.createProjectDao();
		
		projectDao.deleteById(Integer.parseInt(req.params(":id")));
		
		resp.status(200);
	}
	
	public void updateProject(Request req, Response resp){
		
		projectDao = DaoFactory.createProjectDao();
		Gson gson = new Gson();
		
		Project project = gson.fromJson(req.body(), Project.class);
		
		project.setId(Integer.parseInt(req.params(":id")));
		
		projectDao.update(project);
		resp.status(200);
		
	}
}
