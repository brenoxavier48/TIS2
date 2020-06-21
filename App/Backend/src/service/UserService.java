package service;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import spark.Request;
import spark.Response;

import org.omg.CORBA.AnyHolder;

import com.google.gson.Gson;


public class UserService {
	
	UserDao userDao;
	
	public User findUser(Request req, Response resp){
		
		Gson gson = new Gson();
		userDao = DaoFactory.createUserDao();
		
		User user = gson.fromJson(req.body(), User.class);
		System.out.println(user);
		User userReturn = userDao.find(user);
		
		if(userReturn != null){
			return userReturn;
		}
		
		return null;
	}
	
	public void insertUser(Request req, Response resp){
		Gson gson = new Gson();
		userDao = DaoFactory.createUserDao();
		
		User user = gson.fromJson(req.body(), User.class);
		System.out.println(user);
		System.out.println(req.body());
		
		int id = userDao.insert(user);
		
		if(id > 0){
			resp.body("{\"success\": true, \"id\": \""+id+"\"}");
			resp.status(201);
		}
		else{
			resp.body("{\"success\": false}");
			resp.status(200);
		}
	}
	
	

}
