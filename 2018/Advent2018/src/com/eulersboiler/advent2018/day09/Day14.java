package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.util.ArrayList;

public class Day14 {
	public static void main(String[] args) throws IOException {
		int in =633601;
		ArrayList<Integer> recipes = new ArrayList<>();
		int curID1 = 0;
		int curID2 = 1;
		recipes.add(3);
		recipes.add(7);
		while(recipes.size()-10<50000000) {
			int sum = 0;
//			for(int i = 0; i<recipes.size(); i++) {
//				sum+=recipes.get(i);
//			}
			sum = recipes.get(curID1)+recipes.get(curID2);
			String s = ""+sum;
			for(char c:s.toCharArray()) {
				recipes.add(Integer.parseInt(""+c));
			}

			curID1 = (curID1+recipes.get(curID1)+1)%recipes.size();
			curID2 = (curID2+recipes.get(curID2)+1)%recipes.size();
		}
		for(int i = in; i<in+10; i++) {
			System.out.print(recipes.get(i));
		}
		System.out.println();
		for(int i = 0; i<recipes.size(); i++) {
			String s = "";
			for(int j = i; j<i+6; j++) {
				s+=recipes.get(j);
			}
			//System.out.println(s);
			if(s.equals("633601")) {
				System.out.println(i);
				i=1000000;
			}
		}
	}

}
