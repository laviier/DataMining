package model;

public class FacebookFeedBean {
	String id;
	FacebookUser from;
	String message;
	String story;
	String picture;
	FeedPlace place;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FacebookUser getFrom() {
		return from;
	}
	public void setFrom(FacebookUser from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public FeedPlace getPlace() {
		return place;
	}
	public void setPlace(FeedPlace place) {
		this.place = place;
	}
	
}
