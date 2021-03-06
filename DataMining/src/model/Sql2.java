package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Sql2 {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	//private static String contactListQuery = "select * from client, physical_address, email where client.address_id = physical_address.address_id and client.email_id = email.email_id;";
	private static Connection conn,conn1,conn2,conn4,conn5,conn6;
	private static Statement st,st1,st2,st4,st5,st6;
	
	public static ArrayList<String> getAllId(){
			//String id, String firstName, String lastName, String url,String picUrl) {
		connect();
		ArrayList<String> a = new ArrayList<String>();
		String selectId = "select linkedin_id from linkedin_user where fa_id=1;";
		try {
			ResultSet rs = st.executeQuery(selectId);
			while (rs.next()) { 
				a.add(rs.getString("linkedin_id"));
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
		return a;
	}
	
	public static void insertToken(String token, String raw, String date) {
		connect(); 
		String sqlToken = "insert into linkedin_token (token,raw_response,expire_date,fa_id) values ('" 
				 + token + "','" + raw + "','" + date + "',1)";
		try {
			st.execute(sqlToken);
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void update(String id, int isLead) {
		connect();
		String update = "update linkedin_user set is_lead="+isLead+" where linkedin_id ='"+id+"';";
		try {
			st.execute(update);
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	public static void insert(String id, String firstName, String lastName, String title, 
			String company, String url, String picUr) throws SQLException{
		//String id, String firstName, String lastName, String url,String picUrl) {
		connect();
		title = getInsertableString(title);
		company = getInsertableString(company);
		String selectUser = "select linkedin_id from linkedin_user where linkedin_id='"+id+"';";
		ResultSet hasid = st.executeQuery(selectUser);
		
		if(!hasid.next()){
			String insertUser = "INSERT INTO linkedin_user (linkedin_id,first_name,last_name,url,profile_url,is_client,is_lead,fa_id,is_first_level) VALUE " +  
					"('"+id+"','"+firstName+"','"+lastName+"','"+url+"','"+picUr+"',0,0,1,0)";
			String selectTitle = "select title_id from title where title_name='"+title+"';";
			String selectCompany = "select company_id from company where company_name='"+company+"';";
			String insertTitle = "insert into title (title_name) value ('"+title+"');";
			String insertCompany = "insert into company (company_name) value ('"+company+"');";
			
			String update1 = "update linkedin_user set url='"+url+"' where linkedin_id='"+id+"';";
			String update2 = "update linkedin_user set profile_url='"+picUr+"' where linkedin_id='"+id+"';";
			
			
			try {
				int t,c;
				
				ResultSet tid = st1.executeQuery(selectTitle);
				if (tid.next()) { 
					t = tid.getInt("title_id");
				} else {
					st4.execute(insertTitle);
					ResultSet tid2 = st5.executeQuery(selectTitle);
					t = tid2.getInt("title_id");
				}

				ResultSet cid = st2.executeQuery(selectCompany);
				if (cid.next()) { 
					c = cid.getInt("company_id");
				} else {
					st4.execute(insertCompany);
					ResultSet cid2 = st6.executeQuery(selectCompany);
					c = cid2.getInt("company_id");
				}
				
				String insertPosition = "INSERT INTO positions (linkedin_id,title_id,company_id,is_current) VALUE " +  
						"('"+id+"','"+t+"','"+c+"',1)";
				
				try {
					st4.execute(insertUser);
					st4.execute(insertPosition);
					st4.execute(update1);
					st4.execute(update2);
				} catch (Exception e) {
					st4.execute(update1);
					st4.execute(update2);
				}
			} catch (Exception e) {
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
			}
		}
}
	
	private static void connect() {
		 try
		    {
		      // create our mysql database connection
			 if (conn == null) {
			
		      String myDriver = "org.gjt.mm.mysql.Driver";
		      Class.forName(myDriver);
		      conn = DriverManager.getConnection(url, "root", "112233");
		      
		      st = conn.createStatement();
			 }
			 
			 if (conn1 == null) {
				 
					
			      String myDriver = "org.gjt.mm.mysql.Driver";
			      Class.forName(myDriver);
			      conn1 = DriverManager.getConnection(url, "root", "112233");
			      
			      st1 = conn1.createStatement();
				 }
				 if (conn2 == null) {
					 
						
				      String myDriver = "org.gjt.mm.mysql.Driver";
				      Class.forName(myDriver);
				      conn2 = DriverManager.getConnection(url, "root", "112233");
				      
				      st2 = conn2.createStatement();
					 }
				 
				 if (conn4 == null) {
					 
						
				      String myDriver = "org.gjt.mm.mysql.Driver";
				      Class.forName(myDriver);
				      conn4 = DriverManager.getConnection(url, "root", "112233");
				      
				      st4 = conn4.createStatement();
					 }
				 if (conn5 == null) {
					 
						
				      String myDriver = "org.gjt.mm.mysql.Driver";
				      Class.forName(myDriver);
				      conn5 = DriverManager.getConnection(url, "root", "112233");
				      
				      st5 = conn5.createStatement();
					 }
				 if (conn6 == null) {
					 
						
				      String myDriver = "org.gjt.mm.mysql.Driver";
				      Class.forName(myDriver);
				      conn6 = DriverManager.getConnection(url, "root", "112233");
				      
				      st6 = conn6.createStatement();
					 }
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
	}
	
	public static String getInsertableString(String str) {
		if (str == null) {
			return "null";
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\'') {
				sb.append("\\'");
				continue;
			}
			sb.append(c);
		}
		
		return sb.toString();
	}
}
