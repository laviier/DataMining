package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FacebookFeedBean;
import model.FacebookFeeds;
import model.FacebookImage;
import model.FacebookImages;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;

public class GetFBFeeds extends TimerTask {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	
	@Override
    public void run() {
        System.out.println("facebook feeds");
        String apiKey = "1447625108855686";
	    String apiSecret = "a8ce3774165d43d3bbf2b543afef0e65";
	    String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me/home";
	    String scope = "user_about_me,user_friends,read_stream";
	    
	    OAuthService service = new ServiceBuilder()
	                                  .provider(FacebookApi.class)
	                                  .apiKey(apiKey)
	                                  .apiSecret(apiSecret)
	                                  .scope(scope)
	                                  .build();

         Token accessToken = GetFBToken.getAccessToken();
		    
		 OAuthRequest oauthrequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		 service.signRequest(accessToken, oauthrequest);
		 Response oauthresponse = oauthrequest.send();

		 String connection=oauthresponse.getBody();
		 
		 connect();			 
		 FacebookFeeds feeds = new Gson().fromJson(connection, FacebookFeeds.class);
		 
		 List<FacebookFeedBean> allFeeds = feeds.getData();
		 for (int i = 0; i < allFeeds.size(); i++) {
			 FacebookFeedBean ithFeed = allFeeds.get(i);
			 boolean hasIt = false; 
			 ResultSet rs = null;
			 try {
				 rs = st.executeQuery("Select * from feeds where feeds_id = '" 
						 + ithFeed.getId() + "'");
				 while (rs.next()) {
					 hasIt = true;
				 }
			 } catch (SQLException e) {
				 e.printStackTrace();
			 }
			 //the feed has already been stored in the database
			 if (hasIt) {
				 continue;
			 }
			 
			 //To get right size of image;
			 String pic = "null";
			 int bigImage = 1;
			 if (ithFeed.getObject_id() != null) {
				 pic = getRightSize(ithFeed.getObject_id());
			 }
			 if (pic.equals("null")) {
				 pic = getInsertableString(ithFeed.getPicture());
				 bigImage = 0;
			 }
			 
			 String feedId = getInsertableString(ithFeed.getId());
			 String fromId = getInsertableString(ithFeed.getFrom().getId());
			 String fromName = getInsertableString(ithFeed.getFrom().getName());
			 String story = getInsertableString(ithFeed.getStory());
			 String message = getInsertableString(ithFeed.getMessage());
			 String timeOld = getInsertableString(ithFeed.getCreated_time());
			

			 DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
			 String jtdate = timeOld;
			 Date timeConvert = parser.parseDateTime(jtdate).toDate();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time = sdf.format(timeConvert);
			 
			 String sqlInsert = "insert into feeds values (";
			 if (ithFeed.getPlace() == null) {
				 sqlInsert += "'" + feedId + "','" + fromId + "','" 
						 + fromName + "','" + story + "','" + message
						 + "','" + "null" + "','" + pic + "','" + time + "','" + "facebook" + "','" + bigImage + "')";
			 } else {
				 sqlInsert += "'" + ithFeed.getId() + "','" + ithFeed.getFrom().getId() + "','" 
						 + ithFeed.getFrom().getName() + "','" + ithFeed.getStory() + "','" + ithFeed.getMessage()
						 + "','" + getInsertableString(ithFeed.getPlace().getName()) + "','" + pic + "','" 
						 + time + "','" + "facebook" + bigImage + "')";
			 }
			 try {
				 System.out.println(sqlInsert);
				 st.executeUpdate(sqlInsert);
			 } catch (SQLException e) {
				 e.printStackTrace();
			 }
		 }
    }
	
	private String getRightSize(String objectId) {
        String apiKey = "1447625108855686";
	    String apiSecret = "a8ce3774165d43d3bbf2b543afef0e65";
	    String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.1/" + objectId;
	    
	    OAuthService service = new ServiceBuilder()
	                                  .provider(FacebookApi.class)
	                                  .apiKey(apiKey)
	                                  .apiSecret(apiSecret)
	                                  .build();

         Token accessToken = GetFBToken.getAccessToken();
		    
         try {
			 OAuthRequest oauthrequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			 service.signRequest(accessToken, oauthrequest);
			 Response oauthresponse = oauthrequest.send();
	
			 String connection=oauthresponse.getBody();
			 System.out.println("images jason: " + connection);
			 FacebookImages images = new Gson().fromJson(connection, FacebookImages.class);
			 
			 List<FacebookImage> allImages = images.getImages();
			 FacebookImage curr = null;
			 for (int i = 0; i < allImages.size(); i++) {
				 curr = allImages.get(i); 
				 if (curr.getWidth() >= 300) {
					 System.out.println("source(>=300): " + curr.getSource());
					 return curr.getSource();
				 }
			 }
         } finally {
         }
         return "null";
	}
	
	/**
	 *  Single Quotation Mark may cause syntax problem
	 * @param str
	 * @return string that can be safely insert
	 */
	private String getInsertableString(String str) {
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
	
	private static void connect() {
		try {
	      // create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      Class.forName(myDriver);
	      conn = DriverManager.getConnection(url, "root", "112233");

	      // create the java statement
	      st = conn.createStatement();	      
	    } catch (Exception e) {
	      //System.err.println("Got an exception! ");
	      //System.err.println(e.getMessage());
	    }
	}
	
	
}
