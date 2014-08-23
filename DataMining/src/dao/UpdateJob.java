package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import model.JobUpdateBean;
import model.LinkedInUser;
import model.Sql2;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi20;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import control.LinkedInUserXML;

@WebServlet("/JobUpdates")
public class UpdateJob extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String apiKey = "77tdd86y1zq4xu";
		String apiSecret = "gG39SPQXrbLl2TGD";
		String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~/connections:(id,first-name,last-name,picture-url,site-standard-profile-request,location,positions)";
		 
		OAuthService service = new ServiceBuilder()
		 		.provider(LinkedInApi20.class)
		        .apiKey(apiKey)
		        .apiSecret(apiSecret)
		        .scope("r_fullprofile rw_nus r_network")
		        .build();
		 //+ "r_emailaddress r_contactinfo rw_company_admin w_messages"
		 
		Token accessToken = UpdateJobSql.getAccessToken();
		 
		OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service.signRequest(accessToken, requestOauth);
		Response responseOauth = requestOauth.send();
			 
		String connection=responseOauth.getBody();
		LinkedInUserXML temp = new LinkedInUserXML();
		List<JobUpdateBean> updates = new LinkedList<JobUpdateBean>();
			 
		try {
			updates = temp.parseXML(connection);
			System.out.println(connection);
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
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter(); 
		out.print(gson.toJson(updates));
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
