package org.heavenfox.rnsolver.electrics;

import org.heavenfox.rnsolver.math.*;
//import org.heavenfox.rnsolver.util.Queue;

import java.util.*;

public class ResistanceNetwork {
	/**
	 * In
	 */
	public int in;
	
	/**
	 * Out
	 */
	public int out;
	
	/**
	 * Voltage
	 */
	public int voltage;
	
	/**
	 * Resistance Network Graph
	 */
	public int[][] resistances = new int[100][100];
	
	/**
	 * Positive Direction
	 */
	public boolean[][] directions = new boolean[100][100];
	
	/**
	 * Node Count (=Max node ID +1)
	 */
	private int nodeCount;
	
	/**
	 * Degrees
	 */
	private int[] degree = new int[100];
	
	/**
	 * Constructor
	 */
	public ResistanceNetwork()	{
		for ( int i=0;i<100;i++ ) {
			for ( int j=0;j<100;j++ ){
				this.resistances[i][j] = -1;
			}
		}
		Arrays.fill(this.degree, 0);
		
		// Random voltage
		Random random = new Random();
		this.voltage = random.nextInt(50)+10;
	}
	
	/**
	 * Reduce the Network
	 */
	public void reduce() {
		// If a node's degree is less than 3
		for ( int i=0;i<this.nodeCount;i++ ) {
			// If this is a 
			if ( this.degree[i] == 1 ) {
				int current = i;
				while ( this.degree[current] == 1 ) {
					this.degree[current] = 0;
					for ( int j=0;j<this.nodeCount;j++ ) {
						if ( this.resistances[current][j] >= 0 ) {
							// This one is!
							this.resistances[current][j] = -1;
							current = j;
							break;
						}
					}
				}
			}
		}
		
		for ( int i=0;i<this.nodeCount;i++ ) {
			if ( this.degree[i] == 2 ) {
				for ( int j=0;j<this.nodeCount;j++ ) {
					int[] relatedNode = new int[2];
					int relatedNodeCount = 0;
					if ( this.resistances[i][j] >= 0 ) {
						relatedNode[relatedNodeCount++] = j;
					}
					this.resistances[relatedNode[0]][relatedNode[1]] = this.resistances[i][relatedNode[0]] + this.resistances[i][relatedNode[1]];
					this.degree[i] = 0;
					this.resistances[i][relatedNode[0]] = this.resistances[i][relatedNode[1]] = -1;
				}
			}
		}
	}
	
	public void addResistance(int a, int b, int r) {
		if ( this.resistances[a][b]<0 ){
			this.degree[a]++;
			this.degree[b]++;
		}
		this.resistances[a][b] = this.resistances[b][a] = r;
		this.nodeCount = Math.max(Math.max(a+1, b+1), this.nodeCount);
	}
	
	public Fraction solve() {
		EquationSet equSet = new EquationSet();
		
		//----------------------------------
		// Mark Positive Direction
		//----------------------------------
		LinkedList<Resistance> BFSQ = new LinkedList<Resistance>();
		boolean[][] BFSVisited = new boolean[100][100];
		
		for ( int i=0;i<this.nodeCount;i++ ) {
			if ( this.resistances[this.in][i] >= 0 ) {
				BFSQ.addFirst(new Resistance(this.in,i));
			}
		}
		
		while (!BFSQ.isEmpty()) {
			Resistance R = BFSQ.removeLast();
			this.directions[R.a][R.b] = true;
			BFSVisited[R.a][R.b] = BFSVisited[R.b][R.a] = true;
			for ( int i=0;i<this.nodeCount;i++ ) {
				if ( this.resistances[R.b][i]>=0 && !BFSVisited[R.b][i] ) {
					BFSVisited[R.b][i] = BFSVisited[i][R.b] = true;
					BFSQ.addFirst(new Resistance(R.b,i));
				}
			}
		}
		
		// DEBUG
		/*
		System.out.println("---------");
		for ( int i=0;i<100;i++ ){
			for ( int j=0;j<100;j++ ){
				if ( this.resistances[i][j]>=0 && this.directions[i][j] ){
					System.out.println(i+" "+j);
				}
			}
		}
		System.out.println("---------");
		*/
		// END DEBUG
		
		//----------------------------------
		// KCL
		//----------------------------------
		for ( int i=0;i<this.nodeCount;i++ ) {
			if ( this.degree[i] != 0 ) {
				// Add equation of node i
				Equation cE = new Equation();
				for ( int j=0;j<this.nodeCount;j++ ) {
					if ( this.resistances[i][j] >= 0 ) {
						// Check. is the stream from i or to i?
						if ( this.directions[j][i] ) {
							// To i.
							cE.setTerm(1, Unknown.assign(i,j));
						} else {
							// From i.
							cE.setTerm(-1, Unknown.assign(i,j));
						}
					}
				}
				
				if ( i == this.in ) {
					cE.setTerm(1,0);
				}
				
				if ( i == this.out ) {
					cE.setTerm(-1, 0);
				}
				//cE.print();
				
				equSet.add(cE);
			}
		}
		
		//----------------------------------
		// KVL
		//----------------------------------
		this.KVL(equSet, new Route(this.in));
		
		//----------------------------------
		// Solve
		//----------------------------------
		equSet.solve();
		
		// R=U/I
		Fraction ans = equSet.roots[0].clone();
		ans.inv();
		ans.times(this.voltage);
		return ans;
	}
	
	// DO KVL
	private void KVL(EquationSet equSet, Route currentRoute) {
		int lastNode = currentRoute.getLast();
		if ( lastNode == this.out ) {
			Equation equ = new Equation();
			for ( int i=0;i<currentRoute.last-1;i++ ) {
				int from = currentRoute.r[i], to = currentRoute.r[i+1];
				int a = (this.directions[from][to] ? 1 : -1)*this.resistances[from][to];
				equ.setTerm(a, Unknown.assign(from, to));
			}
			equ.setConstant(this.voltage);
			equSet.add(equ);
			//equ.print();
		} else {
			for ( int i=0;i<this.nodeCount;i++ ) {
				if ( this.resistances[lastNode][i]>=0 && !currentRoute.exist(i) ) {
					Route newRoute = currentRoute.clone();
					newRoute.add(i);
					this.KVL(equSet, newRoute);
				}
			}
		}
	}
}