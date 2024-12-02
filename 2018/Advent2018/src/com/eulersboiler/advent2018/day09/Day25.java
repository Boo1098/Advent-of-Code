package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day25 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day25.txt"));
		int[][] in = new int[lines.size()][4];
		for (int i = 0; i < lines.size(); i++) {
			String[] s = lines.get(i).replaceAll(" ","").split(",");
			for (int j = 0; j < 4; j++) {
				in[i][j] = Integer.parseInt(s[j]);
			}
		}
		int sum = 0;
		ArrayList<ArrayList<int[]>> cons = new ArrayList<>();
		for (int j = 0; j < in.length; j++) {
			int[] cur = in[j];
			boolean flag = false;
			g: for (ArrayList<int[]> c : cons) {
				for (int[] cc : c) {
					if (Math.abs(getTax(cc, cur)) <= 3) {
						c.add(cur);
						flag = true;
						break g;

					}
				}
			}
			if (!flag) {
				ArrayList<int[]> newww = new ArrayList<>();
				newww.add(cur);
				cons.add(newww);
			}
			merge(cons);
		}
		System.out.println(cons.size());
	}

	private static void merge(ArrayList<ArrayList<int[]>> cons) {
		for(int i = 0; i<cons.size(); i++) {
			ArrayList<int[]> c1 = cons.get(i);
			for(int j = i+1; j<cons.size(); j++) {
				ArrayList<int[]> c2 = cons.get(j);
				boolean flag = false;
				for(int[] cc1 :c1) {
					for(int[]cc2: c2) {
						if(Math.abs(getTax(cc1,cc2))<=3) {
							flag = true;
						}
					}
				}
				if(flag) {
					c1.addAll(c2);
					cons.remove(cons.indexOf(c2));
					j--;
				}
			}
		}
	}

	public static int getTax(int[] c, int[] cur) {
		int sum = 0;
		for (int i = 0; i<4; i++) {
			
			sum += Math.abs(c[i]-cur[i]);
		}
		return sum;
	}
}