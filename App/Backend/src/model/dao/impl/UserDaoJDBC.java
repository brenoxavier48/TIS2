package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.UserDao;
import model.entities.User;

public class UserDaoJDBC implements UserDao{

	Connection conn;
	
	public UserDaoJDBC(Connection conn){
		this.conn = conn;
	}

	@Override
	public int insert(User obj) {
		PreparedStatement st = null;
		int id = 0;
		
		try{
			st = conn.prepareStatement(
					"INSERT INTO user "
					+"(Nome, Senha, Username) "
					+"VALUE (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getSenha());
			st.setString(3, obj.getUserName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()){
					id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else{
				throw new DbException("Unexpected error! No rows affected!");
			}
		}catch(SQLException e){
			throw new DbException(e.getMessage());
			
		}
		return id;
		
	}

	@Override
	public User find(User obj) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			st = conn.prepareStatement(
					"SELECT * FROM user "
					+"WHERE Username = ? "
					+"AND Senha = ? "
					);
			st.setString(1, obj.getUserName());
			st.setString(2, obj.getSenha());
			
			rs = st.executeQuery();
			
			User user = null;
			
			if(rs.next()){
				user = instanciateUser(rs);
			}
			
			return user;
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally{
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private User instanciateUser(ResultSet rs) throws SQLException {
		
		User user = new User();
		user.setId(rs.getInt("Id"));
		user.setNome(rs.getString("Nome"));
		user.setSenha(rs.getString("Senha"));
		user.setUserName(rs.getString("UserName"));
		
		return user;
	}
}
