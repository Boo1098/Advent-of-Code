package com.eulersboiler.advent2018.day09;

public class Day11 {
	public static void main(String[] args) {
		int serial = 7672;
		int[][] power = new int[300][300];
		for (int x = 0; x < 300; x++) {
			for (int y = 0; y < 300; y++) {
				int temp = x + 10;
				temp *= y;
				temp += serial;
				temp *= (x + 10);
				String t = "" + temp;
				if (t.length() >= 3) {
					temp = Integer.parseInt("" + t.charAt(t.length() - 3));
				} else {
					temp = 0;
				}
				temp -= 5;
				power[x][y] = temp;
			}
		}
		int max = 0;
		int xc = 0;
		int yc = 0;
		int size = 0;

		for (int i = 1; i < 30; i++) {
			for (int x = 0; x < 300 - i; x++) {
				for (int y = 0; y < 300 - i; y++) {
					int sum = 0;
					for (int j = 0; j < i; j++) {
						for (int k = 0; k < i; k++) {
							sum += power[j + x][k + y];
						}
					}
					if (sum > max) {
						max = sum;
						xc = x;
						yc = y;
						size = i;
					}
				}
			}
		}
		System.out.println(xc + "," + yc + "," + size);
	}
}
