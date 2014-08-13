package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import model.LinkedInUser;
import model.SecondFriends;
import model.Sql2;

import org.genericdao.DAOException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi20;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.xml.sax.SAXException;

public class LoginServlet extends HttpServlet {
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String apiKey = "77tdd86y1zq4xu";
		 String apiSecret = "gG39SPQXrbLl2TGD";
		 Token EMPTY_TOKEN = null;
		 String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~/connections:(id,first-name,last-name,picture-url,site-standard-profile-request,location,positions)";
		 
		 OAuthService service = new ServiceBuilder()
		 		.provider(LinkedInApi20.class)
		        .apiKey(apiKey)
		        .apiSecret(apiSecret)
		        .callback("http://localhost:8080/DataMining/LoginServlet")
		        .scope("r_fullprofile rw_nus r_network")
		        .build();
		 //+ "r_emailaddress r_contactinfo rw_company_admin w_messages"
		    
		 String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		 
		 
		 if (request.getSession().getAttribute("owncall").equals("yes")) {
			 
			 request.getSession().setAttribute("owncall", "no");
			 response.sendRedirect(authorizationUrl);
			 return;
		 } else {
			 
			 if(request.getSession().getAttribute("function").equals("1")) {
			 
			 String code = request.getParameter("code");
			 
			 Verifier verifier = new Verifier(code);
			 Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			 request.getSession().setAttribute("access_token", accessToken);
			 
			 OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			 service.signRequest(accessToken, requestOauth);
			 Response responseOauth = requestOauth.send();
			 
			 response.setContentType("text/xml");
			 PrintWriter out=response.getWriter();
			 String connection=responseOauth.getBody();
			 LinkedInUserXML temp = new LinkedInUserXML();
			 
			 try {
				temp.parseXML(connection);
				// temp.test();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 ArrayList<LinkedInUser> users = temp.users;
			 
			 out.println(connection);
			 }else {
				 ArrayList<String> a = Sql2.getAllId();

				 //String PROTECTED_RESOURCE_URL2 = "https://api.linkedin.com/v1/people/~/network/updates:(update-content:(person:(id,first-name,last-name,headline)))";
				 String code = request.getParameter("code");
				 
				 Verifier verifier = new Verifier(code);
				 Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
				 request.getSession().setAttribute("access_token", accessToken);
				 
				 for ( int i=0; i<a.size(); i++) {
					 String PROTECTED_RESOURCE_URL2 = "https://api.linkedin.com/v1/people/"+a.get(i)+"/network/updates:(update-content:(person:(id,first-name,last-name,headline)))";
					 
					 OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL2);
					 //Token accessToken = (Token) request.getSession().getAttribute("access_token");
					 service.signRequest(accessToken, requestOauth);
					 Response responseOauth = requestOauth.send();
					 
					 SecondUserXML temp = new SecondUserXML();
					 
					 response.setContentType("text/xml");
					 PrintWriter out=response.getWriter();
					 String connection=responseOauth.getBody();
					 
					 try {
						try {
							temp.parseXML(connection);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (XPathExpressionException
							| ParserConfigurationException | SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.println(connection);
				 }
			 }
		 }
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);   
	 }

}
