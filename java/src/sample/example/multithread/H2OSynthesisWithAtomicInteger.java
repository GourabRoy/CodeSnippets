package sample.example.multithread;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class H2OSynthesisWithAtomicInteger {

	static class Hydrogen implements Runnable {
		volatile private AtomicInteger hCount;
		public Hydrogen(AtomicInteger hCount) {this.hCount = hCount;}
		public void run() {
			while(hCount.get()>1) {
				try {
					synchronized(hCount) {
						hCount.wait();
					}
					Thread.sleep(new Double(1000*Math.random()).longValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.print("H");
			synchronized(hCount) {
				hCount.incrementAndGet();
				hCount.notifyAll();
			}
		}
	}
	
	static class Oxygen implements Runnable {
		volatile private AtomicInteger hCount;
		public Oxygen(AtomicInteger hCount) {this.hCount = hCount;}
		public void run() {
			while(hCount.get()<2) {
				try {
					synchronized(hCount) {
						hCount.wait();
					}
					Thread.sleep(new Double(1000*Math.random()).longValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.print("O");
			synchronized(hCount) {
				hCount.decrementAndGet();
				hCount.decrementAndGet();
				hCount.notifyAll();
			}
		}
	}

	public static void synthesize(String input) {
		input = input.toUpperCase();
		AtomicInteger hCount = new AtomicInteger();
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
