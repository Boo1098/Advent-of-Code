package com.eulersboiler.advent2018.day09;

public class Cart {
	int direction = 0;
	int next = 0;
	int x;
	int y;

	public Cart(char c, int xc, int yc) {
		if (c == '>') {
			direction = 0;
		} else if (c == '^') {
			direction = 1;

		} else if (c == '<') {
			direction = 2;
		} else if (c == 'v') {
			direction = 3;
		}
		x = xc;
		y = yc;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void move(char[][] map) {
		if (direction == 0) {
			x++;
		} else if (direction == 1) {
			y--;
		} else if (direction == 2) {
			x--;
		} else if (direction == 3) {
			y++;
		}
		if (map[y][x] == '/') {
			if (direction == 0 || direction == 2) {
				direction++;
				if (direction == 4) {
					direction = 0;
				}
			} else {
				direction--;
				if (direction == -1) {
					direction = 3;
				}
			}
		}
		if (map[y][x] == '\\') {
			if (direction == 0 || direction == 2) {
				
				direction--;
				if (direction == -1) {
					direction = 3;
				}
			} else {
				direction++;
				if (direction == 4) {
					direction = 0;
				}
			}
		}
		if (map[y][x] == '+') {
			if (next == 0) {
				direction++;
				next++;
			} else if (next == 1) {
				next++;
			} else if (next == 2) {
				direction--;
				next = 0;
			}
			if (direction == -1) {
				direction = 3;
			} else if (direction == 4) {
				direction = 0;
			}
		}
	}

	public boolean compareTo(Cart c) {
		if (y < c.getX()) {
			return true;
		} else if (y == c.getX()) {
			if (x < c.getY()) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	public String toString() {
		return x + "," + y;
	}

}
