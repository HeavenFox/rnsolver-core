package org.heavenfox.rnsolver.math;

public class Matrix {
	public Fraction[][] M;
	public int w;
	public int h;
	public Matrix(int w,int h) {
		this.w = w;
		this.h = h;
		this.M = new Fraction[h][w];
	}
	
	public void swapRow(int a,int b) {
		for ( int i=0;i<this.w;i++ ) {
			Fraction x;
			x = this.M[b][i];
			this.M[b][i] = this.M[a][i];
			this.M[a][i] = x;
		}
	}
	
	public void rowMultiple(int r,Fraction n) {
		for ( int i=0;i<this.w;i++ ) {
			this.M[r][i].times(n);
		}
	}
	
	public void rowDivide(int r,Fraction n) {
		if ( n.a == 0 ) {
			System.out.println("Divider is zero");
		}
		for ( int i=0;i<this.w;i++ ) {
			this.M[r][i].div(n);
		}
	}
	
	public boolean rowIsZero(int r) {
		for ( int i=0;i<this.w;i++ ) {
			if ( this.M[r][i].a != 0 ) {
				return false;
			}
		}
		return true;
	}
	
	public void removeRow(int r) {
		for ( int i=r;i<this.h-1;i++ ) {
			for ( int j=0;j<this.w;j++ ) {
				this.M[i][j] = this.M[i+1][j];
			}
		}
		this.h--;
	}
	
	/**
	 * Add row a to b
	 * 
	 * @param a
	 * @param b
	 */
	public void rowAdd(int a,int b) {
		for ( int i=0;i<this.w;i++ ) {
			this.M[b][i].add(this.M[a][i]);
		}
	}
	
	/**
	 * Print this matrix as Mathematica style
	 */
	public void print()
	{
		for ( int i=0;i<this.h;i++ )
		{
			if ( i != 0 )
			{
				System.out.println(",");
			}
			System.out.print("{");
			for ( int j=0;j<this.w;j++ )
			{
				if ( j != 0 )
				{
					System.out.print(",");
				}
				System.out.print(this.M[i][j]);
			}
			System.out.print("}");
		}
	}
}
