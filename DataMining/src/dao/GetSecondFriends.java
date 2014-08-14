package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

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
import control.SecondUserXML;

public class GetSecondFriends extends TimerTask  {

    @Override
    public void run() {
        System.out.println("test second");
        String apiKey = "77tdd86y1zq4xu";
		String apiSecret = "gG39SPQXrbLl2TGD";
		
		OAuthService service = new ServiceBuilder()
		 		.provider(LinkedInApi20.class)
		        .apiKey(apiKey)
		        .apiSecret(apiSecret)
		        .scope("r_fullprofile rw_nus r_network")
		        .build();
		 //+ "r_emailaddress r_contactinfo rw_company_admin w_messages"
		 
		Token accessToken = UpdateJobSql.getAccessToken();
		 
		ArrayList<String> a = Sql2.getAllId();

		 for ( int i=0; i<a.size(); i++) {
			 String PROTECTED_RESOURCE_URL2 = "https://api.linkedin.com/v1/people/"+a.get(i)+"/network/updates:(update-content:(person:(id,first-name,last-name,headline)))";
			 
			 OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL2);
			 //Token accessToken = (Token) request.getSession().getAttribute("access_token");
			 service.signRequest(accessToken, requestOauth);
			 Response responseOauth = requestOauth.send();
			 
			 SecondUserXML temp = new SecondUserXML();
			 
			 String connection=responseOauth.getBody();
			 
			 try {
				try {
					temp.parseXML(connection);
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (XPathExpressionException
					| ParserConfigurationException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

    }

}