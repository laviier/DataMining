package model;

import java.io.Serializable;

/**
 * 
 * This class contains the point information used for calculation.
 * 
 * */
public class Point implements Serializable {

	private static final long serialVersionUID = -6367428052254303463L;
	private double x;
	private double y;
	private double z;
	private int clusterIndex;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.clusterIndex = -1;
	}

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

	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	public String toString() {
		return "(" + x + "," + y + "," + ") Cluster: " + clusterIndex;
	}

	public int getClusterIndex() {
		return clusterIndex;
	}

	public void setClusterIndex(int clusterIndex) {
		this.clusterIndex = clusterIndex;
	}
}

