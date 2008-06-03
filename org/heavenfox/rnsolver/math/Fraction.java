package org.heavenfox.rnsolver.math;

public class Fraction implements Cloneable {
	public int a,b;
	
	public Fraction() {
		this.a = 0;
		this.b = 1;
	}
	
	public Fraction(int n) {
		this.a = n;
		this.b = 1;
	}
	
	public Fraction(int a,int b) {
		this.a = a;
		this.b = b;
	}
	
	public void add(int n) {
		this.a += n*this.b;
		this.reduce();
	}
	
	public void add(Fraction f) {
		if ( f.isInfinite() || this.isInfinite() )
		{
			this.a = 1;
			this.b = 0;
			return;
		}
		if ( this.isNaN() || f.isNaN() )
		{
			this.a = this.b = 0;
			return;
		}
		int common = lcm(this.b,f.b);
		int newA = this.a * (common/this.b) + f.a * (common/f.b);
		this.a = newA;
		this.b = common;
		this.reduce();
	}
	
	public void times(int n) {
		this.a *= n;
		this.reduce();
	}
	
	public void times(Fraction f) {
		this.a *= f.a;
		this.b *= f.b;
		this.reduce();
	}
	
	public void div(int n) {
		this.b *= n;
		this.reduce();
	}
	
	public void div(Fraction f) {
		this.a *= f.b;
		this.b *= f.a;
		this.reduce();
	}
	
	public void reduce() {
		if ( this.b == 0 ) {
			return;
		}
		// Act Zero as 0/1
		if ( this.a == 0 ) {
			this.b = 1;
		}
		boolean sign = (this.a>0)!=(this.b>0);
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		int divider = gcd(this.a,this.b);
		this.a/=divider;
		this.b/=divider;
		this.a *= (sign?-1:1);
	}
	
	
	public double dec() {
		return (double)this.a/(double)this.b;
	}
	
	public void inv() {
		int x;
		x = this.a;
		this.a = this.b;
		this.b = x;
		this.reduce();
	}
	
	public Fraction clone() {
		this.reduce();
		Fraction f = new Fraction(this.a,this.b);
		return f;
	}
	
	
	public String toString() {
		if ( this.a == 0 && this.b == 0 ) {
			return "NaN";
		}
		if ( this.a != 0 && this.b == 0 ) {
			return "Infinite";
		}
		
		if ( this.b == 1 ) {
			return new Integer(this.a).toString();
		}
		return this.a + "/" + this.b;
	}
	
	public boolean isNaN()
	{
		return (this.a == 0)&&(this.b == 0);
	}
	
	public boolean isInfinite()
	{
		return (this.b == 0)&&(this.a != 0);
	}
	
	private static int gcd(int a,int b) {
		if ( a < b ) {
			int x=a;
			a=b;
			b=x;
		}
		while ( b>0 ) {
			int x = a;
			a = b;
			b = x % b;
		}
		return a;
	}
	
	private static int lcm(int a,int b) {
		return a*b/gcd(a,b);
	}
}
