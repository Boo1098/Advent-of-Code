package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day16 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day16.txt"));
		ArrayList<int[][]> in = new ArrayList<>();
		for (int i = 0; i < lines.size(); i += 4) {
			int[][] temp = new int[3][4];
			String one = lines.get(i);
			String[] two = lines.get(i + 1).split(" ");
			String three = lines.get(i + 2);
			for (int j = 0; j < 4; j++) {
				temp[0][j] = Integer.parseInt("" + one.charAt(j * 3 + 9));
			}
			for (int j = 0; j < 4; j++) {
				temp[1][j] = Integer.parseInt(two[j]);
			}
			for (int j = 0; j < 4; j++) {
				temp[2][j] = Integer.parseInt("" + three.charAt(j * 3 + 9));
			}
			in.add(temp);
		}
		int sum = 0;
		ArrayList<Integer>[] opModes = new ArrayList[16];
		for (int i = 0; i < opModes.length; i++) {
			opModes[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < in.size(); i++) {
			int[][] cur = in.get(i);
			int count = 0;
			if (check(addr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(addi(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(mulr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(muli(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(banr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(bani(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(borr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(bori(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(setr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(seti(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(gtir(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(gtri(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(gtrr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(eqir(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(eqri(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (check(eqrr(cur[0].clone(), cur[1]), cur[2]))
				count++;
			if (count >= 3)
				sum++;
			// System.out.println(count);
		}

		System.out.println(sum);

		for (int i = 0; i < in.size(); i++) {
			int[][] cur = in.get(i);
			ArrayList<Integer> works = new ArrayList<>();
			if (check(addr(cur[0].clone(), cur[1]), cur[2]))
				works.add(0);
			if (check(addi(cur[0].clone(), cur[1]), cur[2]))
				works.add(1);
			if (check(mulr(cur[0].clone(), cur[1]), cur[2]))
				works.add(2);
			if (check(muli(cur[0].clone(), cur[1]), cur[2]))
				works.add(3);
			if (check(banr(cur[0].clone(), cur[1]), cur[2]))
				works.add(4);
			if (check(bani(cur[0].clone(), cur[1]), cur[2]))
				works.add(5);
			if (check(borr(cur[0].clone(), cur[1]), cur[2]))
				works.add(6);
			if (check(bori(cur[0].clone(), cur[1]), cur[2]))
				works.add(7);
			if (check(setr(cur[0].clone(), cur[1]), cur[2]))
				works.add(8);
			if (check(seti(cur[0].clone(), cur[1]), cur[2]))
				works.add(9);
			if (check(gtir(cur[0].clone(), cur[1]), cur[2]))
				works.add(10);
			if (check(gtri(cur[0].clone(), cur[1]), cur[2]))
				works.add(11);
			if (check(gtrr(cur[0].clone(), cur[1]), cur[2]))
				works.add(12);
			if (check(eqir(cur[0].clone(), cur[1]), cur[2]))
				works.add(13);
			if (check(eqri(cur[0].clone(), cur[1]), cur[2]))
				works.add(14);
			if (check(eqrr(cur[0].clone(), cur[1]), cur[2]))
				works.add(15);
			opModes[cur[1][0]] = add(opModes[cur[1][0]], works);
		}
		HashMap<Integer, Integer> op = new HashMap<>();
		while (!check2(opModes)) {
			for (int i = 0; i < opModes.length; i++) {
				ArrayList<Integer> cur = opModes[i];
				if (cur.size() == 1) {
					op.put(i, cur.get(0));
					remove(cur.get(0), opModes);
				}
			}
		}

		int[] reg = new int[4];

		List<String> lines2 = Files.readAllLines(Paths.get("day16_2.txt"));
		ArrayList<int[]> s = new ArrayList<>();
		for (String d : lines2) {
			String[] temp = d.split(" ");
			int[] t = new int[4];
			t[0] = Integer.parseInt(temp[0]);
			t[1] = Integer.parseInt(temp[1]);
			t[2] = Integer.parseInt(temp[2]);
			t[3] = Integer.parseInt(temp[3]);
			s.add(t);
		}

		for (int i = 0; i < s.size(); i++) {
			int[] cur = s.get(i);
			if (op.get(cur[0]) == 0)
				reg = addr(reg, cur);
			if (op.get(cur[0]) == 1)
				reg = addi(reg, cur);
			if (op.get(cur[0]) == 2)
				reg = mulr(reg, cur);
			if (op.get(cur[0]) == 3)
				reg = muli(reg, cur);
			if (op.get(cur[0]) == 4)
				reg = banr(reg, cur);
			if (op.get(cur[0]) == 5)
				reg = bani(reg, cur);
			if (op.get(cur[0]) == 6)
				reg = borr(reg, cur);
			if (op.get(cur[0]) == 7)
				reg = bori(reg, cur);
			if (op.get(cur[0]) == 8)
				reg = setr(reg, cur);
			if (op.get(cur[0]) == 9)
				reg = seti(reg, cur);
			if (op.get(cur[0]) == 10)
				reg = gtir(reg, cur);
			if (op.get(cur[0]) == 11)
				reg = gtri(reg, cur);
			if (op.get(cur[0]) == 12)
				reg = gtrr(reg, cur);
			if (op.get(cur[0]) == 13)
				reg = eqir(reg, cur);
			if (op.get(cur[0]) == 14)
				reg = eqri(reg, cur);
			if (op.get(cur[0]) == 15)
				reg = eqrr(reg, cur);
		}
		System.out.println(reg[0]);
	}

	private static boolean check2(ArrayList<Integer>[] opModes) {
		for (ArrayList<Integer> i : opModes) {
			if (i.size() > 1) {
				return false;
			}
		}
		return true;
	}

	private static void remove(int k, ArrayList<Integer>[] opModes) {
		for (int i = 0; i < opModes.length; i++) {
			ArrayList<Integer> cur = opModes[i];
			for (int j = 0; j < cur.size(); j++) {
				if (cur.get(j) == k) {
					cur.remove(j);
					j--;
				}
			}
		}

	}

	private static ArrayList<Integer> add(ArrayList<Integer> a, ArrayList<Integer> works) {
		if (a.size() == 0) {
			a = works;
			return works;
		}
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < works.size(); j++) {
				if (a.get(i) == works.get(j)) {
					temp.add(a.get(i));
				}
			}
		}
		a = temp;
		return temp;
	}

	private static boolean check(int[] in, int[] is) {
		if (in == null) {
			return false;
		}
		for (int i = 0; i < in.length; i++) {
			if (in[i] != is[i])
				return false;
		}
		return true;
	}

	public static int[] addr(int[] reg, int[] com) {
		try {
			reg[com[3]] = reg[com[1]] + reg[com[2]];
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] addi(int[] reg, int[] com) {
		reg[com[3]] = reg[com[1]] + com[2];
		return reg;
	}

	public static int[] mulr(int[] reg, int[] com) {
		try {
			reg[com[3]] = reg[com[1]] * reg[com[2]];
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] muli(int[] reg, int[] com) {
		reg[com[3]] = reg[com[1]] * com[2];
		return reg;
	}

	public static int[] banr(int[] reg, int[] com) {
		try {
			reg[com[3]] = reg[com[1]] & reg[com[2]];
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] bani(int[] reg, int[] com) {
		reg[com[3]] = reg[com[1]] & com[2];
		return reg;
	}

	public static int[] borr(int[] reg, int[] com) {
		try {
			reg[com[3]] = reg[com[1]] | reg[com[2]];
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] bori(int[] reg, int[] com) {
		reg[com[3]] = reg[com[1]] | com[2];
		return reg;
	}

	public static int[] setr(int[] reg, int[] com) {
		try {
			reg[com[3]] = reg[com[1]];
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] seti(int[] reg, int[] com) {
		reg[com[3]] = com[1];
		return reg;
	}

	public static int[] gtir(int[] reg, int[] com) {
		try {
			if (com[1] > reg[com[2]])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] gtri(int[] reg, int[] com) {
		try {
			if (reg[com[1]] > com[2])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] gtrr(int[] reg, int[] com) {
		try {
			if (reg[com[1]] > reg[com[2]])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] eqir(int[] reg, int[] com) {
		try {
			if (com[1] == reg[com[2]])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] eqri(int[] reg, int[] com) {
		try {
			if (reg[com[1]] == com[2])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}

	public static int[] eqrr(int[] reg, int[] com) {
		try {
			if (reg[com[1]] == reg[com[2]])
				reg[com[3]] = 1;
			else
				reg[com[3]] = 0;
		} catch (Exception e) {
			return null;
		}
		return reg;
	}
}
