package org.heavenfox.rnsolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.heavenfox.rnsolver.electrics.ResistanceNetwork;

public class RNSolver {

	/**
	 * Main Entry Point of Resistance Network Solver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to RNSolver. Starting service...");
		Socket incoming = null;
		
		ServerSocket server = null;
		
		BufferedReader in = null;
		PrintWriter out = null;
		ResistanceNetwork rn = null;
		try {
			server = new ServerSocket(5428);
			
			incoming = server.accept();
			
			in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			out = new PrintWriter(incoming.getOutputStream(),true);
			
			System.out.println("Service started.");
			
			rn = read(in);
		} catch (Exception e) {
			// Unknown Host
			System.out.println("Sorry, unexpected condition occurs");
		}
		
		try {
			out.println(rn.solve().toString());
			
			System.out.println("Done!");
			
			incoming.close();
			server.close();
			out.close();
			in.close();
		} catch(Exception e) {
			System.out.println("Error occurs");
		}
	}
	
	/**
	 * Read in Resistance Network
	 * 
	 * @param reader Reader object
	 * @return Resistance network
	 */
	public static ResistanceNetwork read(BufferedReader reader) {
		ResistanceNetwork rn = new ResistanceNetwork();
		// Number
		String s;
		
		try {
			int number = 0;
			s = reader.readLine();
			number = Integer.parseInt(s);
			
			int in,out;
			s = reader.readLine();
			in = Integer.parseInt(s.split(" ")[0]);
			out = Integer.parseInt(s.split(" ")[1]);
			
			rn.in = in;
			rn.out = out;
			for ( int i=0;i<number;i++ ) {
				s = reader.readLine();
				int a,b,r;
				a = Integer.parseInt(s.split(" ")[0]);
				b = Integer.parseInt(s.split(" ")[1]);
				r = Integer.parseInt(s.split(" ")[2]);
				
				rn.addResistance(a, b, r);
			}
		} catch (IOException e) {
			System.out.println("Error occurs!");
		} catch (Exception e) {
			System.out.println("Communication error.");
		}
		
		return rn;
	}
}
