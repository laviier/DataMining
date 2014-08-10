package mining;
public class AverageSalariesOfLinkedInUser {
	private int averageTitleSalary;
	private int averageCompanySalary;
	private int averageLocationSalary;
	private String linkedinId;
	private int isClient;
	
	public AverageSalariesOfLinkedInUser() {
		this.averageTitleSalary = -1;
		this.averageCompanySalary = -1;
		this.averageLocationSalary = -1;
		this.linkedinId = "";
		this.isClient = -1;
	}
	
	public int getAverageTitleSalary() {
		return averageTitleSalary;
	}
	public void setAverageTitleSalary(int averageTitleSalary) {
		this.averageTitleSalary = averageTitleSalary;
	}
	public int getAverageCompanySalary() {
		return averageCompanySalary;
	}
	public void setAverageCompanySalary(int averageCompanySalary) {
		this.averageCompanySalary = averageCompanySalary;
	}
	public int getAverageLocationSalary() {
		return averageLocationSalary;
	}
	public void setAverageLocationSalary(int averageLocationSalary) {
		this.averageLocationSalary = averageLocationSalary;
	}
	public int getIsClient() {
		return this.isClient;
	}
	
	public void setIsClient(int isClient) {
		this.isClient = isClient;
	}
	
	public String getLinkedinId() {
		return linkedinId;
	}
	
	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}
	public String toString() {
		return averageTitleSalary + " " + averageCompanySalary + " " + averageLocationSalary;
	}
}
