package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
private static Connection conn = null;
	
	public static Connection getConnection(){
		if(conn == null){
			try{
				Properties info = loadProperties();
				String url = info.getProperty("dburl");
				System.out.println(url);
				conn = DriverManager.getConnection(url, info);				
			}
			catch(SQLException e){
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection(){
		if(conn != null){
			try{
				conn.close();
			}
			catch(SQLException e){
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties(){
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e){
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st){
		try{
			if(st != null){
				st.close();
			}
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
	}
}
