package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Day23 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day23.txt"));
		ArrayList<long[]> bots = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {
			String[] s = lines.get(i).replaceAll("pos=<", "").replaceAll(">, r=", ",").split(",");
			long[] in = new long[4];
			in[0] = Integer.parseInt(s[0]);
			in[1] = Integer.parseInt(s[1]);
			in[2] = Integer.parseInt(s[2]);
			in[3] = Integer.parseInt(s[3]);
			bots.add(in);
		}
		long maxR = 0, maxID = 0;
		for (int i = 0; i < bots.size(); i++)
			if (bots.get(i)[3] > maxR) {
				maxR = bots.get(i)[3];
				maxID = i;
			}
		long[] maxB = bots.get((int) maxID);
		int count = 0;
		for (long[] b : bots) {
			if (Math.abs(b[0] - maxB[0]) + Math.abs(b[1] - maxB[1]) + Math.abs(b[2] - maxB[2]) < maxB[3])
				count++;
		}
		System.out.println(count);
		long minX = Integer.MAX_VALUE;
		long minY = Integer.MAX_VALUE;
		long minZ = Integer.MAX_VALUE;
		long maxX = Integer.MIN_VALUE;
		long maxY = Integer.MIN_VALUE;
		long maxZ = Integer.MIN_VALUE;
		for (long[] b : bots) {
			if (b[0] > maxX)
				maxX = b[0];
			if (b[0] < minX)
				minX = b[0];
			if (b[1] > maxY)
				maxY = b[1];
			if (b[1] < minY)
				minY = b[1];
			if (b[2] > maxZ)
				maxZ = b[2];
			if (b[2] < minZ)
				minZ = b[2];
		}

		PriorityQueue<Cube> cubes = new PriorityQueue<>(Comparator.comparingLong(Cube::getCompare));
		cubes.add(new Cube(minX, minY, minZ, maxX, maxY, maxZ, bots));
		Cube max = null;
		while (!cubes.isEmpty()) {
			Cube c = cubes.poll();
			if (c.size() != 0) {
				Cube[] newC = c.newCubes(bots);
				c.compare();
				for (Cube d : newC)
					if (d.amount() != 0)
						cubes.add(d);
			} else {
				max = c;
				break;
			}
		}

		System.out.println(max.getD(0, 0, 0)+" "+max);
	}

	private static class Cube {
		long minX, minY, minZ, maxX, maxY, maxZ;
		int amount = 0;
		long compare = 0;

		public Cube(long miX, long miY, long miZ, long mX, long mY, long mZ, ArrayList<long[]> bots) {
			minX = miX;
			minY = miY;
			minZ = miZ;
			maxX = mX;
			maxY = mY;
			maxZ = mZ;
			getHits(bots);
			compare = compare();
		}

		public long compare() {
			long dist = 0;
			if (0 <= maxX && 0 >= minX && 0 <= maxY && 0 >= minY && 0 <= maxZ && 0 >= minZ)
				dist = 0;
			else {
				long[] distt = new long[8];
				distt[0] = Math.abs(minX) + Math.abs(minY) + Math.abs(minZ);
				distt[1] = Math.abs(minX) + Math.abs(minY) + Math.abs(maxZ);
				distt[2] = Math.abs(minX) + Math.abs(maxY) + Math.abs(minZ);
				distt[3] = Math.abs(minX) + Math.abs(maxY) + Math.abs(maxZ);
				distt[4] = Math.abs(maxX) + Math.abs(minY) + Math.abs(minZ);
				distt[5] = Math.abs(maxX) + Math.abs(minY) + Math.abs(maxZ);
				distt[6] = Math.abs(maxX) + Math.abs(maxY) + Math.abs(minZ);
				distt[7] = Math.abs(maxX) + Math.abs(maxY) + Math.abs(maxZ);
				dist = Integer.MAX_VALUE;
				for (long l : distt) {
					if (l < dist)
						dist = l;
				}
			}
			long l = (long) ((-((long) amount) * Math.pow(10, 15)));
			return l + dist;
		}

		public long getCompare() {
			return compare;
		}

		public int getD(int i, int j, int k) {
			return (int) (Math.abs(minX - i) + Math.abs(minY - j) + Math.abs(minZ - k));
		}

		public long size() {
			return maxX - minX + maxY - minY + maxZ - minZ;
		}

		public int amount() {
			return -amount;
		}

		public Cube[] newCubes(ArrayList<long[]> bots) {
			Cube[] c = new Cube[8];
			long[] center = new long[3];
			center[0] = minX + (maxX - minX) / 2;
			center[1] = minY + (maxY - minY) / 2;
			center[2] = minZ + (maxZ - minZ) / 2;
			c[0] = new Cube(minX, minY, minZ, center[0], center[1], center[2], bots);

			c[1] = new Cube(center[0] + 1, minY, minZ, maxX, center[1], center[2], bots);

			c[2] = new Cube(minX, center[1] + 1, minZ, center[0], maxY, center[2], bots);

			c[3] = new Cube(center[0] + 1, center[1] + 1, minZ, maxX, maxY, center[2], bots);

			c[4] = new Cube(minX, minY, center[2] + 1, center[0], center[1], maxZ, bots);

			c[5] = new Cube(center[0] + 1, minY, center[2] + 1, maxX, center[1], maxZ, bots);

			c[6] = new Cube(minX, center[1] + 1, center[2] + 1, center[0], maxY, maxZ, bots);

			c[7] = new Cube(center[0] + 1, center[1] + 1, center[2] + 1, maxX, maxY, maxZ, bots);

			return c;

		}

		private int getHits(ArrayList<long[]> bots) {
			int count = 0;
			long[] center = new long[3];
			center[0] = minX + (maxX - minX) / 2;
			center[1] = minY + (maxY - minY) / 2;
			center[2] = minZ + (maxZ - minZ) / 2;
			for (long[] b : bots) {
				boolean[] bb = new boolean[9];
				bb[1] = canHit(minX, minY, minZ, b);
				bb[2] = canHit(minX, minY, maxZ, b);
				bb[3] = canHit(minX, maxY, minZ, b);
				bb[4] = canHit(minX, maxY, maxZ, b);
				bb[5] = canHit(maxX, minY, minZ, b);
				bb[6] = canHit(maxX, minY, maxZ, b);
				bb[7] = canHit(maxX, maxY, minZ, b);
				bb[8] = canHit(maxX, maxY, maxZ, b);
				boolean flag = false;
				for (boolean bbb : bb)
					if (bbb)
						flag = bbb;
				if (flag)
					count++;
			}
			amount = count;
			return count;
		}

		private boolean canHit(long x, long y, long z, long[] b) {
			long[] center = new long[3];
			center[0] = x;
			center[1] = y;
			center[2] = z;
			long[] dir = new long[3];
			for (int i = 0; i < dir.length; i++)
				dir[i] = center[i] - b[i];
			double[] mint = new double[6];
			mint[0] = -((double) b[0] - minX) / dir[0];
			mint[1] = -((double) b[0] - maxX) / dir[0];
			mint[2] = -((double) b[1] - minY) / dir[1];
			mint[3] = -((double) b[1] - maxY) / dir[1];
			mint[4] = -((double) b[2] - minZ) / dir[2];
			mint[5] = -((double) b[2] - maxZ) / dir[2];
			double[][] hits = new double[6][3];
			ArrayList<double[]> sh = new ArrayList<>();
			for (int i = 0; i < 6; i++) {
				hits[i][0] = (b[0] + mint[i] * dir[0]);
				hits[i][1] = (b[1] + mint[i] * dir[1]);
				hits[i][2] = (b[2] + mint[i] * dir[2]);
				if (hits[i][0] >= minX && hits[i][0] <= maxX && hits[i][1] >= minY && hits[i][1] <= maxY
						&& hits[i][2] >= minZ && hits[i][2] <= maxZ)
					sh.add(hits[i]);
			}
			boolean flag = false;
			for (double[] shrt : sh) {
				if (Math.abs(shrt[0] - b[0]) + Math.abs(shrt[1] - b[1]) + Math.abs(shrt[2] - b[2]) <= b[3]) {
					flag = true;
				}
			}
			if (flag || (b[0] >= minX && b[0] <= maxX && b[1] >= minY && b[1] <= maxY && b[2] >= minZ && b[2] <= maxZ))
				return true;
			return false;
		}

		public String toString() {
			return "(" + minX + ", " + minY + ", " + minZ + ") to (" + maxX + "," + maxY + "," + maxZ + ") with "
					+ amount;
		}
	}
}