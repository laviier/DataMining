package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public class LoginFacebook extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String apiKey = "1447625108855686";
	    String apiSecret = "a8ce3774165d43d3bbf2b543afef0e65";
	    String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me/?fields=friends";
	    Token EMPTY_TOKEN = null;
	    
	    OAuthService service = new ServiceBuilder()
	                                  .provider(FacebookApi.class)
	                                  .apiKey(apiKey)
	                                  .apiSecret(apiSecret)
	                                  .callback("http://localhost:8080/DataMining/LoginFacebook")
	                                  .scope("user_about_me,user_education_history,user_friends,user_work_history,user_birthday,user_location")
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
			 
			 OAuthRequest oauthrequest2 = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
			 service.signRequest(accessToken, oauthrequest2);
			 Response oauthresponse2 = oauthrequest2.send();
			 
			 response.setContentType("application/json");
			 PrintWriter out=response.getWriter();
			 String connection=oauthresponse.getBody();
			 String s2=oauthresponse2.getBody();
			 out.println("This is user's friends:");
			 out.println(connection);
			 out.println();
			 out.println("This is user's about_me:");
			 out.println(s2);
			 out.flush();
		 }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);   
	}

}
