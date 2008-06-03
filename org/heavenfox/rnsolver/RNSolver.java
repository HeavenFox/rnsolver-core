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
		System.out.println("欢迎使用网络电阻等效电阻求解软件!");
		System.out.println("制作:东北师大附中初中部 初二十二班 祝靖斯");
		System.out.println("本作品是2008年科技创新大赛的参赛项目");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("请按照说明输入网络电阻的结构");
		ResistanceNetwork rn = read(reader);
		System.out.println("等效电阻R = "+rn.solve().toString()+" Ω");
		System.out.println("按任意键继续...");
		// Pause
		try {
			System.in.read();
		} catch(Exception e) {
			System.out.println("发生错误");
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
			System.out.println("出现错误!");
		} catch (Exception e) {
			System.out.println("请输入合法的整数");
		}
		
		return rn;
	}
}
