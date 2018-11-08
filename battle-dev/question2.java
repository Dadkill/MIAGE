
/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/

package com.isograd.exercise;

import java.util.*;

public class IsoContest {
	public static void main(String[] argv) throws Exception {

		Scanner sc = new Scanner(System.in);

		String l1 = sc.nextLine();
		String l2;
		int nbMot = Integer.valueOf(l1);
		int resValide = 0;
		String currentMot = null;
		String[] currentMotSplit;
		ArrayList<String> voyelle = new ArrayList<String>();
		voyelle.add("a");
		voyelle.add("e");
		voyelle.add("i");
		voyelle.add("o");
		voyelle.add("u");
		voyelle.add("y");

		ArrayList<String> memoire = new ArrayList<String>();

		for (int i = 0; i < nbMot; i++) {
			currentMot = sc.nextLine();

			if (currentMot.length() >= 5 && currentMot.length() <= 7) {
				char pLettre = currentMot.charAt(0);
				char dLettre = currentMot.charAt(1);
				int pIntLettre = (int) pLettre;
				int dIntLettre = (int) dLettre;
				if (dIntLettre - pIntLettre == 1) {
					String pStringLettre = currentMot.substring(currentMot.length() - 1, currentMot.length());
					if (voyelle.contains(pStringLettre)) {
						if (!memoire.contains(currentMot)) {
							memoire.add(currentMot);
							resValide++;
						}
					}
				}
			}
		}
		System.out.println(resValide);
	}
}