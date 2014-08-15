package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import org.scribe.oauth.OAuthService;
import org.xml.sax.SAXException;

import control.SecondUserXML;

public class GetLinkedInFeeds extends TimerTask {
	@Override
    public void run() {
        //System.out.println("test linkedinfeed");
        //mine
//      String apiKey = "77tdd86y1zq4xu";
//		String apiSecret = "gG39SPQXrbLl2TGD";
		//fei
		
		//leilei
		String apiKey = "77kkfyfzz2ltwt";
		String apiSecret = "8UNpOx7NG2MYkPTM";
		
		OAuthService service = new ServiceBuilder()
		 		.provider(LinkedInApi20.class)
		        .apiKey(apiKey)
		        .apiSecret(apiSecret)
		        .scope("r_fullprofile rw_nus r_network")
		        .build();
		 //+ "r_emailaddress r_contactinfo rw_company_admin w_messages"
		 
		Token accessToken = UpdateJobSql.getAccessToken();
		 
			 String PROTECTED_RESOURCE_URL2 = 
					 "https://api.linkedin.com/v1/people/~/network/updates:(timestamp,update-key,update-content:(person:(id,first-name,last-name,current-share:(comment,content:(submitted-url)))))";
			 
			 OAuthRequest requestOauth = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL2);
			 //Token accessToken = (Token) request.getSession().getAttribute("access_token");
			 service.signRequest(accessToken, requestOauth);
			 Response responseOauth = requestOauth.send();
			 
			 LinkedInFeedXML temp = new LinkedInFeedXML();
			 
			 String connection=responseOauth.getBody();
			 //System.out.println(connection);
			 
			 try {
				// System.out.println("call parse xml");
				temp.parseXML(connection);
			} catch (XPathExpressionException
					| ParserConfigurationException | SAXException |SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }
	
}
