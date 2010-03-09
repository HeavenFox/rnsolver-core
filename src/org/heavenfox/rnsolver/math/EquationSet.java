package org.heavenfox.rnsolver.math;

public class EquationSet {
	public Equation[] equations;
	public int equationNum;
	public int unknownNum;
	public Fraction[] roots;
	
	public EquationSet() {
		this.equationNum = 0;
		this.roots = new Fraction[100];
		this.equations = new Equation[10000];
	}
	
	public void add(Equation e) {
		for ( int i=0;i<this.equationNum;i++ ) {
			if ( this.equations[i].equals(e) )
			{
				return;
			}
		}
		this.equations[this.equationNum++] = e;
	}
	
	public boolean solvable() {
		// Re-assign unknowns
		boolean[] unknownExist = new boolean[100];
		for ( int i=0;i<this.equationNum;i++ ) {
			for ( int j=0;j<100;j++ ) {
				if ( this.equations[i].getTerm(j) != 0 ) {
					unknownExist[j] = true;
				}
			}
		}
		int unknownCount = 0;
		for ( int i=0;i<100;i++ ) {
			if ( unknownExist[i] ) {
				unknownCount++;
			}
		}
		
		// Check if no solution
		if ( unknownCount > this.equationNum ) {
			return false;
		}
		return true;
	}
	
	public void solve()
	{
		// Check if no solution
		//if ( !this.solvable() ) {
		//	throw new Exception("TOO MANY UNKNOWN!");
		//}
		// Re-assign unknowns
		boolean[] unknownExist = new boolean[100];
		int[] unknownMap = new int[101];
		for ( int i=0;i<this.equationNum;i++ ) {
			for ( int j=0;j<100;j++ ) {
				if ( this.equations[i].getTerm(j) != 0 ) {
					unknownExist[j] = true;
				}
			}
		}
		int currentUnknown = 0;
		for ( int i=0;i<100;i++ ) {
			if ( unknownExist[i] ) {
				unknownMap[currentUnknown++] = i; 
			}
		}
		
		//-------------------------------------------
		// Solve Equation
		//-------------------------------------------
		// Generate matrix
		Matrix A = new Matrix(currentUnknown,this.equationNum);
		Matrix B = new Matrix(1,this.equationNum);
		
		for ( int i=0;i<this.equationNum;i++ ) {
			for ( int j=0;j<currentUnknown;j++ ) {
				A.M[i][j] = new Fraction(this.equations[i].getTerm(unknownMap[j]));
			}
			B.M[i][0] = new Fraction(this.equations[i].getConstant());
		}
		B.print();
		// Gauss
		// First, reduce
		for ( int i=0;i<currentUnknown;i++ ) {
			// Find max one as primary element
			int maxRow = i;
			double maxValue = 0;
			for ( int j=i;j<this.equationNum;j++ ) {
				if ( Math.abs(A.M[j][i].dec()) > maxValue ) {
					maxValue = Math.abs(A.M[j][i].dec());
					maxRow = j;
				}
			}
			if ( maxRow != i ) {
				A.swapRow(i, maxRow);
				B.swapRow(i, maxRow);
			}
			Fraction divider = A.M[i][i].clone();
			if ( divider.a != 0 ) {
				A.rowDivide(i, divider);
				B.rowDivide(i, divider);
				for ( int j=i+1;j<this.equationNum;j++ ) {
					divider = Fractions.opp(A.M[j][i]);
					if ( divider.a != 0 && divider.b != 0 ) {
						A.rowDivide(j, divider);
						B.rowDivide(j, divider);
						A.rowAdd(i, j);
						B.rowAdd(i, j);
					}
				}
			}
		}
		// Remove all zero lines
		for ( int i=A.h-1;i>=0;i-- ) {
			if ( A.rowIsZero(i) ) {
				A.removeRow(i);
				B.removeRow(i);
			}
		}
		// Get back
		for ( int i=currentUnknown-1;i>=0;i-- ) {
			// OK! Now x[i] = B[i][0]
			this.roots[unknownMap[i]] = B.M[i][0].clone();
			for ( int j=i-1;j>=0;j-- ) {
				B.M[j][0] = Fractions.sub(B.M[j][0],Fractions.times(A.M[j][i],B.M[i][0]));
				A.M[j][i].a = 0;
			}
		}
	}
}
