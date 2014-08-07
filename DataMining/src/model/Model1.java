package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Model1 {
	
	private String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static String contactListQuery = "select * from client, physical_address, email where client.address_id = physical_address.address_id and client.email_id = email.email_id;";
	private Connection conn;
	private Statement st;
	
	
	public void insert_linkedin_user(){
			//String id, String firstName, String lastName, String url,String picUrl) {
		connect();
		try {
			ResultSet rs = st.executeQuery(contactListQuery);

			// iterate through the java resultset
			while (rs.next()) {
				String client = rs.getString("client_status");
				System.out.print(client);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	private void connect() {
		 try
		    {
		      // create our mysql database connection
		      String myDriver = "org.gjt.mm.mysql.Driver";
		      Class.forName(myDriver);
		      conn = DriverManager.getConnection(url, "root", "112233");
		       
		      // our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      
		 
		      // create the java statement
		      st = conn.createStatement();
		       
		      // execute the query, and get a java resultset
		      
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
	}
}
