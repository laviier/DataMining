package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.scribe.model.Token;

import com.google.gson.Gson;

public class UpdateJobSql{
	
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn;
	private static Statement st;
	
//	public static void main(String[] args) {
//		List<LeadBean> l = getContactList();
//		for (LeadBean a: l) {
//			System.out.println(a.toString());
//		}
//		System.out.println(l.size());
//	}
	
	public static Token getAccessToken() {
		connect();
		String[] token = new String[3];
		String getToken = "SELECT token,raw_response,expire_date FROM linkedin_token "
				+ "where fa_id=1 and expire_date=(SELECT MAX(expire_date) FROM linkedin_token where fa_id=1);";
		try {
			ResultSet rs = st.executeQuery(getToken);

			// iterate through the java resultset
			while (rs.next()) {
				token[0] = rs.getString("token");
				token[1] = rs.getString("raw_response");
				token[2] = rs.getString("expire_date");
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		Token accessToken = new Token(token[0],"",token[1]);
		return accessToken;
	}
	
	private static void connect() {
		 try
		    {
		      String myDriver = "org.gjt.mm.mysql.Driver";
		      Class.forName(myDriver);
		      conn = DriverManager.getConnection(url, "root", "112233");
		      st = conn.createStatement();
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
	}
}
