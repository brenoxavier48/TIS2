package app;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;
import static spark.Spark.port;
import static spark.Spark.stop;

import com.google.gson.Gson;

import service.CorsFilter;
import service.TaskService;
import service.UserService;

public class Aplication {

	
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		UserService userService = new UserService();
		TaskService taskService = new TaskService();
		
		port(3030);
		
		CorsFilter.apply();		
		get("/user", (req, resp) -> {
			return  gson.toJson(userService.findUser(req, resp));
			});
		
		post("/user", (req, resp) -> {
			userService.insertUser(req, resp);
			return "ok";
		});
		
		
		get("/task", (req, resp) -> {
			return gson.toJson(taskService.findUserTasks(req, resp));
		});
		
		post("/task", (req, resp)->{
			return gson.toJson(taskService.insertTask(req, resp));
		});
		
		get("/task/:id", (req, resp) -> {
			taskService.deleteTask(req, resp);
			return "ok";
		});
		
		post("/task/:id", (req, resp) -> {
			taskService.updateTask(req, resp);
			return "ok";
		});
		
		//stop();
        
        //System.exit(0);
	} 
}
