package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day18.txt"));
		char[][] map = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			map[i] = lines.get(i).toCharArray();
		}
		ArrayList<char[][]> past = new ArrayList<>();
		for (long m = 0; m < 1000000000; m++) {
			char[][] newM = new char[map.length][map[0].length];
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {
					int x1 = x - 1;
					int x2 = x + 1;
					int y1 = y - 1;
					int y2 = y + 1;
					if (x1 == -1) {
						x1 = 0;
					}
					if (x2 == map.length) {
						x2 = map.length - 1;
					}
					if (y1 == -1) {
						y1 = 0;
					}
					if (y2 == map[x].length) {
						y2 = map[x].length - 1;
					}
					int sO = 0;
					int sL = 0;
					int sT = 0;
					for (int xx = x1; xx <= x2; xx++) {
						for (int yy = y1; yy <= y2; yy++) {
							if (xx == x && yy == y) {

							} else {
								if (map[xx][yy] == '.') {
									sO++;
								} else if (map[xx][yy] == '|') {
									sT++;
								} else {
									sL++;
								}
							}
						}
					}
					if (map[x][y] == '.') {
						if (sT >= 3) {
							newM[x][y] = '|';
						} else {
							newM[x][y] = '.';
						}
					} else if (map[x][y] == '|') {
						if (sL >= 3) {
							newM[x][y] = '#';
						} else {
							newM[x][y] = '|';
						}
					} else if (map[x][y] == '#') {
						if (sL >= 1 && sT >= 1) {
							newM[x][y] = '#';
						} else {
							newM[x][y] = '.';
						}
					}
				}
			}

			map = newM;
			past.add(newM);
			long c = check(past);
			if (c != -1) {
				ArrayList<char[][]> loop = new ArrayList<>();
				for (long i = c + 1; i < past.size(); i++) {
					loop.add(past.get((int) i));
				}
				//System.out.println("tellme");
				long left = 1000000000 - m - 2;
				long loo = loop.size();
				long finish = (left % loo);
				map = loop.get((int) finish);
				m = Long.MAX_VALUE - 1;
			}
			// if (m % 100000000 == 0) {
			// System.out.println(m);
			// for (char[] d : map) {
			// for (char c : d) {
			// System.out.print(c);
			// }
			// System.out.println();
			// }
			// System.out.println();
			// }
		}
		int sumT = 0;
		int sumL = 0;
		for (char[] d : past.get(9)) {
			for (char c : d) {
				if (c == '|')
					sumT++;
				if (c == '#')
					sumL++;

			}
		}
		System.out.println(sumT * sumL);
		sumT = 0;
		sumL = 0;
		for (char[] d : map) {
			for (char c : d) {
				if (c == '|')
					sumT++;
				if (c == '#')
					sumL++;

			}
		}
		System.out.println(sumT * sumL);
	}

	private static int check(ArrayList<char[][]> past) {
		char[][] check = past.get(past.size() - 1);
		for (int i = 0; i < past.size() - 1; i++) {
			char[][] cur = past.get(i);
			boolean flag = true;
			for (int x = 0; x < cur.length; x++) {
				for (int y = 0; y < cur.length; y++) {
					if (check[x][y] != cur[x][y]) {
						flag = false;
					}
				}
			}
			if (flag) {
				return i;
			}
		}
		return -1;
	}
}