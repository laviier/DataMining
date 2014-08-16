package model;

import java.util.ArrayList;
import java.util.List;

public class FacebookImages {
	private List<FacebookImage> images;

	public FacebookImages() {
		images = new ArrayList<FacebookImage>();
	}
	
	public List<FacebookImage> getImages() {
		return images;
	}

	public void setImages(List<FacebookImage> images) {
		this.images = images;
	}
	
	public void addImages(FacebookImage image) {
		images.add(image);
	}
}
