package com.eulersboiler.advent2018.day09;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day10.txt"));
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<Point> velocity = new ArrayList<>();
		int x, y;
		for (int i = 0; i < lines.size(); i++) {
			String[] cur = lines.get(i).replaceAll("position=<", "").replaceAll(">", "").replaceAll("velocity=<", "")
					.replaceAll(">", "").replaceAll(",", "").replaceAll("^ ", "").replaceAll("  ", " ").split(" ");
			x = Integer.parseInt(cur[0]);
			y = Integer.parseInt(cur[1]);
			points.add(new Point(x, y));
			x = Integer.parseInt(cur[2]);
			y = Integer.parseInt(cur[3]);
			velocity.add(new Point(x, y));
		}
		int time = 0;
		while (check(points)) {
			for (int i = 0; i < points.size(); i++) {
				Point p = points.get(i);
				Point v = velocity.get(i);
				points.get(i).setLocation(p.getX() + v.getX(), p.getY() + v.getY());
			}
			time++;
		}
		System.out.println(time);

	}

	private static boolean check(ArrayList<Point> points) {
		int maxHeight = Integer.MIN_VALUE;
		int lowHeight = Integer.MAX_VALUE;
		for (Point p : points) {
			if (p.getY() > maxHeight) {
				maxHeight = (int) p.getY();
			}
			if (p.getY() < lowHeight) {
				lowHeight = (int) p.getY();
			}
		}
		if (maxHeight - lowHeight < 10) {
			int lowX = Integer.MAX_VALUE;
			int highX = Integer.MIN_VALUE;
			for (Point p : points) {
				if (p.getX() > highX) {
					highX = (int) p.getX();
				}
				if (p.getX() < lowX) {
					lowX = (int) p.getX();
				}
			}
			for (int y = lowHeight; y <= maxHeight; y++) {
				for (int x = lowX; x <= highX; x++) {
					if (check2(points, x, y)) {
						System.out.print("#");
					} else {
						System.out.print(".");
					}
				}
				System.out.println();
			}
			return false;
		}
		return true;
	}

	private static boolean check2(ArrayList<Point> points, int x, int y) {
		for (Point p : points) {
			if (p.getX() == x && p.getY() == y) {
				return true;
			}
		}
		return false;
	}
}
