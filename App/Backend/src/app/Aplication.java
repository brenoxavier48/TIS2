package app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.google.gson.Gson;

import service.CorsFilter;
import service.ProjectService;
import service.TaskService;
import service.UserService;

public class Aplication {

	
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		UserService userService = new UserService();
		TaskService taskService = new TaskService();
		ProjectService projectService = new ProjectService();
		
		port(3030);
		
		CorsFilter.apply();		
		get("/user", (req, resp) -> {
			return  gson.toJson(userService.findUser(req, resp));
			});
		
		post("/user", (req, resp) -> {
			userService.insertUser(req, resp);
			return resp;
		});
		
		
		get("/task", (req, resp) -> {
			return gson.toJson(taskService.findUserTasks(req, resp));
		});
		
		post("/task", (req, resp)->{
			taskService.insertTask(req, resp);
			return resp;
		});
		
		get("/task/:id", (req, resp) -> {
			taskService.deleteTask(req, resp);
			return "ok";
		});
		
		post("/task/:id", (req, resp) -> {
			taskService.updateTask(req, resp);
			return "ok";
		});
		
		get("user/projects", (req, resp) -> {
			return gson.toJson(projectService.findUserProjects(req, resp));
		});
		
		post("/project", (req, resp) -> {
			projectService.insertProject(req, resp);
			return resp;
		});
		
		get("/project/:id", (req, resp) -> {
			projectService.deleteProject(req, resp);
			return resp;
		});
		
		post("/project/:id", (req, resp) -> {
			projectService.updateProject(req, resp);
			return resp;
		});
		
		//stop();
        
        //System.exit(0);
	} 
}
