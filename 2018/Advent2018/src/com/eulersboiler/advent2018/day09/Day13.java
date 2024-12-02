package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day13.txt"));
		char[][] map = new char[lines.size()][lines.get(0).length()];
		ArrayList<Cart> carts = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			map[i] = lines.get(i).toCharArray();
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == '>' || map[i][j] == '<' || map[i][j] == '^' || map[i][j] == 'v') {
					carts.add(new Cart(map[i][j], j, i));
					if (map[i][j] == '>' || map[i][j] == '<') {
						map[i][j] = '-';
					} else {
						map[i][j] = '|';
					}
				}
			}
		}
		boolean crash = false;
		int cx = 0;
		int cy = 0;
		while (!crash) {
			ArrayList<Cart> temp = new ArrayList<>();
			while (carts.size() > 0) {
				Cart max = carts.get(0);
				int id = 0;
				for (int i = 1; i < carts.size(); i++) {
					if (!max.compareTo(carts.get(i))) {
						max = carts.get(i);
						id = i;
					}
				}
				temp.add(carts.remove(id));
			}
			carts = temp;

			for (int i = 0; i < carts.size(); i++) {
				Cart c = carts.get(i);
				c.move(map);
				Cart d;
				for (int j = 0; j < carts.size(); j++) {
					if (j != i) {
						d = carts.get(j);
						if (c.getX() == d.getX() && c.getY() == d.getY()) {
							crash = true;
							cx = c.getX();
							cy = c.getY();
							if (i < j) {
								carts.remove(j);
								carts.remove(i);
								j = 1000;
								i -= 1;
							} else {
								carts.remove(i);
								carts.remove(j);
								j = 1000;
								i -= 2;
							}
						}
					}
				}
			}
		}
		System.out.println(cx + "," + cy);

		while (carts.size() > 1) {
			ArrayList<Cart> temp = new ArrayList<>();
			while (carts.size() > 0) {
				Cart max = carts.get(0);
				int id = 0;
				for (int i = 1; i < carts.size(); i++) {
					if (!max.compareTo(carts.get(i))) {
						max = carts.get(i);
						id = i;
					}
				}
				temp.add(carts.remove(id));
			}
			carts = temp;

			for (int i = 0; i < carts.size(); i++) {
				Cart c = carts.get(i);
				c.move(map);
				Cart d;
				for (int j = 0; j < carts.size(); j++) {
					if (j != i) {
						d = carts.get(j);
						if (c.getX() == d.getX() && c.getY() == d.getY()) {
							crash = false;
							cx = c.getX();
							cy = c.getY();
							if (i < j) {
								carts.remove(j);
								carts.remove(i);
								j = 1000;
								i -= 1;
							} else {
								carts.remove(i);
								carts.remove(j);
								j = 1000;
								i -= 2;
							}
						}
					}
				}
			}
		}
		System.out.println((carts.get(0).getX() - 1) + "," + carts.get(0).getY());
	}

	public static boolean check(ArrayList<Cart> c, int x, int y) {
		for (Cart d : c) {
			if (d.getX() == x && d.getY() == y) {
				return true;
			}
		}
		return false;
	}

}
