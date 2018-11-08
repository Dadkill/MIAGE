/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/

package com.isograd.exercise;

import java.util.*;

public class IsoContest {
public static void main( String[] argv ) throws Exception {
		
		Scanner sc = new Scanner(System.in);

        String l1 = sc.nextLine();
        float nombrePoint = Float.valueOf(l1);
        float valeurRech = nombrePoint/2;

        int ancienPoint = -1;
        int currentPoint = 0;

        String l2 = sc.nextLine();
        String[] l2Split = l2.split(" ");
        int res = 0;
        for (int i = 0; i < nombrePoint+1; i++) {
            if(ancienPoint == -1) {
                ancienPoint = Integer.valueOf(l2Split[0]);
            }else{
				currentPoint = Integer.valueOf(l2Split[i]);

				if(currentPoint == valeurRech && valeurRech == ancienPoint) {
					System.out.println("INF");
					res = -1;
					break;
				}else if(currentPoint >= valeurRech && ancienPoint < valeurRech || currentPoint <= valeurRech && ancienPoint > valeurRech) {
					res++;
				}
				ancienPoint=currentPoint;
            }
        }
        if(res != -1) {
			System.out.println(res);
	}
}