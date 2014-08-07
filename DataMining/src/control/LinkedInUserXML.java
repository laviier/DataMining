package control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.genericdao.DAOException;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import model.LinkedInUser;
import model.LinkedInUserDao;
import model.Model;
import model.MyDAOException;

import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LinkedInUserXML {
	ArrayList<LinkedInUser> users;
	Model model;
	LinkedInUserDao lDao;
	
	public LinkedInUserXML() {
		this.users = new ArrayList<LinkedInUser>();
		try {
			this.model = new Model();
		} catch (MyDAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.lDao = model.getLinkedInUserDao();
	}

	public void parseXML(String xmlSource) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		//Get the DOM Builder Factory
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	    //Get the DOM Builder
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    ByteArrayInputStream is = new ByteArrayInputStream(xmlSource.getBytes("UTF-8"));

	    //Load and Parse the XML document
	    //document contains the complete XML as a Tree.
	    Document document = (Document) builder.parse(is);

	    //Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getElementsByTagName("person");

	    for (int i = 0; i < nodeList.getLength(); i++) {

	    	//We have encountered an <employee> tag.
	    	Node node = nodeList.item(i);
	    	if (node instanceof Element) {
	    		
	        LinkedInUser user = new LinkedInUser();
	        
	        try {
	        	user.setId(((Element) node).getElementsByTagName("id").item(0).getTextContent());
	        	user.setFirstName(((Element) node).getElementsByTagName("first-name").item(0).getTextContent());
	        	user.setLastName(((Element) node).getElementsByTagName("last-name").item(0).getTextContent());
	        	user.setProfileURL(((Element) node).getElementsByTagName("picture-url").item(0).getTextContent());
	 	       
	        } catch (NullPointerException e){
    			e.printStackTrace();
    		}
	        
	        NodeList tempUrlNodes = ((Element) node).getElementsByTagName("site-standard-profile-request");
	        Node tempUrlNode = tempUrlNodes.item(0);
	        if (tempUrlNode instanceof Element) {
	        	user.setURL(((Element) tempUrlNode).getElementsByTagName("url").item(0).getTextContent());
	        }
	        
	        NodeList tempLocationNodes = ((Element) node).getElementsByTagName("location");
	        Node tempLocationNode = tempLocationNodes.item(0);
	        if (tempLocationNode instanceof Element) {
	        	try {
	        		user.setLocation(((Element) tempLocationNode).getElementsByTagName("name").item(0).getTextContent());
	        	} catch (NullPointerException e){
        			e.printStackTrace();
        		}
	        }
	        
	        NodeList tempPositionsNodes = ((Element) node).getElementsByTagName("positions");
	        Node tempPositionsNode = tempPositionsNodes.item(0);
	        if (tempPositionsNode instanceof Element) {
	        	try{
			        NodeList tempPositionNodes = ((Element) tempPositionsNode).getElementsByTagName("position");
			        Node tempPositionNode = tempPositionNodes.item(0);
			        if (tempPositionNode instanceof Element) {
			        	try {
			        		user.setTitle(((Element) tempPositionNode).getElementsByTagName("title").item(0).getTextContent());
			        	} catch (NullPointerException e){
		        			e.printStackTrace();
		        		}
			        	NodeList tempCompanyNodes = ((Element) tempPositionNode).getElementsByTagName("company");
			        	Node tempCompanyNode = tempCompanyNodes.item(0);
			        	if (tempCompanyNode instanceof Element) {
			        		try {
			        			user.setCompany(((Element) tempCompanyNode).getElementsByTagName("name").item(0).getTextContent());
			        		} catch (NullPointerException e){
			        			e.printStackTrace();
			        		}
			        	}
			        	
			        	NodeList tempDateNodes = ((Element) tempPositionNode).getElementsByTagName("start-date");
			        	Node tempDateNode = tempDateNodes.item(0);
			        	if (tempDateNode instanceof Element) {
			        		try {
			        			String year = ((Element) tempDateNode).getElementsByTagName("year").item(0).getTextContent();
			        			String month = ((Element) tempDateNode).getElementsByTagName("month").item(0).getTextContent();
			        			user.setStartMonth(Integer.parseInt(month));
			        			user.setStartYear(Integer.parseInt(year));
			        		} catch (NullPointerException e){
			        			e.printStackTrace();
			        		}
			        	}
			        }
	        	} catch (NullPointerException e){
	        		e.printStackTrace();
	        	}
	        }
	        
	        users.add(user);
	        
	        try {
				lDao.insert_linkedin_user(user.getId(), user.getFirstName(), user.getLastName(), user.getURL(), user.getProfileURL());
			} catch (MyDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }

	    }

	    //Printing the Employee list populated.
//	    for (int i = 0; i < users.size(); i++) {
//	      System.out.println(users.get(i).toString());
//	    }

	}
}
