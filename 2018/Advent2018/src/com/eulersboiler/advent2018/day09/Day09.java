package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day09 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day9.txt"));

		String[] in = lines.get(0).split(" ");
		int players = Integer.parseInt(in[0]);
		int last = Integer.parseInt(in[6])*100;
		ArrayList<Integer> circle = new ArrayList<>();
		int currentM = 0;
		circle.add(currentM);
		int id = 0;
		int player = 0;
		currentM++;
		long[] scores = new long[players];
		for (int i = 1; i < last; i++) {
			currentM = i;
			if (currentM % 23 == 0) {
				scores[player] += currentM;
				long score = 0;
				if (id - 7 >= 0) {
					score += circle.remove(id - 7);
					scores[player] +=score;
				} else {
					score += circle.remove((id - 7) + circle.size());
					scores[player] +=score;
				}
				System.out.println(score);
				id -= 7;
				if (id < 0) {
					id += circle.size()+1;
				}
			
			} else {
				if (circle.size() == 1) {
					circle.add(currentM);
					id = circle.size() - 1;
				} else if(id+2<=circle.size()){
					circle.add((id + 2), currentM);
					id = (id + 2);
				} else {
					id = (id+2)-circle.size();
					circle.add(id,currentM);
				}
			}
			player = (player + 1) % scores.length;
		}
		long max = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] > max) {
				max = scores[i];
			}
		}
		System.out.println(max);
	}
}
