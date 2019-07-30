package sample.example.multithread;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class H2OSynthesisWithSemaphore {

	static class Hydrogen implements Runnable {
		private Semaphore hCount;
		private Semaphore mCount;
		public Hydrogen(Semaphore hCount, Semaphore mCount) {
			this.hCount = hCount;
			this.mCount = mCount;
		}
		public void run() {
			try {
//				System.out.println("Acquiring Hydrogen semaphore.");
				hCount.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("H");
			if(hCount.availablePermits()==0) {
//				System.out.println("Releasing Molecule semaphore.");
				mCount.release();
			}
		}
	}
	
	static class Oxygen implements Runnable {
		volatile private Semaphore hCount;
		private Semaphore mCount;
		public Oxygen(Semaphore hCount, Semaphore mCount) {
			this.hCount = hCount;
			this.mCount = mCount;
		}
		public void run() {
			try {
//				System.out.println("Acquiring Molecule semaphore for Oxygen.");
				mCount.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("O");
//			System.out.println("Releasing Molecule semaphore.");
//			System.out.println("Releasing Hydrogen semaphore.");
			hCount.release(2);
		}
	}

	public static void synthesize(String input) throws InterruptedException {
		input = input.toUpperCase();
		Semaphore hCount = new Semaphore(2);
		Semaphore mCount = new Semaphore(1);
		mCount.acquire();
		for(char c : input.toCharArray()) {
			if(c=='H') {
				new Thread(new Hydrogen(hCount, mCount)).start();;
			}
			if(c=='O') {
				new Thread(new Oxygen(hCount, mCount)).start();;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String input = sc.next();
			synthesize(input);
		}
	}
}
