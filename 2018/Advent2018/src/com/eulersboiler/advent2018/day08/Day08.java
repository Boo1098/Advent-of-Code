package com.eulersboiler.advent2018.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day08 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day8.txt"));
		String[] in = null;
		for (int i = 0; i < lines.size(); i++) {
			String cur = lines.get(i);
			in = cur.split(" ");
		}
		int[] ini = new int[in.length];
		for (int i = 0; i < in.length; i++) {
			ini[i] = Integer.parseInt(in[i]);
		}
		System.out.println(sum(ini)[0]);
		System.out.println(sum2(ini)[0]);
	}

	private static int[] sum(int[] in) {

		int[] node = new int[2];
		node[0] = in[0];
		node[1] = in[1];
		int[] out = new int[2];
		int sum = 0;
		if (node[0] == 0) {
			for (int i = 2; i < 2 + node[1]; i++) {
				sum += in[i];
			}
			out[0] = sum;
			out[1] = 2 + node[1];
			return out;
		}

		int place = 2;
		for (int i = 0; i < in[0]; i++) {
			int[] temp = new int[in.length - node[1] - place];
			for (int j = place; j < in.length - node[1]; j++) {
				temp[j - place] = in[j];
			}
			int[] back = sum(temp);
			sum += back[0];
			place += back[1];
		}
		for (int i = place; i < place + node[1]; i++) {
			sum += in[i];
		}
		place += node[1];

		out[0] = sum;
		out[1] = place;
		return out;

	}

	private static int[] sum2(int[] in) {

		int[] node = new int[2];
		node[0] = in[0];
		node[1] = in[1];
		int[] out = new int[2];
		int sum = 0;
		if (node[0] == 0) {
			for (int i = 2; i < 2 + node[1]; i++) {
				sum += in[i];
			}
			out[0] = sum;
			out[1] = 2 + node[1];
			return out;
		}

		int place = 2;
		ArrayList<Integer> sums = new ArrayList<>();
		for (int i = 0; i < in[0]; i++) {
			int[] temp = new int[in.length - node[1] - place];
			for (int j = place; j < in.length - node[1]; j++) {
				temp[j - place] = in[j];
			}
			int[] back = sum2(temp);
			sums.add(back[0]);
			place += back[1];
		}
		for (int i = place; i < place + node[1]; i++) {
			if (in[i] - 1 < sums.size()) {
				sum += sums.get(in[i] - 1);
			}
		}
		place += node[1];

		out[0] = sum;
		out[1] = place;
		return out;

	}

}
