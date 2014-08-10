package mining;
import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AverageSalary {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	
	private final static String URL1 = "http://www.indeed.com/salary?q1=";
	private final static String URL2 = "&l1=";
	private final static String URL3 = "&tm=1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connect();
		try {
			setCompanySalary();
			setTitleSalary();
			setLocationSalary();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * change title to URL format
	 * @param original title 
	 * @return URL formatted title
	 */
	private static String titleToURLFormat(String title) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < title.length(); j++) {
			char c = title.charAt(j);
			if (!Character.isLetter(c)) {
				if (sb.length() == 0 || sb.charAt(sb.length() - 1) == '+') {
					continue;
				}
				sb.append('+');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * return salary from description
	 * @param description from indeed salary
	 * @return salary
	 */
	private static int getSalary(String description) {
		int result = 0;
		for (int i = 0 ; i < description.length(); i++) {
			char c = description.charAt(i);
			if (c == '.') {
				break;
			}
			if (!Character.isDigit(c)) {
				continue;
			}
			result = 10 * result + (c - '0');
		}
		//System.out.println("Salary: " + result);
		return result;
	}
	
	private static void setTitleSalary() throws SQLException {
		// create the java mysql update preparedstatement
	    String query = "update title set ave_salary = ? where title_name = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    
		ResultSet rs = st.executeQuery("Select title_name from title");
		while (rs.next()) {
			int averageTitleSalary = 0;
			String title = rs.getString(1);
			String titleURLFormat = titleToURLFormat(title);
		
			if (titleURLFormat != null && titleURLFormat.length() != 0) {
				String completeURL = URL1 + titleURLFormat;
				Document doc = null;
				String description = null;
				try {
					doc = Jsoup.connect(completeURL).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (doc != null) {
					Elements metalinks = doc.select("meta[name=description]");
					if (!metalinks.isEmpty()) {
						description = metalinks.first().attr("content").toString();
					}
				}
	
				averageTitleSalary = getSalary(description);
			}
			preparedStmt.setInt   (1, averageTitleSalary);
		    preparedStmt.setString(2, title);
		    preparedStmt.executeUpdate();
		}
		preparedStmt.close();
	}
	
	private static void setCompanySalary() throws SQLException {
		// create the java mysql update preparedstatement
	    String query = "update company set ave_salary = ? where company_name = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    
		ResultSet rs = st.executeQuery("Select company_name from company");
		while (rs.next()) {
			int averageCompanySalary = 0;
			String company = rs.getString(1);
			String companyURLFormat = titleToURLFormat(company);
			//no title 
			if (companyURLFormat != null && companyURLFormat.length() != 0) {
				String completeURL1 = URL1 + companyURLFormat + URL2 + URL3;
				String completeURL2 = URL1 + companyURLFormat + URL2;
				Document doc1 = null;
				Document doc2 = null;
				String description1 = null;
				String description2 = null;
				try {
					doc1 = Jsoup.connect(completeURL1).get();
					doc2 = Jsoup.connect(completeURL2).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (doc1 != null) {
					Elements metalinks = doc1.select("meta[name=description]");
					if (!metalinks.isEmpty()) {
						description1 = metalinks.first().attr("content").toString();
					}
				}
				if (doc2 != null) {
					Elements metalinks = doc2.select("meta[name=description]");
					if (!metalinks.isEmpty()) {
						description2 = metalinks.first().attr("content").toString();
					}
				}
	
				int averageCompanySalary1 = getSalary(description1);
				int averageCompanySalary2 = getSalary(description2);
				averageCompanySalary = Math.max(averageCompanySalary1, averageCompanySalary2);
			}
			preparedStmt.setInt   (1, averageCompanySalary);
			preparedStmt.setString(2, company);
		    preparedStmt.executeUpdate();
		}
		preparedStmt.close();
	}
	
	private static void setLocationSalary() throws SQLException {
		// create the java mysql update preparedstatement
	    String query = "update location set ave_salary = ? where location_name = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    
	    ResultSet rs = st.executeQuery("Select location_name from location");
		while (rs.next()) {
			int averageLocationSalary = 0;
			String location = rs.getString(1);
			String locationURLFormat = titleToURLFormat(location);

			if (locationURLFormat != null && locationURLFormat.length() != 0) {
				String completeURL = URL1 + locationURLFormat;
				Document doc = null;
				String description = null;
				try {
					doc = Jsoup.connect(completeURL).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (doc != null) {
					Elements metalinks = doc.select("meta[name=description]");
					if (!metalinks.isEmpty()) {
						description = metalinks.first().attr("content").toString();
					}
				}
				averageLocationSalary = getSalary(description);
			}
			preparedStmt.setInt   (1, averageLocationSalary);
			preparedStmt.setString(2, location);
		    preparedStmt.executeUpdate();
		}
		preparedStmt.close();
	}

	private static void connect() {
		try {
	      // create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      Class.forName(myDriver);
	      conn = DriverManager.getConnection(url, "root", "112233");

	      // create the java statement
	      st = conn.createStatement();	      
	    } catch (Exception e) {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	}
}