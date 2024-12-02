package com.eulersboiler.advent2018.day09;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day17 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day17.txt"));
		char[][] map = new char[2000][2000];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				map[x][y] = '.';
			}
		}
		map[500][0] = '|';
		int minY = Integer.MAX_VALUE;
		int maxY = 0;
		for (int i = 0; i < lines.size(); i++) {
			String c = lines.get(i).replaceAll(", ", " ").replaceAll("=", " ").replaceAll("\\.\\.", " ");
			String[] cur = c.split(" ");
			if (cur[0].charAt(0) == 'x') {
				int x = Integer.parseInt(cur[1]);
				int y1 = Integer.parseInt(cur[3]);
				int y2 = Integer.parseInt(cur[4]);
				for (int y = y1; y <= y2; y++) {
					map[x][y] = '#';
				}
				if (y2 > maxY) {
					maxY = y2;
				}
				if (y1 < minY) {
					minY = y1;
				}
			} else {
				int y = Integer.parseInt(cur[1]);
				int x1 = Integer.parseInt(cur[3]);
				int x2 = Integer.parseInt(cur[4]);
				for (int x = x1; x <= x2; x++) {
					map[x][y] = '#';
				}
				if (y > maxY) {
					maxY = y;
				}
				if (y < minY) {
					minY = y;
				}
			}
		}

		ArrayList<Point> ps = new ArrayList<>();
		ps.add(new Point(500, 0));

		while (ps.size() > 0) {
			ArrayList<Point> newP = new ArrayList<>();
			if (ps.size() >= 3) {
				System.out.print("");
			}
			ArrayList<Point> temp = new ArrayList<>();
			while (ps.size() > 0) {
				int mY = Integer.MAX_VALUE;
				int ID = 0;
				for (int i = 0; i < ps.size(); i++) {
					if (ps.get(i).getY() < mY) {
						mY = (int) ps.get(i).getY();
						ID = i;
					}
				}
				temp.add(ps.remove(ID));
			}
			ps = temp;
			for (Point p : ps) {
				int x = (int) p.getX();
				int y = (int) p.getY();
				if (map[x][y + 1] == '.') {
					if ((map[x][y + 2] != '|' && map[x][y + 2] != '~')
							|| !(!scanLeft(map, x, y + 2) || !scanRight(map, x, y + 2))) {

						map[x][y + 1] = '|';
						p.move(x, y + 1);
						newP.add(p);
					} else {
						map[x][y + 1] = '|';
					}

				} else {
					map[x][y] = '~';
					boolean flag = true;
					boolean f = false;
					int ix = x;
					int iy = y;
					while (map[x - 1][y] != '#' && map[x][y + 1] != '.' && flag) {
						x = x - 1;
						map[x][y] = '~';
						if (map[x][y + 1] == '.' && (map[x + 1][y + 1] != '|' && map[x + 1][y + 1] != '~')) {
							flag = false;
							newP.add(new Point(x, y));
							f = true;
						}
						if (map[x][y + 1] == '.' && (map[x + 1][y + 1] == '|' || map[x + 1][y + 1] == '~')) {
							map[x][y] = '.';
						}
					}

					x = ix;
					y = iy;

					flag = true;
					while (map[x + 1][y] != '#' && map[x][y + 1] != '.' && flag) {

						x = x + 1;
						map[x][y] = '~';
						if (map[x][y + 1] == '.' && (map[x - 1][y + 1] != '|' && map[x - 1][y + 1] != '~')) {
							flag = false;
							newP.add(new Point(x, y));
							f = true;
						}
						if (map[x][y + 1] == '.' && (map[x - 1][y + 1] == '|' || map[x - 1][y + 1] == '~')) {
							map[x][y] = '.';
						}
					}
					if (!f && scanLeft(map, ix, iy) && scanRight(map, ix, iy)) {

						newP.add(new Point(ix, iy - 1));
					}
				}
			}
			ps = newP;
			trim(ps, maxY);
		}
		int sum = 0;
		for (int x = 0; x < map.length; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (map[x][y] == '|' || map[x][y] == '~')
					sum++;
			}
		}
		System.out.println(sum);

		for (int y = 0; y < map[0].length; y++) {
			for (int x = 1; x < map.length; x++) {
				if (map[x - 1][y] == '.' && map[x][y] == '~') {
					for (int x2 = x; map[x2][y] == '~'; x2++) {
						x = x2;
						map[x2][y] = '|';
					}
				}
			}
		}
		for (int y = 0; y < map[0].length; y++) {
			for (int x = map.length - 2; x > 0; x--) {
				if (map[x + 1][y] == '.' && map[x][y] == '~') {
					for (int x2 = x; map[x2][y] == '~'; x2--) {
						x = x2;
						map[x2][y] = '|';
					}
				}
			}
		}

		sum = 0;
		for (int x = 0; x < map.length; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (map[x][y] == '~')
					sum++;
			}
		}
		System.out.println(sum);
	}

	private static void trim(ArrayList<Point> ps, int maxY) {
		for (int i = 0; i < ps.size(); i++) {
			if (ps.get(i).getY() > maxY) {
				ps.remove(i);
				i--;
			}
		}
	}

	private static boolean scanLeft(char[][] map, int x, int y) {
		while (map[x - 1][y] != '#' && map[x - 1][y + 1] != '.') {
			x = x - 1;
		}
		if (map[x - 1][y + 1] == '.') {
			return false;
		}
		return true;
	}

	private static boolean scanRight(char[][] map, int x, int y) {
		while (map[x + 1][y] != '#' && map[x + 1][y + 1] != '.') {
			x = x + 1;
		}
		if (map[x + 1][y + 1] == '.') {
			return false;
		}
		return true;
	}
}