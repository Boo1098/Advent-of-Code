package com.eulersboiler.advent2018.day09;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day20.txt"));
		String input = lines.get(0);
		int size = 2000;
		char[][] map = new char[size][size];
		for (int x = 0; x < map.length; x++)
			for (int y = 0; y < map[x].length; y++)
				map[x][y] = '#';

		int sx = size / 2;
		int sy = size / 2;
		travel(input, sx, sy, map);

		System.out.println(furthest(map, sx, sy));
	}

	private static int furthest(char[][] map, int sx, int sy) {
		int l = 0;
		ArrayList<Point> ends = new ArrayList<>();
		ends.add(new Point(sx, sy));
		ArrayList<Point> visited = new ArrayList<>();
		visited.add(ends.get(0));
		int sum = 0;
		while (ends.size() > 0) {
			ArrayList<Point> newE = new ArrayList<>();
			for (Point p : ends) {
				int x = (int) p.getX();
				int y = (int) p.getY();
				if (map[x - 1][y] != '#' && !visited(x - 1, y, visited)) {
					newE.add(new Point(x - 1, y));
				}
				if (map[x][y - 1] != '#' && !visited(x, y - 1, visited)) {
					newE.add(new Point(x, y - 1));
				}
				if (map[x + 1][y] != '#' && !visited(x + 1, y, visited)) {
					newE.add(new Point(x + 1, y));
				}
				if (map[x][y + 1] != '#' && !visited(x, y + 1, visited)) {
					newE.add(new Point(x, y + 1));
				}
			}
			for (Point p : newE) {
				visited.add(p);
			}
			ends = newE;
			l++;
			if (l / 2 % 2 == 0 && l / 2 >= 1000) {
				sum += ends.size();
			}

		}
		System.out.println(sum);
		return l / 2;
	}

	private static boolean visited(int x, int y, ArrayList<Point> visited) {
		Point p;
		for (int i = 0; i < visited.size(); i++) {
			p = visited.get(i);
			if (p.getX() == x && p.getY() == y)
				return true;
		}
		return false;
	}

	private static ArrayList<Point> travel(String input, int x, int y, char[][] map) {
		map[x][y] = '.';

		int in = 0;
		String t = "";
		ArrayList<Point> p = new ArrayList<>();
		if (input.length() == 0) {
			return p;
		}
		p.add(new Point(x, y));

		int xs = x;
		int ys = y;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(') {
				if (in > 0) {
					t += c;
				}
				in++;
			} else if (c == ')') {
				in--;
				if (in > 0) {
					t += c;
				}
				if (in == 0) {
					ArrayList<Point> newP = new ArrayList<>();
					for (Point f : p) {
						newP.addAll(travel(t, (int) f.getX(), (int) f.getY(), map));
					}
					t = "";
					p = newP;
				}
			} else if (in > 0) {
				t += c;
			} else if (c == '|') {
				p.addAll(travel(input.substring(i + 1), xs, ys, map));
				i = input.length();
			} else if (c != '(' && c != ')' && c != '|') {

				for (Point f : p) {
					x = (int) f.getX();
					y = (int) f.getY();
					if (c == 'N') {
						map[x][y - 1] = '-';
						map[x][y - 2] = '.';
						f.move(x, y - 2);
					}
					if (c == 'E') {
						map[x + 1][y] = '|';
						map[x + 2][y] = '.';
						f.move(x + 2, y);
					}
					if (c == 'S') {
						map[x][y + 1] = '-';
						map[x][y + 2] = '.';
						f.move(x, y + 2);
					}
					if (c == 'W') {
						map[x - 1][y] = '|';
						map[x - 2][y] = '.';
						f.move(x - 2, y);
					}
				}
			}
		}
		return p;
	}
}
