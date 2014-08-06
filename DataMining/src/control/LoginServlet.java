package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import model.LinkedInUser;

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
		 String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~/connections:(id,first-name,last-name,site-standard-profile-request,location,positions)";
		 
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
			 
			 
			 String code = request.getParameter("code");
			 
			 Verifier verifier = new Verifier(code);
			 Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			 
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
			 
			 //Yulei test
			 System.out.println("Start to test Jsoup");
			 LinkedInToNumericalData trial = new LinkedInToNumericalData(users);
			 trial.test();
			 
			 out.println(connection);
		 }
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);   
	 }

}
