import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class c_sea_storm {

	public static void main(String[] args) {
		int[] taillePont;
		int nbPirates;
		int[] position;
		int[] tailleChapeaux;

		Scanner sc = new Scanner(System.in);

		String l1 = sc.nextLine();
		String[] demande1 = l1.split(" ");

		taillePont = new int[Integer.valueOf(demande1[0])];
		nbPirates = Integer.valueOf(demande1[1]);
		position = new int[Integer.valueOf(demande1[1])];
		tailleChapeaux = new int[Integer.valueOf(demande1[1])];

		String l2 = sc.nextLine();
		String[] demande2 = l2.split(" ");

		for (int i = 0; i < nbPirates; i++) {
			tailleChapeaux[i] = Integer.valueOf(demande2[i]);
		}

		String l3 = sc.nextLine();
		String[] demande3 = l3.split(" ");

		for (int j = 0; j < nbPirates; j++) {
			position[j] = Integer.valueOf(demande3[j]);
		}

		Set<Integer> s = new HashSet<Integer>();
		int i = 0;
		for (i = 0; i < nbPirates; i++) {

			s.add(position[i]);

		}
		permutation(s, new Stack<Integer>(), s.size());

		// for(i=0; i<)
		int j = 0;
		int k = 0;

		for (i = 0; i < nbPirates; i++) {
			taillePont[position[i]] = 1;
			for (j = 0; j < tailleChapeaux[i]; j++) {
				if (taillePont[position[i + j]] <= taillePont.length) {
					taillePont[position[i + j]] = 1;
				}
				if (taillePont[position[i - j]] > 0) {
					taillePont[position[i - j]] = 1;
				}
			}
		}
		for (k = 0; k <= taillePont.length; k++) {

		}
	}

	public static void permutation(Set<Integer> items, Stack<Integer> permutation, int size) {
		if (permutation.size() == size) {
			// System.out.println(Arrays.toString(permutation.toArray(new Integer[0])));

		}

		Integer[] availableItems = items.toArray(new Integer[0]);
		for (Integer i : availableItems) {
			permutation.push(i);
			items.remove(i);
			permutation(items, permutation, size);
			items.add(permutation.pop());
		}
	}
}
