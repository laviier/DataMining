package mining;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * This class is the single machine sequential version of k means clustering of
 * points. It uses a threshold to detect converge.
 * 
 * */

public class KMeansPointSeq {
	private static int threshold = 5;

	public void cluster(ArrayList<Point> points, int K) {

		int diff = Integer.MAX_VALUE;
		int N = points.size();

		/* Initialize clusters */
		Point[] clusters = new Point[K];

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		double maxX = 0.0;
		double maxY = 0.0;
		double maxZ = 0.0;

		for (Point p : points) {
			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();
			}
			if (p.getZ() < minZ) {
				minZ = p.getZ();
			}
			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}
			if (p.getZ() > maxZ) {
				maxZ = p.getZ();
			}
		}

		double betweenY = Math.abs((maxY - minY)) / (K - 1);
		double betweenX = Math.abs((maxX - minX)) / (K - 1);
		double betweenZ = Math.abs((maxZ - minZ)) / (K - 1);
		for (int i = 0; i < K; i++) {
			clusters[i] = new Point(minX + i * betweenX, minY + i * betweenY, minZ + i * betweenZ);
		}

		Random r = new Random();

		ArrayList<ArrayList<Point>> new_clusters = new ArrayList<ArrayList<Point>>();
		while (diff > threshold) {
			diff = 0;
			new_clusters = new ArrayList<ArrayList<Point>>();
			for (int j = 0; j < K; j++) {
				new_clusters.add(new ArrayList<Point>());
			}

			for (int i = 0; i < N; i++) {
				Point p = points.get(i);
				double minDistance = Double.MAX_VALUE;
				int n = 0;

				for (int j = 0; j < K; j++) {
					double distance = getDistance(p, clusters[j]);
					if (distance < minDistance) {
						minDistance = distance;
						n = j;
					}
				}

				if (p.getClusterIndex() != n) {
					diff++;
					p.setClusterIndex(n);
				}

				new_clusters.get(n).add(p);
			}

			for (int j = 0; j < K; j++) {
				double sumX = 0.0;
				double sumY = 0.0;
				double sumZ = 0.0;
				int size = new_clusters.get(j).size();
				for (Point np : new_clusters.get(j)) {
					sumX += np.getX();
					sumY += np.getY();
					sumZ += np.getZ();
				}

				if (size == 0) {
					clusters[j] = new Point(r.nextDouble() * (maxX - minX),
							r.nextDouble() * (maxY - minY),r.nextDouble() * (maxZ - minZ));
				} else {
					clusters[j] = new Point(sumX / size, sumY / size, sumZ / size);
				}
			}
		}

		for (Point p : points) {
			System.out.println(p);
		}
		return;
	}

	private double getDistance(Point a, Point b) {
		return Math.pow(
				(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2) + 
				Math.pow((a.getZ() - b.getZ()), 2)), 0.5);
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Please specify K and input file path");
			return;
		}

		int K = 1;
		try {
			K = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("K should be a number");
			return;
		}

		long startTime = System.currentTimeMillis();

		ArrayList<Point> points = ReadInput.readPoint(args[1]);
		KMeansPointSeq kMeans = new KMeansPointSeq();
		kMeans.cluster(points, K);

		long endTime = System.currentTimeMillis();

		// Print out time
		// System.out.println("Running Time: " + (endTime - startTime));

	}
}
