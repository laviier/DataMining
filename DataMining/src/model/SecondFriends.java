package model;

public class SecondFriends {
	private String id;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String headline;
	private String url;
	private String profileURL;

	public SecondFriends() {
		this.id = "";
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.headline = "";
		this.url = "";
		this.profileURL = "";
	}
	
	public SecondFriends(String newId) {
		this.id = newId;
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.headline = "";
		this.url = "";
		this.profileURL = "";
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
	
	public String getHeadline() {
		return this.headline;
	}
	
	public void setHeadline(String newHeadline) {
		this.headline = newHeadline;
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
	
	public String toString() {
		return (this.id+" "+this.title+" "+this.company);
	}
}
