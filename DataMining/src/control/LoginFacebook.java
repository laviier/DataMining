package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FacebookFeedBean;
import model.FacebookFeeds;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;


public class LoginFacebook extends HttpServlet {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String apiKey = "1447625108855686";
	    String apiSecret = "a8ce3774165d43d3bbf2b543afef0e65";
	    String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me/home";
	    Token EMPTY_TOKEN = null;
	    String callback_url = "http://localhost:8080/DataMining/LoginFacebook";
	    String scope = "user_about_me,user_friends,read_stream";
	    String redirectURL = "http://localhost:8080/DataMining/success.jsp";
	    
	    OAuthService service = new ServiceBuilder()
	                                  .provider(FacebookApi.class)
	                                  .apiKey(apiKey)
	                                  .apiSecret(apiSecret)
	                                  .callback(callback_url)
	                                  .scope(scope)
	                                  .build();
	   
	    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
	    
	    if (request.getSession().getAttribute("owncallFB").equals("yes")) {
			 request.getSession().setAttribute("owncallFB", "no");
			 response.sendRedirect(authorizationUrl);
			 return;
		 } else {
			 String code = request.getParameter("code");
			 Verifier verifier = new Verifier(code);
			 Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			    
			 OAuthRequest oauthrequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			 service.signRequest(accessToken, oauthrequest);
			 Response oauthresponse = oauthrequest.send();
			 
//			 OAuthRequest oauthrequest2 = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
//			 service.signRequest(accessToken, oauthrequest2);
//			 Response oauthresponse2 = oauthrequest2.send();
			 
			 response.setContentType("application/json");
			 //PrintWriter out=response.getWriter();
			 String connection=oauthresponse.getBody();
//			 String s2=oauthresponse2.getBody();
			 
			 connect();
			 //store the access token
			 String sqlToken = "insert into fb_token (token,raw_response) values ('" 
					 + accessToken.getToken() + "','" + accessToken.getRawResponse()
					 + "')";
			 //insert the token to the database;
			 System.out.println("token: " + accessToken.getToken());
			 System.out.println("secret: " + accessToken.getSecret());
			 System.out.println("raw response: " + accessToken.getRawResponse());
			 try {
				st.executeUpdate(sqlToken);
			 } catch (SQLException e1) {
				e1.printStackTrace();
			 }
			 
			 response.sendRedirect(redirectURL);
		 } 
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);   
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
