package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day12.txt"));
		char[] in2 = lines.get(0).toCharArray();
		ArrayList<Character> cur = new ArrayList<>();
		long start = -5;
		cur.add('.');
		cur.add('.');
		cur.add('.');
		cur.add('.');
		cur.add('.');
		for (int i = 0; i < in2.length; i++) {
			cur.add(in2[i]);
		}
		cur.add('.');
		cur.add('.');
		cur.add('.');
		cur.add('.');
		cur.add('.');
		ArrayList<String[]> rules = new ArrayList<>();
		for (int i = 2; i < lines.size(); i++) {
			String[] c = lines.get(i).split(" => ");
			// if (c[0].charAt(2) != c[1].charAt(0))
			rules.add(c);
		}

		ArrayList<Character> n = new ArrayList<>();
//		for (char c : cur) {
//			System.out.print(c);
//		}
//		System.out.println();
		long d = 5;
		d*=100000;
		d*=100000;
//		System.out.println(d);
		for (long i = 0; i < 2500; i++) {
			start += 5;
			for (int j = 2; j < cur.size() - 2; j++) {
				String k = "" + cur.get(j - 2) + cur.get(j - 1) + cur.get(j) + cur.get(j + 1) + cur.get(j + 2);
				n.add(check(k, rules));
			}
			start-=3;
			while(n.get(n.size()-1)=='.') {
				n.remove(n.size()-1);
			}
			while (n.get(0) == '.') {
				n.remove(0);
				start++;
			}
			n.add(0, '.');
			n.add(0, '.');
			n.add(0, '.');
			n.add(0, '.');
			n.add(0, '.');
			n.add('.');
			n.add('.');
			n.add('.');
			n.add('.');
			n.add('.');
			start -= 5;
			cur = new ArrayList<>();
			for (char c : n) {
				cur.add(c);
			}
//			System.out.print(i+": ");
//			for (char c : cur) {
//				System.out.print(c);
//			}
//			System.out.println(start);
			n = new ArrayList<>();
		}
		
			start+=d-2500;
		
		long sum = 0;
		for (int i = 0; i < cur.size(); i++) {
			if (cur.get(i) == '#') {
				sum += (i + start);
			}
		}
		for (char c : cur) {
			System.out.print(c);
		}
		System.out.println(sum);
	}

	private static char check(String string, ArrayList<String[]> rules) {
		for (int i = 0; i < rules.size(); i++) {
			if (string.equals(rules.get(i)[0])) {
				return rules.get(i)[1].charAt(0);
			}
		}
		return '.';
	}
}
