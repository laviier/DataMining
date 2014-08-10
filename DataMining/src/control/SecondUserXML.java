package control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.SecondFriends;
import model.Sql2;

public class SecondUserXML {
	ArrayList<SecondFriends> users;
	
	public SecondUserXML() {
		this.users = new ArrayList<SecondFriends>();
	}
	
public void parseXML(String xmlSource) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, SQLException {
		
		//Get the DOM Builder Factory
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	    //Get the DOM Builder
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    ByteArrayInputStream is = new ByteArrayInputStream(xmlSource.getBytes("UTF-8"));

	    //Load and Parse the XML document
	    //document contains the complete XML as a Tree.
	    Document document = (Document) builder.parse(is);

	    //Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getElementsByTagName("update");

	    for (int i = 0; i < nodeList.getLength(); i++) {

	    	//We have encountered an <employee> tag.
	    	Node node = nodeList.item(i);
	    	if (node instanceof Element) {
	    		
	    	SecondFriends user = new SecondFriends();
	        
	        NodeList tempPositionsNodes = ((Element) node).getElementsByTagName("update-content");
	        Node tempPositionsNode = tempPositionsNodes.item(0);
	        if (tempPositionsNode instanceof Element) {
	        	try{
			        NodeList tempPositionNodes = ((Element) tempPositionsNode).getElementsByTagName("person");
			        Node tempPositionNode = tempPositionNodes.item(0);
			        if (tempPositionNode instanceof Element) {
			        	try {
			        		user.setId(((Element) tempPositionNode).getElementsByTagName("id").item(0).getTextContent());
			        		user.setFirstName(((Element) tempPositionNode).getElementsByTagName("first-name").item(0).getTextContent());
			        		user.setLastName(((Element) tempPositionNode).getElementsByTagName("last-name").item(0).getTextContent());
			        		user.setHeadline(((Element) tempPositionNode).getElementsByTagName("headline").item(0).getTextContent());
			        		user.setProfileURL(((Element) tempPositionNode).getElementsByTagName("picture-url").item(0).getTextContent());
			        	} catch (NullPointerException e){
		        			//e.printStackTrace();
			        	}
			        	
			        	NodeList tempCompanyNodes = ((Element) tempPositionNode).getElementsByTagName("site-standard-profile-request");
			        	Node tempCompanyNode = tempCompanyNodes.item(0);
			        	if (tempCompanyNode instanceof Element) {
			        		try {
			        			user.setURL(((Element) tempCompanyNode).getElementsByTagName("url").item(0).getTextContent());
			        		} catch (NullPointerException e){
			        			//e.printStackTrace();
			        		}
			        	}
			        }
	        	} catch (NullPointerException e){
	        		//e.printStackTrace();
	        	}
	        }
	        
	        String headline = user.getHeadline();
	        String[] a = null; 
	        if (headline.contains(" at ")) {
	        	a = user.getHeadline().split(" at ");
	        } else if (headline.contains(" At ")) {
	        	a = user.getHeadline().split(" At ");
	        } else if (headline.contains(" AT ")) {
	        	a = user.getHeadline().split(" AT ");
	        } else if (headline.contains("@")) {
	        	a = user.getHeadline().split("@");
	        } else {
	        	a = user.getHeadline().split("at");
	        }
	        
	        if (a.length == 2) {
	        	user.setTitle(a[0]);
	        	user.setCompany(a[1]);
	        } else {
	        	user.setTitle(a[0]);
	        } 
	        
	        users.add(user);
	        
	        System.out.println(user.toString());
	        
	        Sql2.insert(user.getId(), user.getFirstName(), user.getLastName(), user.getTitle(),user.getCompany(), user.getURL(), user.getProfileURL());

//	        Sql.insert_linkedin_user(user.getId(), user.getFirstName(), user.getLastName(), user.getURL(), user.getProfileURL(),
//	        		user.getTitle(),user.getCompany(),user.getLocation(),user.getStartYear(),user.getStartMonth());
	      }

	    }

	}
}
