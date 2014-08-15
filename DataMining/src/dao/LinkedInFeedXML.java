package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

public class LinkedInFeedXML {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	//private static String contactListQuery = "select * from client, physical_address, email where client.address_id = physical_address.address_id and client.email_id = email.email_id;";
	private static Connection conn;
	private static Statement st;
	
	
	public LinkedInFeedXML() {
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

//	    <update>
//	    <timestamp>1408047552891</timestamp>
//	    <update-key>UPDATE-58291257-5905779483037696000</update-key>
//	    <update-content>
//	      <person>
//	        <id>ajYoKPoPYy</id>
//	        <first-name>Shiya</first-name>
//	        <last-name>Luo</last-name>
//	        <current-share>
//	          <comment>test2</comment>
//	          <content>
//	            <submitted-url>http://image-store.slidesharecdn.com/3bc45416-23f0-11e4-94dd-12313b090d61-large.png</submitted-url>
//	          </content>
//	        </current-share>
//	      </person>
//	    </update-content>
//	  </update>
	  
	    //Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getElementsByTagName("update");
		
	    for (int i = 0; i < nodeList.getLength(); i++) {

	    	//We have encountered an <employee> tag.
	    	Node node = nodeList.item(i);
	    	if (node instanceof Element) {
	    		
	    	LinkedInFeedBean feed = new LinkedInFeedBean();
	    	
	    	String timexml = ((Element) node).getElementsByTagName("timestamp").item(0).getTextContent();
	    	System.out.println("time stamp: "+timexml);
	    	long input = Long.parseLong(timexml);
	    	System.out.print(" long :" + input);
	    	
	    	String time = usingDateFormatterWithTimeZone(input);
	        //Get output in GMT
	    	//System.out.println(time);
	    	feed.setTime(time);
	    	System.out.print(" after convert:" + time);
	    	feed.setFeed_id(((Element) node).getElementsByTagName("update-key").item(0).getTextContent());
	        
	        NodeList tempPositionsNodes = ((Element) node).getElementsByTagName("update-content");
	        Node tempPositionsNode = tempPositionsNodes.item(0);
	        if (tempPositionsNode instanceof Element) {
	        	try{
			        NodeList tempPositionNodes = ((Element) tempPositionsNode).getElementsByTagName("person");
			        Node tempPositionNode = tempPositionNodes.item(0);
			        if (tempPositionNode instanceof Element) {
			        	try {
			        		feed.setUser_id(((Element) tempPositionNode).getElementsByTagName("id").item(0).getTextContent());
			        		feed.setFirstName(((Element) tempPositionNode).getElementsByTagName("first-name").item(0).getTextContent());
			        		feed.setLastName(((Element) tempPositionNode).getElementsByTagName("last-name").item(0).getTextContent());
			        	
			        		NodeList tempCompanyNodes = ((Element) tempPositionNode).getElementsByTagName("current-share");
				        	Node tempCompanyNode = tempCompanyNodes.item(0);
				        	if (tempCompanyNode instanceof Element) {
				        		try {
				        			feed.setMessage(((Element) tempCompanyNode).getElementsByTagName("comment").item(0).getTextContent());
				        			
				        			NodeList tempContentNodes = ((Element) tempCompanyNode).getElementsByTagName("content");
						        	Node tempContentNode = tempContentNodes.item(0);
						        	if (tempContentNode instanceof Element) {
						        		try {
						        			feed.setPicture(((Element) tempContentNode).getElementsByTagName("submitted-url").item(0).getTextContent());
						        		} catch (NullPointerException e){
						        			//e.printStackTrace();
						        		}
						        	}
						        	
				        		} catch (NullPointerException e){
				        			//e.printStackTrace();
				        		}
				        	}
				        	
				        	
			        	} catch (NullPointerException e){
		        			//e.printStackTrace();
			        	}
		
			        }
	        	} catch (NullPointerException e){
	        		//e.printStackTrace();
	        	}
	        }
	        
	        feed.setSource("linkedin");
	        
		    if(feed.getUser_id()!="") {
		        System.out.println(feed.toString());
		        insertFeeds(feed.getFeed_id(),feed.getUser_id(),feed.getFirstName()+" "+feed.getLastName(),feed.getMessage(),
		        		feed.getPicture(),feed.getTime(),feed.getSource());
		     }
	      }

	    }

	}

	private static void insertFeeds(String feedid, String userid, String name, String message, String picurl, String time, String source) {
		connect();
		message = getInsertableString(message);
		String insertFeed = "INSERT INTO feeds (feeds_id,user_id,user_name,feed_message,feed_pic,feed_time,feed_source) VALUE " +  
				"('"+feedid+"','"+userid+"','"+name+"','"+message+"','"+picurl+"','"+time +"','"+source +"')";
		try {
			st.execute(insertFeed);
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}


	private static void connect() {
	 try
	    {
	      // create our mysql database connection
		 if (conn == null) {
			 
		
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      Class.forName(myDriver);
	      conn = DriverManager.getConnection(url, "root", "112233");
	      
	      st = conn.createStatement();
		 }    
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	}
	
	public static String getInsertableString(String str) {
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
	
	

	private String usingDateFormatterWithTimeZone(long input){
		Date date = new Date(input);
		//Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//sdf.setCalendar(cal);
		//cal.setTime(date);
		return sdf.format(date);

	}

}
