
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
		String l2 = sc.nextLine();

		int nbAcheteur = Integer.valueOf(l1);
		int prixReserve = Integer.valueOf(l2);

		String currentVendeur;
		String[] currentVendeurSplit;

		String nomVendeur, nomVendeurOpti = null;
		int currentPrix;
		int prixOpti = 0;

		for (int i = 0; i < nbAcheteur; i++) {
			currentVendeur = sc.nextLine();
			currentVendeurSplit = currentVendeur.split(" ");

			nomVendeur = currentVendeurSplit[1];
			currentPrix = Integer.valueOf(currentVendeurSplit[0]);

			if (currentPrix > prixOpti && currentPrix > prixReserve) {
				prixOpti = currentPrix;
				nomVendeurOpti = nomVendeur;
			}
		}
		if (nomVendeurOpti == null) {
			System.out.println("KO");
		} else {
			System.out.println(nomVendeurOpti);
		}
	}
}