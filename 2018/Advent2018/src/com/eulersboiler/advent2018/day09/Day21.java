package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.util.HashMap;

public class Day21 {
	public static void main(String[] args) throws IOException {
		long[] r = new long[6];
		long last = 0;
		HashMap<Long, Boolean> answers = new HashMap<>();
		int ip = 5;
		int count = 0;
		boolean flag = false;
		boolean fff = false;
		boolean[] stupid = new boolean[100000000];
		// 0
		r[2] = 123;
		count++;
		do {
			// 1
			r[2] = r[2] & 456;
			// 2
			if (r[2] == 72)
				r[2] = 1;
			else
				r[2] = 0;
			count += 4;
		} while (r[2] == 0);
		count--;
		// 5
		r[2] = 0;
		// 6
		do {
		r[1] = r[2] | 65536;
		// 7
		r[2] = 1250634;
		count += 3;
		
			do {// 8
				flag = false;
				r[4] = r[1] & 255;
				// 9
				r[2] = r[2] + r[4];
				// 10
				r[2] = r[2] & 16777215;
				// 11
				r[2] *= 65899;
				// 12
				r[2] = r[2] & 16777215;
				// 13
				if (256 > r[1])
					r[4] = 1;
				else
					r[4] = 0;
				// 14
				r[5] = r[4] + r[5];
				// 15
				r[5] = r[5] + 1;
				// 16 addr 4 14 5
				r[5] = 27;
				count += 8;
				if (r[4] == 0) {
					// 17 addi 5 1 5
					r[4] = 0;
					do { // 18 seti 27 2 5
						r[3] = r[4] + 1;
						// 19 seti 0 5 4
						r[3] = r[3] * 256;
						// 20 addi 4 1 3
						if (r[3] > r[1])
							r[3] = 1;
						else
							r[3] = 0;
						// 21 muli 3 256 3
						r[5] = r[3] + r[5];
						// 22 gtrr 3 1 3
						r[5] = r[5] + r[1];
						// 23 addr 3 21 5
						r[5] = 25;
						count += 6;
						if (r[3] == 0) {
							// 24 addi 5 1 5
							r[4] = r[4] + 1;
							// 25 seti 25 5 5
							r[5] = 17;
							count += 2;
						}
					} while (r[3] == 0);
					// 26 addi 4 1 4
					r[1] = r[4];
					// 27 seti 17 2 5
					r[5] = 7;
					flag = true;
					count += 2;
				}
			} while (flag);
			// 28 eqrr 2 0 4

			if (stupid[(int) r[2]]) {
				System.out.println(last);
				r[0] = r[2];
				fff = true;
			} else if (fff) {
				System.out.println("lolwat");
			} else
				stupid[(int) r[2]] = true;
			last = r[2];

			if (r[2] == r[0])
				r[4] = 1;
			else
				r[4] = 0;

		} while (r[4] == 0);
	}
}