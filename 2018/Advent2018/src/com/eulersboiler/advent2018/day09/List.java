package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class List {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		java.util.List<String> lines = Files.readAllLines(Paths.get("test.txt"));

		int last = 7206100;
		ArrayList<Integer> circle = new ArrayList<>();
		int currentM = 0;
		circle.add(currentM);
		int id = 0;
		int count = 0;
		currentM++;
		int look = Integer.parseInt(lines.get(0));
		int[] in = new int[lines.size()];
		for (int i = 0; i < in.length; i++) {
			in[i] = Integer.parseInt(lines.get(i));
		}
		int laster = 0;
		for (int i = 1; i < last; i++) {
			currentM = i;
			if (circle.size() == 1) {
				circle.add(currentM);
				id = circle.size() - 1;
			} else if (id + 2 <= circle.size()) {
				circle.add((id + 2), currentM);
				id = (id + 2);
			} else {
				id = (id + 2) - circle.size();
				circle.add(id, currentM);
			}
			if (currentM % 23 == 0) {
				for (int j = 0; j < circle.size(); j++) {
					if (in[count] == circle.get(j)) {
						count++;
						int sum = 0;
						for(int k = last; k!=j+1; k++) {
							k = k%circle.size();
							sum++;
						}
						laster = j;
						
						System.out.println(sum);
						
					}
				}
			}
		}
	}

}
