package mining2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * This class is the single machine sequential version of k means clustering of
 * points. It uses a threshold to detect converge.
 * 
 * */

public class KMeansPointSeq {
	private static int threshold = 5;

	public void cluster(List<Point> points, int K) {

		int diff = Integer.MAX_VALUE;
		int N = points.size();

		/* Initialize clusters */
		Point[] clusters = new Point[K];

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = 0.0;
		double maxY = 0.0;

		for (Point p : points) {
			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();
			}
			
			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}
			
		}

		double betweenY = Math.abs((maxY - minY)) / (K - 1);
		double betweenX = Math.abs((maxX - minX)) / (K - 1);
		for (int i = 0; i < K; i++) {
			clusters[i] = new Point(minX + i * betweenX, minY + i * betweenY);
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
				int size = new_clusters.get(j).size();
				for (Point np : new_clusters.get(j)) {
					sumX += np.getX();
					sumY += np.getY();
				}

				if (size == 0) {
					clusters[j] = new Point(r.nextDouble() * (maxX - minX),
							r.nextDouble() * (maxY - minY));
				} else {
					clusters[j] = new Point(sumX / size, sumY / size);
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
				(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2)), 0.5);
	}

}
