package model;

public class FacebookFeedBean {
	String id;
	FacebookUser from;
	String message;
	String story;
	String picture;
	FeedPlace place;
	String created_time;
	String source;
	String object_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String updated_time) {
		this.created_time = updated_time;
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
	public void setSource(String soure) {
		this.source = soure;
	}
	public String getSource() {
		return this.source;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
}
