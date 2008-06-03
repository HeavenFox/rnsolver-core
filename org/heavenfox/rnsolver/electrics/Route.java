package org.heavenfox.rnsolver.electrics;

public class Route {
	public int[] r = new int[200];
	public int last = 0;
	
	public Route() {
		
	}
	
	public Route(int node) {
		this.add(node);
	}
	
	public void add(int node) {
		this.r[this.last++] = node;
	}
	
	public boolean exist(int node) {
		for (int i=0;i<this.last;i++ ){
			if ( this.r[i] == node ){
				return true;
			}
		}
		return false;
	}
	
	public int getLast() {
		return this.r[this.last-1];
	}
	
	public Route clone() {
		Route r = new Route();
		for ( int i=0;i<this.last;i++ ) {
			r.r[i] = this.r[i];
		}
		r.last = this.last;
		return r;
	}
}
