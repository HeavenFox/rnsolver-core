package org.heavenfox.rnsolver.math;

import java.util.Arrays;

/**
 * Unknown Assigner
 * 
 * @author HeavenFox
 */
public class Unknown {
	private static int counter = 0;
	public static int[][] unknowns = new int[100][100];
	
	public static void init() {
		Arrays.fill(unknowns, 100, 100, 0);
	}
	
	public static int assign(int a,int b) {
		if ( a > b ) {
			// Swap two numbers
			a = a+b;
			b = a-b;
			a = a-b;
		}
		
		// If already assigned
		if ( unknowns[a][b] != 0 )
		{
			return unknowns[a][b];
		}
		//System.out.println("ASSIGN "+a+" "+b+" "+(counter+1));
		// Otherwise, assign new
		return unknowns[a][b] = ++counter;
	}
}