package model;

import java.util.ArrayList;
import java.util.List;

public class FacebookFeeds {
	private List<FacebookFeedBean> data;

	public FacebookFeeds() {
		data = new ArrayList<>();
	}
	
	public List<FacebookFeedBean> getData() {
		return data;
	}

	public void setData(List<FacebookFeedBean> data) {
		this.data = data;
	}
	
	public void addFeed(FacebookFeedBean feed) {
		data.add(feed);
	}
}
