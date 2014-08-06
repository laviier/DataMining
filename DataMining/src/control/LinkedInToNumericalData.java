package control;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import model.AverageSalariesOfLinkedInUser;
import model.LinkedInUser;

public class LinkedInToNumericalData {
	private final String URL1 = "http://www.indeed.com/salary?q1=";
	private final String URL2 = "&l1=";
	private final String URL3 = "&tm=1";
	private List<LinkedInUser> users;
	private List<Point> points;
	private List<AverageSalariesOfLinkedInUser> salaries;

	public LinkedInToNumericalData(List<LinkedInUser> users) {
		this.users = users;
		salaries = new ArrayList<AverageSalariesOfLinkedInUser>(users.size());
		points = new ArrayList<Point>(users.size());
		
		for (int i = 0; i < users.size(); i++) {
			salaries.add(new AverageSalariesOfLinkedInUser());
			points.add(new Point());
		}
	}
	
	/**
	 * change title to URL format
	 * @param original title 
	 * @return URL formatted title
	 */
	private String titleToURLFormat(String title) {
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
	private int getSalary(String description) {
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
	
	private void setTitleSalary() {
		for (int i = 0; i < users.size(); i++) {
			LinkedInUser user = users.get(i);
			String titleURLFormat = titleToURLFormat(user.getTitle());
			//no title 
			if (titleURLFormat == null || titleURLFormat.length() == 0) {
				salaries.get(i).setAverageTitleSalary(0);
				continue;
			}

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

			int averageTitleSalary = getSalary(description);
			salaries.get(i).setAverageTitleSalary(averageTitleSalary);
		}
	}
	
	private void setCompanySalary() {
		for (int i = 0; i < users.size(); i++) {
			LinkedInUser user = users.get(i);
			String companyURLFormat = titleToURLFormat(user.getCompany());
			//no title 
			if (companyURLFormat == null || companyURLFormat.length() == 0) {
				salaries.get(i).setAverageCompanySalary(0);
				continue;
			}

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

			System.out.println(description1);
			System.out.println(description2);
			int averageCompanySalary1 = getSalary(description1);
			int averageCompanySalary2 = getSalary(description2);
			System.out.println(Math.max(averageCompanySalary1, averageCompanySalary2));
			salaries.get(i).setAverageCompanySalary(Math.max(averageCompanySalary1, averageCompanySalary2));
		}
	}
	
	private void setLocationSalary() {
		for (int i = 0; i < users.size(); i++) {
			LinkedInUser user = users.get(i);
			String locationURLFormat = titleToURLFormat(user.getLocation());
			//no title 
			if (locationURLFormat == null || locationURLFormat.length() == 0) {
				salaries.get(i).setAverageLocationSalary(0);
				continue;
			}

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

			System.out.println(description);
			int averageLocationSalary = getSalary(description);
			salaries.get(i).setAverageLocationSalary(averageLocationSalary);
		}
	}
	
	private void normalizeSalary() {
		
	}
	
	public List<Point> getPoints() {
		setTitleSalary();
		setCompanySalary();
		setLocationSalary();
		return points;
	}
	
	public void test() {
		//setTitleSalary();
		setCompanySalary();
		//setLocationSalary();
		System.out.println("Test done");
		/*for (int i = 0; i < salaries.size(); i++) {
			System.out.println("one person:");
			AverageSalariesOfLinkedInUser userSalary = salaries.get(i);
			System.out.print(userSalary.getAverageCompanySalary() + " ");
			System.out.print(userSalary.getAverageLocationSalary() + " ");
			System.out.println(userSalary.getAverageTitleSalary());
		}*/
	}
}
