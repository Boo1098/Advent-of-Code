package com.eulersboiler.advent2018.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

	public static void main(String args[]) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day1.txt"));
		int sum = 0;
		ArrayList<Integer> freq = new ArrayList<>();
		freq.add(0);
		for (int i = 0; i < lines.size(); i++) {
			String cur = lines.get(i);
			sum+=Integer.parseInt(cur);
		}
		System.out.println(sum);
		sum = 0;
		boolean c = true;
		while (c) {
			for (int i = 0; i < lines.size(); i++) {
				String cur = lines.get(i);
				sum+=Integer.parseInt(cur);
				if(freq.contains(sum)) {
					c=false;
					System.out.println(sum);
					i=lines.size();
				}
				freq.add(sum);
			}
		}

	}
}
