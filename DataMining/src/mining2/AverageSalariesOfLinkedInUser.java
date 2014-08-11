package mining2;
public class AverageSalariesOfLinkedInUser {
	private int averageTitleSalary;
	private int averageCompanySalary;
	private String linkedinId;
	private int isClient;
	private int isFirst;
	
	public AverageSalariesOfLinkedInUser() {
		this.averageTitleSalary = -1;
		this.averageCompanySalary = -1;
		this.linkedinId = "";
		this.isClient = -1;
		this.isFirst = -1;
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
		return averageTitleSalary + " " + averageCompanySalary;
	}
	public int getIsFist() {
		return this.isFirst;
	}
	
	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}
}
