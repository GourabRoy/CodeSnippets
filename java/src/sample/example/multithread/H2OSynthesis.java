package sample.example.multithread;

import java.util.*;

public class H2OSynthesis {

	static class HCount {
		private int count=0;
		
		public void add() {count++;}
		
		public void consume(int x) {count-=x;}
		
		public int get() {return count;}
	}
	
	static class Hydrogen implements Runnable {
		private HCount hCount;
		public Hydrogen(HCount hCount) {this.hCount = hCount;}
		public void run() {
			synchronized(hCount) {
				while(hCount.get()>1) {
					try {
						hCount.wait();
						Thread.sleep(new Double(1000*Math.random()).longValue());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.print("H");
				hCount.add();
				hCount.notifyAll();
			}
		}
	}
	
	static class Oxygen implements Runnable {
		private HCount hCount;
		public Oxygen(HCount hCount) {this.hCount = hCount;}
		public void run() {
			synchronized(hCount) {
				while(hCount.get()<2) {
					try {
						hCount.wait();
						Thread.sleep(new Double(1000*Math.random()).longValue());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.print("O");
				hCount.consume(2);
				hCount.notifyAll();
			}
		}
	}

	public static void synthesize(String input) {
		input = input.toUpperCase();
		HCount hCount = new HCount();
		for(char c : input.toCharArray()) {
			if(c=='H') {
				new Thread(new Hydrogen(hCount)).start();;
			}
			if(c=='O') {
				new Thread(new Oxygen(hCount)).start();;
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String input = sc.next();
			synthesize(input);
		}
	}
}
