package service;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import spark.Request;
import spark.Response;


public class UserService {
	
	UserDao userDao;
	
	public User findUser(Request req, Response resp){
		
		userDao = DaoFactory.createUserDao();
		
			
		User user = new User();
		user.setNome(req.queryParams("nome"));
		user.setSenha(req.queryParams("senha"));
		
		User userReturn = userDao.find(user);
		
		if(userReturn != null){
			return userReturn;
		}
		
		return null;
	}
	
	public void insertUser(Request req, Response resp){
		
		userDao = DaoFactory.createUserDao();
		
		
		User user = new User();
		user.setNome(req.queryParams("nome"));
		user.setSenha(req.queryParams("senha"));
		
		userDao.insert(user);
		
		resp.status(201);
	}
	
	

}
