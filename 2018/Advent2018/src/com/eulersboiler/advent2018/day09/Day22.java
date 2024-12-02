package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day22 {
	public static int tx, ty, depth, edge = 50;

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day22.txt"));
		depth = Integer.parseInt(lines.get(0).split(" ")[1]);
		tx = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[0]);
		ty = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[1]);

		char[][] map = new char[tx + 1 + edge][ty + 1 + edge];
		int[][] elMap = new int[map.length][map[0].length];

		int sum = 0;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				elMap[x][y] = getEL(x, y, elMap);
				int rl = elMap[x][y] % 3;
				if (rl == 0)
					map[x][y] = '.';
				else if (rl == 1)
					map[x][y] = '=';
				else
					map[x][y] = '|';
				if (x <= tx && y <= ty)
					sum += rl;
			}
		}
		System.out.println("Part 1: " + sum);

		System.out.println("Part 2: " + shortestLength(map, 0, 0));
	}

	static boolean[][] vT, vG, vN;

	private static int shortestLength(char[][] map, int x, int y) {
		vT = new boolean[map.length][map[0].length];
		vG = new boolean[map.length][map[0].length];
		vN = new boolean[map.length][map[0].length];
		ArrayList<Head> ends = new ArrayList<>();
		ends.add(new Head(x, y, 0));
		int tool, length = 0;
		while (true) {
			ArrayList<Head> newH = new ArrayList<>();

			for (Head h : ends) {
				if (h.ready()) {
					x = h.getX();
					y = h.getY();
					tool = h.getTool();
					if (x == tx && y == ty)
						if (tool == 0)
							return length;
						else
							function(h.equip(map[x][y]), ends, newH);

					if (x > 0 && h.canMove(map[x - 1][y])) {
						Head g = new Head(x - 1, y, tool);
						if (empty2(ends, g) && empty2(newH, g))
							newH.add(g);
					} else if (x > 0 && !h.changed())
						function(h.equip(map[x][y]), ends, newH);

					if (y > 0 && h.canMove(map[x][y - 1])) {
						Head g = new Head(x, y - 1, tool);
						if (empty2(ends, g) && empty2(newH, g))
							newH.add(g);
					} else if (y > 0 && !h.changed())
						function(h.equip(map[x][y]), ends, newH);

					if (x < tx + edge && h.canMove(map[x + 1][y])) {
						Head g = new Head(x + 1, y, tool);
						if (empty2(ends, g) && empty2(newH, g))
							newH.add(g);
					} else if (x < tx + edge && !h.changed())
						function(h.equip(map[x][y]), ends, newH);

					if (y < ty + edge && h.canMove(map[x][y + 1])) {
						Head g = new Head(x, y + 1, tool);
						if (empty2(ends, g) && empty2(newH, g))
							newH.add(g);
					} else if (y < ty + edge && !h.changed())
						function(h.equip(map[x][y]), ends, newH);

				} else
					newH.add(h);
			}
			ends = newH;
			for (Head h : newH)
				if (h.getCool() == 0)
					if (h.getTool() == 0)
						vT[h.getX()][h.getY()] = true;
					else if (h.getTool() == 1)
						vG[h.getX()][h.getY()] = true;
					else
						vN[h.getX()][h.getY()] = true;
			length++;
		}
	}

	private static void function(Head h, ArrayList<Head> ends, ArrayList<Head> newH) {
		if (empty(ends, h) && empty(newH, h))
			newH.add(h);
	}

	private static boolean empty(ArrayList<Head> ends, Head g) {
		for (Head h : ends)
			if (h.same(g))
				return false;
		if (g.getTool() == 0)
			if (vT[g.getX()][g.getY()])
				return false;
		if (g.getTool() == 1)
			if (vG[g.getX()][g.getY()])
				return false;
		if (g.getTool() == 2)
			if (vN[g.getX()][g.getY()])
				return false;
		return true;
	}

	private static boolean empty2(ArrayList<Head> ends, Head g) {
		for (Head h : ends)
			if (h.same(g) && h.getCool() == 0)
				return false;
		if (g.getTool() == 0)
			if (vT[g.getX()][g.getY()])
				return false;
		if (g.getTool() == 1)
			if (vG[g.getX()][g.getY()])
				return false;
		if (g.getTool() == 2)
			if (vN[g.getX()][g.getY()])
				return false;
		return true;
	}

	private static int getEL(int x, int y, int[][] giMap) {
		int gi = 0;
		if (x == 0 && y == 0)
			gi = 0;
		else if (x == tx && y == ty)
			gi = 0;
		else if (y == 0)
			gi = x * 16807;
		else if (x == 0)
			gi = y * 48271;
		else
			gi = (giMap[x - 1][y] * giMap[x][y - 1]) % 20183;
		return (gi + depth) % 20183;
	}
	
	private static class Head {
		int x = 0;
		int y = 0;
		int tool = 0;
		int cooldown = 0;

		boolean changed = false;

		public Head(int xf, int yf, int i) {
			x = xf;
			y = yf;
			tool = i;
		}

		public Head(int xf, int yf, int i, boolean f) {
			x = xf;
			y = yf;
			tool = i;
			cooldown = 6;
			changed = true;
		}

		public boolean changed() {
			return changed;
		}

		public boolean ready() {
			if (cooldown == 0)
				return true;
			cooldown--;
			return false;
		}

		public int getTool() {
			return tool;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public boolean canMove(char c) {
			return (tool == 2 && (c == '=' || c == '|')) || (tool == 1 && (c == '.' || c == '='))
					|| (tool == 0 && (c == '.' || c == '|'));
		}

		public Head equip(char c) {
			if (c == '.') {
				if (tool == 0)
					return new Head(x, y, 1, true);
				if (tool == 1)
					return new Head(x, y, 0, true);
			}
			if (c == '=') {
				if (tool == 2)
					return new Head(x, y, 1, true);
				if (tool == 1)
					return new Head(x, y, 2, true);
			}
			if (c == '|') {
				if (tool == 0)
					return new Head(x, y, 2, true);
				if (tool == 2)
					return new Head(x, y, 0, true);
			}
			return null;
		}

		public boolean same(Head h) {
			return x == h.getX() && y == h.getY() && tool == h.getTool();
		}

		public int getCool() {
			return cooldown;
		}
	}
}