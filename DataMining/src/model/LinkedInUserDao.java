package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

public class LinkedInUserDao {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableClient;
	private String tableLinkedInUser;
	private String tablePositions;
	private String tableCompany;
	private String tableLocation;
	private String tableTitle;
	

	public LinkedInUserDao(String jdbcDriver2, String jdbcURL2, String t1,
			String t2, String t3, String t4, String t5, String t6) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableClient  = t1;
		this.tableLinkedInUser = t2;
		this.tablePositions = t3;
		this.tableCompany  = t4;
		this.tableLocation  = t5;
		this.tableTitle  = t6;	
	}
	
	public void insert_linkedin_user(String id, String firstName, String lastName, String url,
			String picUrl) throws MyDAOException {
		Connection con = null;
		try {
        	con = getConnection();
        	con.setAutoCommit(false);
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableLinkedInUser + 
        			" (linkedin_id,first_name,last_name,url,profile_url,is_client,is_lead) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        	pstmt.setString(0, id);
        	pstmt.setString(1, firstName);
        	pstmt.setString(2, lastName);
        	pstmt.setString(3, url);
        	pstmt.setString(4, picUrl);
        	pstmt.setInt(5, 0);
        	pstmt.setInt(6, 0);
        	int count = pstmt.executeUpdate();
        	ResultSet keys = pstmt.getGeneratedKeys();    
        	keys.next();  
            int key = keys.getInt(1);
        	keys.close();
        	System.out.println("count ai:"+key);
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	//SELECT LAST_INSERT_ID()
        	con.commit();
        	pstmt.close();
        	releaseConnection(con);
		}  catch (Exception e) {
			try { 
				if (con != null){ 
					con.rollback();con.close();
				} 
			} catch (SQLException e2) { /* ignore */ }
			throw new MyDAOException(e);
		}
	}

	private synchronized Connection getConnection() throws MyDAOException {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}
}
