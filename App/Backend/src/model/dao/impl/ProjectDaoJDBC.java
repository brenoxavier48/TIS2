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
import model.dao.ProjectDao;
import model.entities.Project;
import model.entities.Task;
import model.entities.User;

public class ProjectDaoJDBC implements ProjectDao {

	Connection conn;
	
	public ProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int insert(Project project) {
		PreparedStatement st = null;
		int id = 0;
		try{
			st = conn.prepareStatement(
					"INSERT INTO project "
					+"(Nome, Descricao, UserId) "
					+"VALUES "
					+"(?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, project.getNome());
			st.setString(2, project.getDescricao());
			st.setInt(3, project.getUser().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()){
					id = rs.getInt(1);
					project.setId(id);
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
		return id;
	}

	@Override
	public void update(Project project) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement(
					"UPDATE project "
					+"SET Nome = ?, Descricao = ?"
					+"WHERE Id = ?"
					);
			
			st.setString(1, project.getNome());
			st.setString(2, project.getDescricao());
			st.setInt(3, project.getId());
			
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
			st = conn.prepareStatement("DELETE FROM project WHERE Id = ?");
		
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
	public List<Project> findByUser(User user) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			
			st = conn.prepareStatement(
					"SELECT project.*,user.Nome as UserNome "
					+"FROM project INNER JOIN user "
					+"ON project.UserId = user.Id "
					+"WHERE user.Id = ? "
					+"ORDER BY Nome "
					);
			
			st.setInt(1, user.getId());
			
			rs = st.executeQuery();
			
			List<Project> list = new ArrayList<>();
			Map<Integer, User> map = new HashMap<>();
			
			while(rs.next()){
				
				User userReturn = map.get(rs.getInt("UserId"));
				if(userReturn == null){
					userReturn = instanciateUser(rs);
					map.put(rs.getInt("UserId"), userReturn);
				}
				
				Project objProject = instanciateProject(rs, userReturn);
				list.add(objProject);
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
	
	private Project instanciateProject(ResultSet rs, User user) throws SQLException {

		Project obj = new Project();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setDescricao(rs.getString("Descricao"));
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
