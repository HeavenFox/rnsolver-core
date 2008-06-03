package org.heavenfox.rnsolver.math;

public class Fractions {
	public static Fraction opp(Fraction a) {
		Fraction b = a.clone();
		b.a = -b.a;
		b.reduce();
		return b;
	}
	
	public static Fraction add(Fraction a, Fraction b) {
		Fraction c = a.clone();
		c.add(b);
		return c;
	}
	
	public static Fraction sub(Fraction a, Fraction b) {
		Fraction c = a.clone();
		c.add(opp(b));
		return c;
	}
	
	public static Fraction div(Fraction a, Fraction b) {
		Fraction c = a.clone();
		c.div(b);
		return c;
	}
	
	
	
	public static Fraction times(Fraction a, Fraction b) {
		Fraction c = a.clone();
		c.times(b);
		return c;
	}
	
	public static Fraction inv(Fraction a) {
		Fraction c = a.clone();
		c.inv();
		return a;
	}
}
