package com.eulersboiler.advent2018.day09;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Day15 {

	public static void main(String args[]) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("Day15.txt"));
		boolean fff = true;
		int hit = 23;
		while (fff) {
			// fff=false;

			char[][] map = new char[lines.size()][lines.get(0).length()];
			for (int i = 0; i < lines.size(); i++) {
				map[i] = lines.get(i).toCharArray();
			}
			int elf = 0;
			ArrayList<Unit> u = new ArrayList<>();
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[y].length; x++) {
					if (map[y][x] != '.' && map[y][x] != '#') {
						u.add(new Unit(map[y][x], x, y, hit));
						if (map[y][x] == 'E') {
							elf++;
						}
						map[y][x] = '.';
					}
				}
			}

			int round = 0;
			// System.out.println(round);
			// print(map, u);

			go: while (check(u)) {
				// System.out.println(u.size());
				ArrayList<Unit> temp = new ArrayList<>();
				while (u.size() > 0) {
					int mI = 0;
					Unit max = u.get(0);
					for (int i = 0; i < u.size(); i++) {
						if (max.getX() + max.getY() * 999 > u.get(i).getX() + u.get(i).getY() * 999) {
							mI = i;
							max = u.get(i);
						}
					}
					temp.add(max);
					u.remove(mI);
				}
				u = temp;
				ArrayList<Unit> enemies = null;
				for (int i = 0; i < u.size(); i++) {

					Unit cur = u.get(i);
					int x = cur.getX();
					int y = cur.getY();

					char look;
					if (cur.getType() == 'G') {
						look = 'E';
					} else {
						look = 'G';
					}
					enemies = new ArrayList<>();
					for (int j = 0; j < u.size(); j++) {
						// System.out.println(u.get(i).getType());
						if (u.get(j).getType() == look) {
							enemies.add(u.get(j));
						}
					}
					if (enemies.size() == 0) {
						break go;
					}
					boolean move = true;
					for (int j = 0; j < enemies.size(); j++) {
						Unit curE = enemies.get(j);
						int cx = curE.getX();
						int cy = curE.getY();
						if (cx - 1 == x && cy == y) {
							move = false;
						}
						if (cx + 1 == x && cy == y) {
							move = false;
						}
						if (cx == x && cy == y + 1) {
							move = false;
						}
						if (cx == x && cy == y - 1) {
							move = false;
						}
					}
					if (enemies.size() == 0) {
						break;
					}
					if (move) {

						ArrayList<Point> moveable = new ArrayList<>();
						for (int j = 0; j < enemies.size(); j++) {
							Unit e = enemies.get(j);
							x = e.getX();
							y = e.getY();
							if (map[y - 1][x] == '.' && !check2(x, y - 1, u)) {
								moveable.add(new Point(x, y - 1));
							}
							if (map[y][x - 1] == '.' && !check2(x - 1, y, u)) {
								moveable.add(new Point(x - 1, y));
							}
							if (map[y][x + 1] == '.' && !check2(x + 1, y, u)) {
								moveable.add(new Point(x + 1, y));
							}
							if (map[y + 1][x] == '.' && !check2(x, y + 1, u)) {
								moveable.add(new Point(x, y + 1));
							}

						}

						List<List<Point>> paths = new ArrayList<>();
						for (int j = 0; j < moveable.size(); j++) {
							paths.add(getPath(cur.getX(), cur.getY(), moveable.get(j).getX(), moveable.get(j).getY(),
									map, u));
						}

						int minL = Integer.MAX_VALUE;

						List<List<Point>> shortt = new ArrayList<>();
						for (int j = 0; j < paths.size(); j++) {
							if (!paths.get(j).isEmpty() && paths.get(j).size() < minL) {
								minL = paths.get(j).size();

							}
						}

						for (int j = 0; j < paths.size(); j++) {
							if (!paths.get(j).isEmpty() && paths.get(j).size() == minL) {
								shortt.add(paths.get(j));
							}
						}

						ArrayList<Point> shorte = new ArrayList<>();
						int minn = Integer.MAX_VALUE;
						for (int j = 0; j < shortt.size(); j++) {
							if (shortt.get(j).get(shortt.get(j).size() - 1).getX()
									+ shortt.get(j).get(shortt.get(j).size() - 1).getY() * 999 < minn) {
								minn = (int) (shortt.get(j).get(shortt.get(j).size() - 1).getX()
										+ shortt.get(j).get(shortt.get(j).size() - 1).getY() * 999);
							}
						}

						for (int j = 0; j < shortt.size(); j++) {
							if (shortt.get(j).get(shortt.get(j).size() - 1).getX()
									+ shortt.get(j).get(shortt.get(j).size() - 1).getY() * 999 == minn) {
								shorte.add(shortt.get(j).get(1));
							}
						}

						if (minL != 100000 && !shorte.isEmpty()) {
							cur.move(shorte.get(0).getX(), shorte.get(0).getY());
						}
					}
					int minHP = 300;
					Unit a = null;
					x = cur.getX();
					y = cur.getY();
					ArrayList<Unit> att = new ArrayList<>();
					ArrayList<Unit> at = new ArrayList<>();
					for (int j = 0; j < enemies.size(); j++) {
						Unit curE = enemies.get(j);

						int cx = curE.getX();
						int cy = curE.getY();
						if (cx == x && cy == y + 1) {
							att.add(curE);
							if (minHP > curE.getHP()) {
								minHP = curE.getHP();
								// a = curE;
							}
						}
						if (cx == x - 1 && cy == y) {
							att.add(curE);
							if (minHP > curE.getHP()) {
								minHP = curE.getHP();
								// a = curE;
							}
						}
						if (cx == x + 1 && cy == y) {
							att.add(curE);
							if (minHP > curE.getHP()) {
								minHP = curE.getHP();
								// a = curE;
							}
						}
						if (cx == x && cy == y - 1) {
							att.add(curE);
							if (minHP > curE.getHP()) {
								minHP = curE.getHP();
								// a = curE;
							}
						}
					}
					if (minHP < 300) {
						for (Unit d : att) {
							if (d.getHP() == minHP) {
								at.add(d);
							}
						}

						int mm = Integer.MAX_VALUE;
						for (Unit d : at) {
							if (d.getX() + d.getY() * 999 < mm) {
								mm = d.getX() + d.getY() * 999;
								a = d;
							}
						}
					}
					if (a != null) {

						a.hit(cur.getHit());
						if (a.dead()) {
							int in = getIndex(a, u);
							if (in <= i) {
								i--;
							}
							u.remove(in);

							enemies.remove(getIndex(a, enemies));
						}
					}
					// print(map, u);
				}

				round++;
				// try {
				// TimeUnit.SECONDS.sleep(1);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				System.out.println(round);
				print(map, u);
			}
			int endE = 0;
			for (Unit d : u) {
				if (d.getType() == 'E') {
					endE++;
				}
			}
			// System.out.println(endE+" "+elf);
			if (endE == elf) {
				System.out.println(hit);
				fff = false;
				int sum = 0;
				for (int i = 0; i < u.size(); i++) {
					sum += u.get(i).getHP();
				}
				System.out.println((round) * sum);
				System.out.println(round + " " + sum);
				print(map, u);
			} else {
				System.out.println(hit);
				hit++;
			}
		}
	}

	private static Unit check4(int x, int y, ArrayList<Unit> u) {
		for (Unit d : u) {
			if (d.getX() == x && d.getY() == y) {
				return d;
			}
		}
		return null;
	}

	private static void print(char[][] map, ArrayList<Unit> u) {
		for (int y = 0; y < map.length; y++) {
			ArrayList<Unit> g = new ArrayList<>();
			for (int x = 0; x < map.length; x++) {
				if (check4(x, y, u) != null) {
					Unit f = check4(x, y, u);
					System.out.print(f.getType());
					g.add(f);
				} else {
					System.out.print(map[y][x]);
				}
			}
			System.out.print(" -> ");
			for (int k = 0; k < g.size(); k++) {
				System.out.print(g.get(k) + "(" + g.get(k).getHP() + ") ");
			}
			System.out.println();
		}
	}

	private static boolean check2(int x, int y, ArrayList<Unit> u) {
		for (Unit d : u) {
			if (d.getX() == x && d.getY() == y) {
				return true;
			}
		}
		return false;
	}

	private static int getIndex(Unit a, ArrayList<Unit> u) {
		for (int i = 0; i < u.size(); i++) {
			if (a == u.get(i)) {
				return i;
			}
		}
		return 0;
	}

	private static List<Point> getPath(int xi, int yi, double x2, double y2, char[][] map, ArrayList<Unit> u) {
		boolean flag = true;
		ArrayList<Point> visited = new ArrayList<>();
		// visited.add(new Point(xi, yi));
		ArrayList<ArrayList<Point>> end = new ArrayList<>();
		end.add(new ArrayList<>());
		end.get(0).add(new Point(xi, yi));
		if (xi == x2 && yi == y2) {
			System.out.println(xi + " " + x2);
		}
		int length = 0;
		while (flag && length < 30) {
			for (int i = 0; i < end.size(); i++) {
				int x = (int) end.get(i).get(end.get(i).size() - 1).getX();
				int y = (int) end.get(i).get(end.get(i).size() - 1).getY();
				boolean flag2 = false;
				if (map[y - 1][x] == '.' && !visited(x, y - 1, visited) && !check2(x, y - 1, u)) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> newE = (ArrayList<Point>) end.get(i).clone();
					newE.add(new Point(x, y - 1));
					end.add(newE);
					visited.add(new Point(x, y - 1));
					if (x == x2 && y - 1 == y2) {
						break;
					}
					flag2 = true;
				}
				if (map[y][x - 1] == '.' && !visited(x - 1, y, visited) && !check2(x - 1, y, u)) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> newE = (ArrayList<Point>) end.get(i).clone();
					newE.add(new Point(x - 1, y));
					end.add(newE);
					visited.add(new Point(x - 1, y));
					if (x - 1 == x2 && y == y2) {
						break;
					}
					flag2 = true;
				}

				if (map[y][x + 1] == '.' && !visited(x + 1, y, visited) && !check2(x + 1, y, u)) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> newE = (ArrayList<Point>) end.get(i).clone();
					newE.add(new Point(x + 1, y));
					end.add(newE);
					visited.add(new Point(x + 1, y));
					if (x + 1 == x2 && y == y2) {
						break;
					}
					flag2 = true;
				}
				if (map[y + 1][x] == '.' && !visited(x, y + 1, visited) && !check2(x, y + 1, u)) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> newE = (ArrayList<Point>) end.get(i).clone();
					newE.add(new Point(x, y + 1));
					end.add(newE);
					visited.add(new Point(x, y + 1));
					if (x == x2 && y + 1 == y2) {
						break;
					}
					flag2 = true;
				}
				// if(flag2) {
				end.remove(i);
				i--;
				// }
			}

			for (int i = 0; i < end.size(); i++) {
				int x = (int) end.get(i).get(end.get(i).size() - 1).getX();
				int y = (int) end.get(i).get(end.get(i).size() - 1).getY();
				if (x == x2 && y == y2) {
					flag = false;
					return end.get(i);
				}
			}
			length++;
		}
		return Collections.emptyList();
	}

	private static boolean visited(int x, int y, ArrayList<Point> v) {
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i).getX() == x && v.get(i).getY() == y) {
				return true;
			}
		}
		return false;
	}

	private static boolean check(ArrayList<Unit> u) {
		boolean gob = false;
		boolean elf = false;
		for (int i = 0; i < u.size(); i++) {
			if (u.get(i).toString().charAt(0) == 'G') {
				gob = true;
			}
			if (u.get(i).toString().charAt(0) == 'E') {
				elf = true;
			}
		}
		if (gob != elf) {
			return false;
		}
		return true;
	}

	public static class Unit {
		char type;
		int x, y;
		int hp = 200;
		int hit = 3;

		public Unit(char c, int xc, int yc) {
			type = c;
			x = xc;
			y = yc;
		}

		public Unit(char c, int x2, int y2, int hitc) {
			type = c;
			x = x2;
			y = y2;
			if (c == 'E') {
				hit = hitc;
			}
		}

		public String toString() {
			return "" + type;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public char getType() {
			return type;
		}

		public void move(double x2, double y2) {
			x = (int) x2;
			y = (int) y2;

		}

		public int getHP() {
			return hp;
		}

		public void hit(int i) {
			hp -= i;
		}

		public int getHit() {
			return hit;
		}

		public boolean dead() {
			return hp <= 0;
		}
	}
}