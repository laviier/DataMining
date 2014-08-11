package mining2;

import java.io.Serializable;

/**
 * 
 * This class contains the point information used for calculation.
 * 
 * */
public class Point implements Serializable {

	private static final long serialVersionUID = -6367428052254303463L;
	//private String uid;
	private double x;
	private double y;
	private int clusterIndex;
	private String linkedinId;
	private int isClient;
	private int isFirst;

	public Point(double x, double y) {
		//this.uid = uid;
		this.x = x;
		this.y = y;
		this.clusterIndex = -1;
		this.linkedinId = "";
		this.isClient = -1;
		this.isFirst = -1;
	}
	
//	public String getId() {
//		return uid;
//	}
//
//	public void setId(String id) {
//		this.uid = id;
//	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + ") linkedin id: " + linkedinId 
				+" isClient: "+ isClient +" Cluster: " + clusterIndex;
	}

	public int getClusterIndex() {
		return this.clusterIndex;
	}

	public void setClusterIndex(int clusterIndex) {
		this.clusterIndex = clusterIndex;
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
	
	public int getIsFist() {
		return this.isFirst;
	}
	
	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}
}

