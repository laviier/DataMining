package mining2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Sql2;


public class NormalizedPoints {
	private static String url = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
	private static Connection conn = null;
	private static Statement st = null;
	private List<LinkedInUser> users;
	private List<AverageSalariesOfLinkedInUser> salaries;
	private int minTitleSalary = Integer.MAX_VALUE;
	private int maxTitleSalary = Integer.MIN_VALUE;
	private int minCompanySalary = Integer.MAX_VALUE;
	private int maxCompanySalary = Integer.MIN_VALUE;
	private List<Point> points;
	
	public static void main(String[] args) {
		NormalizedPoints trial = new NormalizedPoints();
		connect();
		List<Point> points = trial.getPoints();
		KMeansPointSeq kMeans = new KMeansPointSeq();
		kMeans.cluster(points, 2);
		for(Point p: points) {
//			System.out.print("( ");
//			System.out.printf("%19.17f",p.getX());// "9.2"中的9表示输出的长度，2表示小数点后的位数。
//			System.out.print(", ");
//			System.out.printf("%19.17f",p.getY());// "9.2"中的9表示输出的长度，2表示小数点后的位数。
//			System.out.print(" ) ");
//			System.out.print("Linkedin ID: "+ p.getLinkedinId());
//			System.out.print(" isFirst: "+ p.getIsFist());
//			System.out.print(" isClient: " + p.getIsClient());
//			System.out.print(" Cluster: " +p.getClusterIndex());
//			System.out.println();
			Sql2.update(p.getLinkedinId(), p.getClusterIndex());
		}
	}
	
	public NormalizedPoints() {
		users = new ArrayList<>();
		salaries = new ArrayList<>();
		points = new ArrayList<>();
	}
	
	/**
	 * get all linkedin users from database
	 * @throws SQLException
	 */
	private void getUsersFromDB() throws SQLException {
		ResultSet rs = st.executeQuery("Select linkedin_id,is_client,is_first_level from linkedin_user");
		while (rs.next()) {
			users.add(new LinkedInUser(rs.getString("linkedin_id"),rs.getInt("is_client"),rs.getInt("is_first_level")));
		}
		
		//find the most current job of this user in the positions table
		for (int i = 0; i < users.size(); i++) {
			String sqlSelect = "Select title_id, company_id "
					+ "from positions where linkedin_id='" + users.get(i).getLinkedinId() 
					+ "' and is_current=" + 1;
			rs = st.executeQuery(sqlSelect);
			rs.next();//we have made sure every user has at least one job
			users.get(i).setTitleId(rs.getInt("title_id"));
			users.get(i).setCompanyId(rs.getInt("company_id"));
		}
	}
	
	/**
	 * get salaries from three salaries tables
	 * @throws SQLException
	 */
	private void getSalaries() throws SQLException {
		getUsersFromDB();
		ResultSet rs = null;
		for (int i = 0; i < users.size(); i++) {
			salaries.add(new AverageSalariesOfLinkedInUser());
			
			String selectTitleSalary = "Select ave_salary from title where title_id = '" + users.get(i).getTitleId() + "'";
			rs = st.executeQuery(selectTitleSalary);
			rs.next();
			minTitleSalary = Math.min(minTitleSalary, rs.getInt(1));
			maxTitleSalary = Math.max(maxTitleSalary, rs.getInt(1));
			salaries.get(i).setAverageTitleSalary(rs.getInt(1));
			
			String selectCompanySalary = "Select ave_salary from company where company_id = '" + users.get(i).getCompanyId() + "'";
			rs = st.executeQuery(selectCompanySalary);
			rs.next();
			minCompanySalary = Math.min(minCompanySalary, rs.getInt(1));
			maxCompanySalary = Math.max(maxCompanySalary, rs.getInt(1));
			salaries.get(i).setAverageCompanySalary(rs.getInt(1));
			
			salaries.get(i).setIsClient(users.get(i).getIsClient());
			salaries.get(i).setLinkedinId(users.get(i).getLinkedinId());
			salaries.get(i).setIsFirst(users.get(i).getIsFist());
		}
	}
	
	/**
	 * normalize salaries
	 * @return a set of points
	 */
	public List<Point> getPoints() {
		try {
			getSalaries();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < salaries.size(); i++) {
			double x = (double)(salaries.get(i).getAverageTitleSalary() - minTitleSalary) / (double)(maxTitleSalary - minTitleSalary);
			double y = (double)(salaries.get(i).getAverageCompanySalary() - minCompanySalary) / (double)(maxCompanySalary - minCompanySalary);
			Point p = new Point(x, y);
			p.setIsClient(salaries.get(i).getIsClient());
			p.setLinkedinId(salaries.get(i).getLinkedinId());
			p.setIsFirst(salaries.get(i).getIsFist());
			points.add(p);
			//System.out.println(x + " " + y + " " + z);
		}
		return points;
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
