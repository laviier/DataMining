package model;

public class LeadBean {
	private String id;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String url;
	private String profileURL;
	private int score;
	
	public LeadBean() {
		this.id = "";
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.url = "";
		this.profileURL = "";
		this.score = 0;
	}
	
	public LeadBean(String newId) {
		this.id = newId;
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.url = "";
		this.profileURL = "";
		this.score = 0;
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
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int newScore) {
		this.score = newScore;
	}
	
	public String toString() {
		String result = this.id + " " + this.firstName + " " + this.lastName  + " " + this.url
				  + " " + this.profileURL + " "  + this.title  + " " + this.company + " " + this.score;
		return result;
	}
}
