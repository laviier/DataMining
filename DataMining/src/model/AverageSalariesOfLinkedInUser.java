package model;

public class AverageSalariesOfLinkedInUser {
	private int averageTitleSalary;
	private int averageCompanySalary;
	private int averageLocationSalary;
	
	public AverageSalariesOfLinkedInUser() {
		averageTitleSalary = 0;
		averageCompanySalary = 0;
		averageLocationSalary = 0;
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
	
}
