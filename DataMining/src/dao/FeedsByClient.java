package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.FacebookFeedBean;
import model.FacebookFeeds;
import model.FacebookUser;
import model.FeedPlace;

@WebServlet("/FbFeeds")
public class FeedsByClient extends HttpServlet {
	private String clientName;
	private FacebookFeeds allFeeds = new FacebookFeeds();
	
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	
	/*public static void main(String[] args) {
		FeedsByClient trial = new FeedsByClient("Arsenal");
		try {
			System.out.println(trial.getFeeds());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		clientName = name;
		String json = null;
		try {
			allFeeds = new FacebookFeeds();
			json = getFeeds();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter(); 
		System.out.println(json);
		out.print(json);
		out.flush();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public String getFeeds() throws SQLException {
		 connect();
		 ResultSet rs = st.executeQuery("select * from facebook_feeds where user_name = '" + clientName + "'");
		 while (rs.next()) {
			 FacebookFeedBean feed = new FacebookFeedBean();
			 FacebookUser from = new FacebookUser();
			 FeedPlace place = new FeedPlace();
			 
			 feed.setId(rs.getString(1));
			 
			 from.setId(rs.getString(2));
			 from.setName(rs.getString(3));
			 feed.setFrom(from);
			 
			 feed.setStory(rs.getString(4));
			 feed.setMessage(rs.getString(5));
			 
			 place.setName(rs.getString(6));
			 feed.setPlace(place);
			 
			 feed.setPicture(rs.getString(7));
			 
			 feed.setUpdated_time(rs.getString(8));
			 
			 allFeeds.addFeed(feed);
		 }
		 rs.close();
		 
		 Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		 String json = gson.toJson(allFeeds);
		 return json;
	}
	
	private static void connect() {
		try {
	      // create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      Class.forName(myDriver);
	      conn = DriverManager.getConnection(url, "root", "112233");

	      // create the java statement
	      st = conn.createStatement();	      
	    } catch (Exception e) {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	}
}