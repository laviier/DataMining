package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

import model.FacebookFeedBean;
import model.FacebookFeeds;
import model.FacebookUser;
import model.FeedPlace;

public class FeedsByClient {
	private String json = null;
	private String clientName;
	private FacebookFeeds allFeeds;
	
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	
	public static void main(String[] args) {
		FeedsByClient trial = new FeedsByClient("Arsenal");
		try {
			System.out.println(trial.getFeeds());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FeedsByClient(String name) {
		clientName = name;
		allFeeds = new FacebookFeeds();
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
			 
			 allFeeds.addFeed(feed);
		 }
		 rs.close();
		 
		 Gson gson = new Gson();
		 json = gson.toJson(allFeeds);
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
