package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi20;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class LoginServlet extends HttpServlet {
	   
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String apiKey = "77tdd86y1zq4xu";
		 String apiSecret = "gG39SPQXrbLl2TGD";
		 Token EMPTY_TOKEN = null;
		 String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~/connections?modified=new";
		 
		 OAuthService service = new ServiceBuilder()
		 		.provider(LinkedInApi20.class)
		        .apiKey(apiKey)
		        .apiSecret(apiSecret)
		        .callback("http://localhost:8080/DataMining/LoginServlet")
		        .scope("r_fullprofile rw_nus r_network")
		        .build();
		 //+ "r_emailaddress r_contactinfo rw_company_admin w_messages"
		    
		 String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		 
		 System.out.println(request.getRequestURI());
		 System.out.println(request.getContextPath());
		 
		 if (request.getSession().getAttribute("owncall").equals("yes")) {
			 System.out.println("Redirect to the authorizaiotn url");
			 request.getSession().setAttribute("owncall", "no");
			 response.sendRedirect(authorizationUrl);
			 return;
		 } else {
			 System.out.println("Auth successful - got callback");
			 
			 String code = request.getParameter("code");
			 
			 Verifier verifier = new Verifier(code);
			 System.out.println("Trading the Request Token for an Access Token...");
			 Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			 System.out.println("Got the Access Token!");
			 
			 System.out.println("Now we're going to access a protected resource...");
			 OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			 service.signRequest(accessToken, requestOauth);
			 Response responseOauth = requestOauth.send();

			 
			 System.out.println("Got it! Lets see what we found...");
			 System.out.println();
			 
			 System.out.println(responseOauth.getCode());
			 System.out.println(responseOauth.getBody());
			 
			 response.setContentType("text/xml");
			 PrintWriter out=response.getWriter();
			 String connection=responseOauth.getBody();
			 out.println(connection);
		 }
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);   
	 }

}
