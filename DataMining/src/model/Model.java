package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private LinkedInUserDao  lDao;
	
	public Model() throws ServletException, MyDAOException, DAOException {
		try {
			String jdbcDriver = "com.mysql.jdbc.Driver";
			String jdbcURL    = "jdbc:mysql://raymond-james.isri.cmu.edu:3306/raymond";
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "112233");
			lDao  = new LinkedInUserDao(jdbcDriver, jdbcURL, "client", "linkedin_user","positions","company","location","title");
		}
		catch (MyDAOException e) {
			throw new ServletException(e);
		}
	}
	public LinkedInUserDao getLinkedInUserDao() { return lDao; }
}