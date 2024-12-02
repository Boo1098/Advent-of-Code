package com.eulersboiler.advent2018.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day06 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day6.txt"));
		ArrayList<Character[]> list = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String[] in = lines.get(i).split(" ");

			Character[] c = new Character[2];

			c[0] = in[1].charAt(0);

			c[1] = in[7].charAt(0);
			list.add(c);
		}
		HashMap<Character, String> order = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			Character[] c = list.get(i);

			if (order.get(c[1]) != null) {
				order.put(c[1], order.get(c[1]) + c[0]);

			} else {
				order.put(c[1], "" + c[0]);

			}
		}
		ArrayList<Character> executed = new ArrayList<>();
		// executed.add('G');
		boolean end = false;
		int[] timer = new int[26];
		for (int i = 0; i < timer.length; i++) {
			timer[i] = -1;
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			if (order.get(c) == null) {

				order.put(c, "delete");
				timer[c - 65] = c - 65 + 60;
			}

		}

		int time = 0;
		boolean flag = false;
		while (!end) {
			if (flag) {
				for (char c = 'A'; c <= 'Z'; c=(char) (c+1)) {

					for (int i = 0; i < executed.size(); i++) {
						char d = executed.get(i);
						if (order.get(c).contains("" + d)) {
							order.put(c, order.get(c).replace("" + d, ""));
							// System.out.println(order.get(c));

						}
						if (order.get(c).equals("")) {
							int sum = 0;
							for (int j = 0; j < 26; j++) {
								if (timer[j] >= 0) {
									sum++;
								}
							}
							if (sum < 5) {
								order.put(c, "delete");
								timer[c - 65] = c - 65 + 60;
							}
						}
					}
				}
				flag = false;
			}

			if (executed.size() == 26) {
				end = true;
			}
			for (char c = 'A'; c <= 'Z'; c++) {
				if (timer[c - 65] == 0) {
					executed.add(c);
					flag = true;
				}
			}
			for (int i = 0; i < 26; i++) {
				if (timer[i] >= 0)
					timer[i]--;

			}
			if (!end) {
				time++;
			}
			// System.out.println(executed.size() + " " + time);
		}
		for (char c : executed) {
			System.out.print("" + c);
		}
		System.out.println();
		System.out.println(time);
	}

}
