import java.util.Scanner;

public class a_loot_sharing {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String b = sc.nextLine();
		String t = sc.nextLine();
		String n = sc.nextLine();
		
		int bInt = Integer.valueOf(b);
		int tInt = Integer.valueOf(t);
		int nInt = Integer.valueOf(n);
		
		int bNeed = bInt*2;
		int tNeed = tInt*3;
		int need = bNeed+tNeed;
		
		if(need <= nInt) {
			System.out.println("LOOT!");
		}else {
			System.out.println("RHUM!");
		}
	}
}
