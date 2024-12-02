package com.eulersboiler.advent2018.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day24 {
	static int boost;

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("day24.txt"));
		boost = -1;
		int sum = 0;
		boolean flag = true;
		while (flag) {
			flag = false;
			boost++;
			// if (boost == 32)
			// System.out.println();
			ArrayList<Group> groups = new ArrayList<>();
			for (int i = 1; i <= 10; i++) {
				String[] s = lines.get(i).replaceAll(" \\(.*\\)", "").split(" ");
				// System.out.println(lines.get(i).replaceAll(" \\(.*\\)", "").split(" ")[12]);
				int type = 0;
				int units = Integer.parseInt(s[0]);
				int hp = Integer.parseInt(s[4]);
				int attack = Integer.parseInt(s[12]) + boost;
				int intiative = Integer.parseInt(s[17]);
				String dType = s[13];
				ArrayList<String> weak = new ArrayList<>();
				ArrayList<String> immune = new ArrayList<>();
				if (lines.get(i).contains("(")) {
					String d = lines.get(i);
					d = d.replaceAll(".*\\(", "").replaceAll("\\).*", "");
					// System.out.println(d);
					String[] f = d.split(";");
					for (String j : f) {
						String[] k = j.replaceAll(", ", " ").replaceAll("^ ", "").split(" ");
						if (k[0].equals("weak"))
							for (int l = 2; l < k.length; l++)
								weak.add(k[l]);
						if (k[0].equals("immune"))
							for (int l = 2; l < k.length; l++)
								immune.add(k[l]);
					}
				}
				groups.add(new Group(type, units, hp, attack, intiative, dType, weak, immune));
			}
			for (int i = 13; i < lines.size(); i++) {
				String[] s = lines.get(i).replaceAll(" \\(.*\\)", "").split(" ");
				// System.out.println(lines.get(i).replaceAll(" \\(.*\\)", "").split(" ")[12]);
				int type = 1;
				int units = Integer.parseInt(s[0]);
				int hp = Integer.parseInt(s[4]);
				int attack = Integer.parseInt(s[12]);
				int intiative = Integer.parseInt(s[17]);
				String dType = s[13];
				ArrayList<String> weak = new ArrayList<>();
				ArrayList<String> immune = new ArrayList<>();
				if (lines.get(i).contains("(")) {
					String d = lines.get(i);
					d = d.replaceAll(".*\\(", "").replaceAll("\\).*", "");
					// System.out.println(d);
					String[] f = d.split(";");
					for (String j : f) {
						String[] k = j.replaceAll(", ", " ").replaceAll("^ ", "").split(" ");
						if (k[0].equals("weak"))
							for (int l = 2; l < k.length; l++)
								weak.add(k[l]);
						if (k[0].equals("immune"))
							for (int l = 2; l < k.length; l++)
								immune.add(k[l]);
					}
				}
				groups.add(new Group(type, units, hp, attack, intiative, dType, weak, immune));
			}
			int time = 0;
			while (check(groups) && time<100000) {
				time++;
				ArrayList<Group> temp = new ArrayList<>();
				while (groups.size() > 0) {
					int maxI = 0;
					long maxC = 0;
					for (int i = 0; i < groups.size(); i++) {
						if (groups.get(i).compareTarget() > maxC) {
							maxC = groups.get(i).compareTarget();
							maxI = i;
						}
					}
					temp.add(groups.remove(maxI));
				}
				groups = temp;
				ArrayList<Group> targets = new ArrayList<>();
				for (int i = 0; i < groups.size(); i++) {
					Group cur = groups.get(i);
					int look = cur.other();
					Group best = null;
					long mostD = 0;
					for (int j = 0; j < groups.size(); j++) {
						if (groups.get(j).getType() == look) {
							if (cur.wouldDamage(groups.get(j)) > mostD && !targeted(groups.get(j), targets)) {
								mostD = cur.wouldDamage(groups.get(j));
								best = groups.get(j);
							}
						}
					}
					cur.setTarget(best);
					if (best != null)
						targets.add(best);
				}
				temp = new ArrayList<>();
				while (groups.size() > 0) {
					int maxI = 0;
					int maxC = 0;
					for (int i = 0; i < groups.size(); i++) {
						if (groups.get(i).getInitiative() > maxC) {
							maxC = groups.get(i).getInitiative();
							maxI = i;
						}
					}
					temp.add(groups.remove(maxI));
				}
				groups = temp;
				for (Group g : groups) {
					if (g.getU() > 0) {
						g.attack();
					}

				}
				for (int i = 0; i < groups.size(); i++) {
					if (groups.get(i).getU() <= 0) {
						groups.remove(i);
						i--;
					}
				}
			}
			sum = 0;
			boolean fff = false;
			for (Group g : groups) {
				sum += g.getU();
				if (g.getType() == 0 && !check(groups))
					fff = true;
			}
			// System.out.println();
			flag = !fff;
			// System.out.println(sum);
			if (boost == 0)
				System.out.println(sum);
			// System.out.println(boost);
			// System.out.println();
		}
		System.out.println(sum);
		//System.out.println(boost);
	}

	private static boolean targeted(Group group, ArrayList<Group> targets) {
		for (Group g : targets)
			if (g == group)
				return true;
		return false;
	}

	private static boolean check(ArrayList<Group> groups) {
		boolean im = false;
		boolean in = false;
		for (Group g : groups) {
			if (g.getType() == 0)
				im = true;
			if (g.getType() == 1)
				in = true;
		}
		return im && in;
	}

	private static class Group {
		int type = 0;
		int units, hp, attack, initiative;
		String dType;
		ArrayList<String> weak, immune;
		Group targeted;

		public Group(int type2, int units2, int hp2, int attack2, int intiative2, String dType2,
				ArrayList<String> weak2, ArrayList<String> immune2) {
			type = type2;
			units = units2;
			hp = hp2;
			attack = attack2;
			initiative = intiative2;
			dType = dType2;
			weak = weak2;
			immune = immune2;
		}

		public int getU() {
			// TODO Auto-generated method stub
			return units;
		}

		public void attack() {
			// TODO Auto-generated method stub
			if (targeted != null) {
				long damage = 0;

				for (String s : targeted.getImmune()) {
					if (s.equals(dType))
						damage = 0;
				}
				for (String s : targeted.getWeak()) {
					if (s.equals(dType))
						damage = (long) units * attack * 2;
				}
				if (damage == 0)
					damage = (long) units * attack;
				targeted.damage(damage);
			}
		}

		private void damage(long damage) {
			// TODO Auto-generated method stub
			// System.out.println(damage / hp);
			units -= damage / hp;
		}

		public void setTarget(Group best) {
			targeted = best;

		}

		public long wouldDamage(Group group) {
			for (String s : group.getImmune()) {
				if (s.equals(dType))
					return 0;
			}
			long big = ((long) Math.pow(10, 5)) * 1000 * 10000;
			for (String s : group.getWeak()) {
				if (s.equals(dType))
					return units * (long) attack * 2 * big + group.getU() * group.getA();
			}
			// if (boost > 24)
			// System.out.println(units * (long) attack * big + group.getU() *
			// group.getA());
			return units * (long) attack * big + group.getU() * group.getA();
		}

		private int getA() {
			// TODO Auto-generated method stub
			return attack;
		}

		private int getInitiative() {
			// TODO Auto-generated method stub
			return initiative;
		}

		private ArrayList<String> getImmune() {
			// TODO Auto-generated method stub
			return immune;
		}

		private ArrayList<String> getWeak() {
			// TODO Auto-generated method stub
			return weak;
		}

		public int getType() {
			// TODO Auto-generated method stub
			return type;
		}

		public int other() {
			if (type == 0)
				return 1;
			return 0;
		}

		public long compareTarget() {

			return units * (long) attack * 100000 + initiative;
		}

		public String toString() {
			return type + ": " + " att," + attack + " init," + initiative + " u," + units+" hp,"+hp;
		}
	}
}