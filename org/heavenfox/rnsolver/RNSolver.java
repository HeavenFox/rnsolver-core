package org.heavenfox.rnsolver;

import java.io.*;
import org.heavenfox.rnsolver.electrics.*;

public class RNSolver {

	/**
	 * Main Entry Point of Resistance Network Solver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("��ӭʹ����������Ч����������!");
		System.out.println("����:����ʦ���г��в� ����ʮ���� ף��˹");
		System.out.println("����Ʒ��2008��Ƽ����´����Ĳ�����Ŀ");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("�밴��˵�������������Ľṹ");
		ResistanceNetwork rn = read(reader);
		System.out.println("��Ч����R = "+rn.solve().toString()+" ��");
		System.out.println("�����������...");
		// Pause
		try {
			System.in.read();
		} catch(Exception e) {
			System.out.println("��������");
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
			System.out.println("���ִ���!");
		} catch (Exception e) {
			System.out.println("������Ϸ�������");
		}
		
		return rn;
	}
}
