import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class b_the_parrots_of_tortuga {
	public static int achatPeroquet(int gold, int prixPeroquet, int taxe) {
		return (gold - taxe) / prixPeroquet;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String l1 = sc.nextLine();
		String[] demande = l1.split(" ");

		int gold = Integer.valueOf(demande[0]);
		int nbVendeur = Integer.valueOf(demande[1]);

		String currentVendeur;
		String[] currentVendeurSplit;

		String nomVendeur, nomVendeurOpti = null;
		int taxe, prix;
		int taxeOpti, prixOpti;
		int peroquetNbOpti = 0;
		int achatOpti;

		for (int i = 0; i < nbVendeur; i++) {
			currentVendeur = sc.nextLine();
			currentVendeurSplit = currentVendeur.split(" ");

			nomVendeur = currentVendeurSplit[0];
			prix = Integer.valueOf(currentVendeurSplit[1]);
			taxe = Integer.valueOf(currentVendeurSplit[2]);
			int currentNbPerroquet = achatPeroquet(gold, prix, taxe);

			if (currentNbPerroquet > peroquetNbOpti) {
				peroquetNbOpti = currentNbPerroquet;
				taxeOpti = taxe;
				prixOpti = prix;
				nomVendeurOpti = nomVendeur;
			}
		}
		if (nomVendeurOpti == null) {
			System.out.println("Impossible");
		} else {
			System.out.println(peroquetNbOpti);
			System.out.println(nomVendeurOpti);
		}
	}
}
