package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.TaskDao;
import model.entities.Task;
import model.entities.User;

public class TaskDaoJDBC implements TaskDao{
	
	Connection conn;
	
	public TaskDaoJDBC(Connection conn){
		this.conn = conn;
	}

	@Override
	public void insert(Task obj) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement(
					"INSERT INTO task "
					+"(Nome, Descricao, Status, UserId) "
					+"VALUES "
					+"(?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			st.setString(3, obj.getStatus());
			st.setInt(4, obj.getUser().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()){
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else{
				throw new DbException("Unexpected error! No rows affected!");
				
			}
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Task obj) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement(
					"UPDATE task "
					+"SET Nome = ?, Descricao = ?, Status = ?, UserId = ? "
					+"WHERE Id = ?"
					);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			st.setString(3, obj.getStatus());
			st.setInt(4, obj.getUser().getId());
			st.setInt(5, obj.getId());
			
			st.executeUpdate();
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}	
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement("DELETE FROM task WHERE Id = ?");
		
			st.setInt(1, id);
			
			st.executeUpdate();
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}		
	}

	@Override
	public Task findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findByUser(User user) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			
			st = conn.prepareStatement(
					"SELECT task.*,user.Nome as UserNome "
					+"FROM task INNER JOIN user "
					+"ON task.UserId = user.Id "
					+"WHERE user.Id = ? "
					+"ORDER BY Nome "
					);
			
			st.setInt(1, user.getId());
			
			rs = st.executeQuery();
			
			List<Task> list = new ArrayList<>();
			Map<Integer, User> map = new HashMap<>();
			
			while(rs.next()){
				
				User userReturn = map.get(rs.getInt("UserId"));
				
				if(userReturn == null){
					userReturn = instanciateUser(rs);
					map.put(rs.getInt("UserId"), userReturn);
				}
				
				Task objtask = instanciateTask(rs, userReturn);
				list.add(objtask);
			}
			
			return list;
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Task instanciateTask(ResultSet rs, User user) throws SQLException {

		Task obj = new Task();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setDescricao(rs.getString("Descricao"));
		obj.setStatus(rs.getString("Status"));
		obj.setUser(user);
		
		return obj;
	}
	
	private User instanciateUser(ResultSet rs) throws SQLException {
		
		User user = new User();
		user.setId(rs.getInt("UserId"));
		user.setNome(rs.getString("UserNome"));
		
		return user;
	}

}
