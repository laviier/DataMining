package dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GetLeads {
	
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static String contactListQuery = 
			"SELECT linkedin_user.linkedin_id, linkedin_user.first_name, linkedin_user.last_name, "+
					"linkedin_user.url, linkedin_user.profile_url, title.title_name, company.company_name "+
					"FROM (linkedin_user "+
					"JOIN positions ON positions.linkedin_id = linkedin_user.linkedin_id "+
					"JOIN title ON title.title_id = positions.title_id "+
					"JOIN company ON company.company_id = positions.company_id) "+
					"where linkedin_user.url != '' AND linkedin_user.is_lead=1 "+
					"AND positions.is_current=1 "+
					"ORDER BY title.ave_salary DESC;";
	private static Connection conn;
	private static Statement st;
	
//	public static void main(String[] args) {
//		List<LeadBean> l = getContactList();
//		for (LeadBean a: l) {
//			System.out.println(a.toString());
//		}
//		System.out.println(l.size());
//	}
	
	
	public static List<LeadBean> getContactList() {
		connect();
		List<LeadBean> leads = new LinkedList<LeadBean>();
		try {
			ResultSet rs = st.executeQuery(contactListQuery);

			// iterate through the java resultset
			while (rs.next()) {
				LeadBean lead = new LeadBean();
				lead.setId(rs.getString("linkedin_user.linkedin_id"));
				lead.setFirstName(rs.getString("linkedin_user.first_name"));
				lead.setLastName(rs.getString("linkedin_user.last_name"));
				lead.setURL(rs.getString("linkedin_user.url"));
				lead.setProfileURL(rs.getString("linkedin_user.profile_url"));
				lead.setTitle(rs.getString("title.title_name"));
				lead.setCompany(rs.getString("company.company_name"));
				
				leads.add(lead);

			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		return leads;
	}
	
	private static void connect() {
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
