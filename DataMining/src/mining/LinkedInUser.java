package mining;

public class LinkedInUser {
	private String linkedinId;
	private int titleId;
	private int companyId;
	private int locationId;
	private int isClient;
	
	public LinkedInUser(String id, int isClient) {
		this.linkedinId = id;
		this.isClient = isClient;
	}
	
	public String getLinkedinId() {
		return linkedinId;
	}
	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}
	public int getIsClient() {
		return this.isClient;
	}
	public void setIsClient(int isClient) {
		this.isClient = isClient;
	}
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
}
