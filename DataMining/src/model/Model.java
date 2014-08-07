package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private LinkedInUserDao  lDao;
	
	public Model(ServletConfig config) throws ServletException, MyDAOException, DAOException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			lDao  = new LinkedInUserDao(jdbcDriver, jdbcURL, "client", "linkedInUser","positions","company","location","title");
		}
		catch (MyDAOException e) {
			throw new ServletException(e);
		}
	}
	public LinkedInUserDao getLinkedInUserDao() { return lDao; }
}