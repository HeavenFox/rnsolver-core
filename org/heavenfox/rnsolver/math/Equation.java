package org.heavenfox.rnsolver.math;

public class Equation {
	public int unknownNum;
	
	private int[] a;
	
	private int constant;
	
	public Equation() {
		this.a = new int[100];
	}
	
	public int getTerm(int x) {
		return this.a[x];
	}
	
	public void setTerm(int a, int x) {
		this.a[x] = a;
		if ( x > this.unknownNum ) {
			this.unknownNum = x;
		}
	}
	
	public void setConstant(int constant) {
		this.constant = constant;
	}
	
	public int getConstant() {
		return this.constant;
	}
	
	public boolean equals(Equation e) {
		/*
		double ratio = e.getConstant() / this.getConstant();
		for ( int i = 0; i < 100;i++ ) {
			if ( this.getTerm(i) == 0 && e.getTerm(i) == 0 ) {
				continue;
			}
			// xor
			if ( (this.getTerm(i)==0)!=(e.getTerm(i)==0) ) {
				return false;
			}
			if ( e.getTerm(i)/this.getTerm(i) != ratio ) {
				return false;
			}
		}*/
		return false;
	}
	
	public void print() {
		String s = "";
		for ( int i=0;i<100;i++ ) {
			if ( this.getTerm(i) != 0 ){
				s+=(this.getTerm(i)>0?"+":"")+(new Integer(this.getTerm(i)).toString()+"*x["+i+"]");
			}
		}
		s+="=";
		s+=this.constant;
		System.out.println(s);
	}
}
