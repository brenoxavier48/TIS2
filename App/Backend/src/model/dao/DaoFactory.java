package model.dao;

import db.DB;
import model.dao.impl.TaskDaoJDBC;
import model.dao.impl.UserDaoJDBC;

public class DaoFactory {
	
	public static TaskDao createTaskDao(){
		return new TaskDaoJDBC(DB.getConnection());
	}
	
	public static UserDao createUserDao(){
		return new UserDaoJDBC(DB.getConnection());
	}
}
