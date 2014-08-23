package model;

public class JobUpdateBean {
	private String id;
	private String firstName;
	private String lastName;
	private String title;
	private String company;
	private String titleNew;
	private String companyNew;
	private String url;
	private String profileURL;
	
	public JobUpdateBean() {
		this.id = "";
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.titleNew = "";
		this.companyNew = "";
		this.url = "";
		this.profileURL = "";
	}
	
	public JobUpdateBean(String newId) {
		this.id = newId;
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.company = "";
		this.titleNew = "";
		this.companyNew = "";
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
	
	public String getTitleNew() {
		return this.titleNew;
	}
	
	public void setTitleNew(String newTitle) {
		this.titleNew = newTitle;
	}
	
	public String getCompanyNew() {
		return this.companyNew;
	}
	
	public void setCompanyNew(String newCompany) {
		this.companyNew = newCompany;
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
		String result = this.id + " " + this.firstName + " " + this.lastName  + " " + this.url
				  + " " + this.profileURL + " " + this.title + " " + this.company  + " " 
				+ this.titleNew + " " + this.companyNew;
		return result;
	}
}
