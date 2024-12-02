package com.eulersboiler.advent2018.day09;

import java.io.IOException;

public class Day19 {
	public static void main(String[] args) throws IOException {
		long[] r = new long[6];
		r[0] = 1;
		r[5] = r[5] + 2;

		r[5] = r[5] * r[5];

		r[5] = 19 * r[5];

		r[5] = r[5] * 11;

		r[1] = r[1] + 6;

		r[1] = r[1] * 22;

		r[1] = r[1] + 13;

		r[5] = r[5] + r[1];

		if (r[0] == 1) {
			r[1] = 27;

			r[1] = r[1] * 28;

			r[1] = 29 + r[1];

			r[1] = r[1] * 30;

			r[1] = r[1] * 14;

			r[1] = r[1] * 32;

			r[5] = r[5] + r[1];

			r[0] = 0;

		}
		r[4] = 1;
		do {

//			r[2] = 1;
//
//			do {
//				r[1] = r[4] * r[2];
//
//				if (r[1] == r[5])
//					r[1] = 1;
//				else
//					r[1] = 0;
//
//				if (r[1] == 1)
//					r[0] += r[4];
//
//				r[2] = r[2] + 1;
//
//				if (r[2] > r[5])
//					r[1] = 1;
//				else
//					r[1] = 0;
//
//			} while (r[1] == 0);
//
//			r[4] = r[4] + 1;
//
//			if (r[4] > r[5])
//				r[1] = 1;
//			else
//				r[1] = 0;
			
			for(int i = 1; i<=r[5]; i++) {
				if(r[5]%i==0)
					r[0]+=i;
			}
			r[1] = 1;

		} while (r[1] == 0);

		System.out.println(r[0]);
		/* start */

	}
}
