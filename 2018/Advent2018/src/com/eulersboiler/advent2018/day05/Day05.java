package com.eulersboiler.advent2018.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day05 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day5.txt"));
		boolean flag = false;
		String input = lines.get(0);
		while (!flag) {
			flag = true;
			for (int i = 0; i < input.length() - 1; i++) {
				if (input.charAt(i) == input.charAt(i + 1) + 32 || input.charAt(i) == input.charAt(i + 1) - 32) {
					input = input.substring(0, i) + input.substring(i + 2);
					flag = false;

				}
			}
		}
		System.out.println(input.length());

		int shortest = 1000000;
		String shorte = input;
		for (char j = 'a'; j <= 'z'; j++) {
			input = shorte.replaceAll("[" + j + "" + (char) (j - 32) + "]", "");
			flag = false;
			while (!flag) {
				flag = true;
				for (int i = 0; i < input.length() - 1; i++) {
					if (input.charAt(i) == input.charAt(i + 1) + 32 || input.charAt(i) == input.charAt(i + 1) - 32) {
						input = input.substring(0, i) + input.substring(i + 2);
						flag = false;
					}
				}
			}
			if (input.length() < shortest) {
				shortest = input.length();
			}
		}
		System.out.println(shortest);
	}

}
