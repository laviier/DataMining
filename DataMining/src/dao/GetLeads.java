package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/LeadList")
public class GetLeads extends HttpServlet {
	
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static String contactListQuery = 
			"SELECT linkedin_user.linkedin_id, linkedin_user.first_name, linkedin_user.last_name, "+
					"linkedin_user.url, linkedin_user.profile_url, title.title_name, company.company_name, title.ave_salary "+
					"FROM (linkedin_user "+
					"JOIN positions ON positions.linkedin_id = linkedin_user.linkedin_id "+
					"JOIN title ON title.title_id = positions.title_id "+
					"JOIN company ON company.company_id = positions.company_id) "+
					"where linkedin_user.url != '' AND linkedin_user.is_lead=1 "+
					"AND positions.is_current=1 AND linkedin_user.is_client=0 "+
					"ORDER BY title.ave_salary DESC " +
					"LIMIT 20;";
	private static Connection conn;
	private static Statement st;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<LeadBean> lead = getContactList();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter(); 
		out.print(gson.toJson(lead));
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
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
				
				int salary = rs.getInt("title.ave_salary");
				
				if(salary>200000) lead.setScore(3);
				else if (salary<=200000 && salary>=100000) lead.setScore(2);
				else lead.setScore(1);
				
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
