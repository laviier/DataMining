package dao;

public class LinkedInFeedBean {
	String feed_id;
	String user_id;
	String firstName;
	String lastName;
	String message;
	String picture;
	String time;
	String source;
	
	public LinkedInFeedBean(){
		this.feed_id = "";
		this.user_id= "";
		this.firstName= "";
		this.lastName= "";
		this.message= "";
		this.picture= "";
		this.time= "";
		this.source= "";
	}
	
	public String getFeed_id() {
		return feed_id;
	}

	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String toString() {
		return this.feed_id + " " +
				this.user_id + " " +
				this.firstName + " " +
				this.lastName + " " +
				this.message + " " +
				this.picture + " " +
				this.time + " " +
				this.source;
	}
}
