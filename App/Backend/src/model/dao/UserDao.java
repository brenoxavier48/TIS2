package model.dao;

import java.util.List;

import model.entities.User;



public interface UserDao {
	
	int insert(User obj);
	User find(User obj);
}
