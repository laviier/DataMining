package model;

public class LinkedInUser {
	private String id;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String location;
	private String url;
	private String profileURL;
	private int startYear,startMonth;
	
	public LinkedInUser() {
		this.id = "";
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.location = "";
		this.url = "";
		this.profileURL = "";
		this.startMonth = 0;
		this.startYear = 0;
	}
	
	public LinkedInUser(String newId) {
		this.id = newId;
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.location = "";
		this.url = "";
		this.profileURL = "";
		this.startMonth = 0;
		this.startYear = 0;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String newId) {
		this.id = newId;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String newCompany) {
		this.company = newCompany;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public void setURL(String newURL) {
		this.url = newURL;
	}
	
	public String getProfileURL() {
		return this.profileURL;
	}
	
	public void setProfileURL(String newURL) {
		this.profileURL = newURL;
	}
	
	public int getStartMonth() {
		return this.startMonth;
	}
	
	public void setStartMonth(int newStartMonth) {
		this.startMonth = newStartMonth;
	}
	
	public int getStartYear() {
		return this.startYear;
	}
	
	public void setStartYear(int newStartYear) {
		this.startYear = newStartYear;
	}
	
	public String toString() {
		String result = this.id + " " + this.firstName + " " + this.lastName  + " " + this.url
				  + " " + this.profileURL + " " + this.location + " " + this.title 
				  + " " + this.company  + " " + this.startYear  + " " + this.startMonth;
		return result;
	}
}

