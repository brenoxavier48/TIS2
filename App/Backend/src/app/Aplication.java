package app;

import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.dao.UserDao;
import model.entities.Task;
import model.entities.User;

public class Aplication {

	public static void main(String[] args) {
		
		TaskDao taskDao = DaoFactory.createTaskDao();
		UserDao userDao = DaoFactory.createUserDao();
		
		User user1 = new User(2, "Gabigooolll", "8787");
		
		//Task task1 = new Task(11, "Trabalhar", "com Dev", "Andamento", user1);
		
		//taskDao.findByUser(user1);
		userDao.insert(user1);
		
		
		//System.out.println(userDao.find(user1));

	}

}
