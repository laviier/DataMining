package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.scribe.model.Token;

public class GetFBToken {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn;
	private static Statement st;
	
	public static Token getAccessToken() {
		connect();
		String[] token = new String[2];
		String tokenStr = "SELECT MAX(token_id), token, raw_response FROM fb_token";
		
		try {
			ResultSet rs = st.executeQuery(tokenStr);
			while (rs.next()) {
				token[0] = rs.getString("token");
				token[1] = rs.getString("raw_response");
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		//System.out.println("token: " + token[0] + " raw_response: " + token[1]);
		Token accessToken = new Token(token[0], "", token[1]);
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
