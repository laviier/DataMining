package model;

public class LinkedInUser {
	private String id;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String location;
	private String url;
	
	public LinkedInUser() {
		this.id = "";
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.location = "";
		this.url = "";
	}
	
	public LinkedInUser(String newId) {
		this.id = newId;
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.location = "";
		this.url = "";
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
	
	public String toString() {
		String result = this.id + "" + this.firstName + "" + this.lastName  + "" + this.url
				 + "" + this.location + "" + this.title + "" + this.company;
		return result;
	}
}

