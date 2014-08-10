package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class Sql {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	//private static String contactListQuery = "select * from client, physical_address, email where client.address_id = physical_address.address_id and client.email_id = email.email_id;";
	private static Connection conn1,conn2,conn3,conn4,conn5,conn6,conn7,conn8,conn9;
	private static Statement st1,st2,st3,st4,st5,st6,st7,st8,st9;
	
	public static void insert_linkedin_user(String id, String firstName, String lastName, String url,
			String picUr, String title, String company, String location,int year,int month){
			//String id, String firstName, String lastName, String url,String picUrl) {
		connect();
		String insertUser = "INSERT INTO linkedin_user (linkedin_id,first_name,last_name,url,profile_url,is_client,is_lead,fa_id,is_first_level) VALUE " +  
		"('"+id+"','"+firstName+"','"+lastName+"','"+url+"','"+picUr+"',0,0,1,1)";
		String selectTitle = "select title_id from title where title_name='"+title+"';";
		String selectLocation = "select location_id from location where location_name='"+location+"';";
		String selectCompany = "select company_id from company where company_name='"+company+"';";
		String insertTitle = "insert into title (title_name) value ('"+title+"');";
		String insertLocation = "insert into location (location_name) value ('"+location+"');";
		String insertCompany = "insert into company (company_name) value ('"+company+"');";
		try {
			int t,c,l;
			
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
			

			ResultSet lid = st3.executeQuery(selectLocation);
			if (lid.next()) { 
				l = lid.getInt("location_id");	
			} else {
				st4.execute(insertLocation);
				ResultSet lid2 = st7.executeQuery(selectLocation);
				l = lid2.getInt("location_id");
			}
			
			try {
				st4.execute(insertUser);
			} catch (Exception e) {
				//System.err.println("user exist! ");
			}
			
			String selectPostion = "select position_id from positions where linkedin_id='"+id+"' and start_year="+year+" and start_month="+month+";";
			String selectPostion2 = "select position_id from positions where linkedin_id='"+id+"' and is_current=1;";
			
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(new Date());
		    int currentYear = cal.get(Calendar.YEAR);
		    int currentMonth = cal.get(Calendar.MONTH);
			
			String updatePosotion = "update positions set is_current=0, end_year="+currentYear
					+", end_month="+currentMonth+" where linkedin_id='"+id+"' and is_current=1;";
			String insertPosition = "INSERT INTO positions (linkedin_id,title_id,company_id,location_id,start_year,start_month,is_current) VALUE " +  
					"('"+id+"','"+t+"','"+c+"','"+l+"','"+year+"','"+month+"',1)";
			
			ResultSet p = st8.executeQuery(selectPostion);
			if (p.next()) { 
				//
			} else {
				ResultSet p2 = st9.executeQuery(selectPostion2);
				if (p2.next()) { 
					System.out.print(firstName+" "+lastName+" changed job!");
					try {
						st4.execute(updatePosotion);
						st4.execute(insertPosition);
					} catch (Exception e) {
						//System.err.println("position exist! ");
					}
				} else {
					try {
						st4.execute(insertPosition);
					} catch (Exception e) {
						//System.err.println("position exist! ");
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	private static void connect() {
		 try
		    {
		      // create our mysql database connection
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
			 if (conn3 == null) {
				 
					
			      String myDriver = "org.gjt.mm.mysql.Driver";
			      Class.forName(myDriver);
			      conn3 = DriverManager.getConnection(url, "root", "112233");
			      
			      st3 = conn3.createStatement();
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
			 if (conn7 == null) {
				 
					
			      String myDriver = "org.gjt.mm.mysql.Driver";
			      Class.forName(myDriver);
			      conn7 = DriverManager.getConnection(url, "root", "112233");
			      
			      st7 = conn7.createStatement();
				 }
			 
			 if (conn8 == null) {
				 
					
			      String myDriver = "org.gjt.mm.mysql.Driver";
			      Class.forName(myDriver);
			      conn8 = DriverManager.getConnection(url, "root", "112233");
			      
			      st8 = conn8.createStatement();
				 }
			 
			 if (conn9 == null) {
				 
					
			      String myDriver = "org.gjt.mm.mysql.Driver";
			      Class.forName(myDriver);
			      conn9 = DriverManager.getConnection(url, "root", "112233");
			      
			      st9 = conn9.createStatement();
				 }
		       
		      
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
	}
}
