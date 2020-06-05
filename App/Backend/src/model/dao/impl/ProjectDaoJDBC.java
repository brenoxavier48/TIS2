package model.dao.impl;

import java.util.List;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.dao.ProjectDao;
import model.entities.Project;

public class ProjectDaoJDBC implements ProjectDao {

	Connection conn;
	
	public ProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Project project) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement(
					"INSERT INTO project "
					+"(Nome, Descricao) "
					+"VALUES "
					+"(?, ?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, project.getNome());
			st.setString(2, project.getDescricao());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()){
					int id = rs.getInt(1);
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

	}

	@Override
	public void update(Project project) {
		PreparedStatement st = null;
		
		try{
			st = conn.prepareStatement(
					"UPDATE project "
					+"SET Nome = ?, Descricao = ?, Status = ?, UserId = ? "
					+"WHERE Id = ?"
					);
			
			st.setString(1, project.getNome());
			st.setString(2, project.getDescricao());
			st.setInt(5, project.getId());
			
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
	public Project findById(Integer id) {
		PreparedStatement st = null;
		
		try{
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Project> findByUser(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

}
