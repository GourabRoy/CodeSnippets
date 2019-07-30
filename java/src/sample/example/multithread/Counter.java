package sample.example.multithread;

import java.util.concurrent.atomic.AtomicInteger;

class Counter {
	public AtomicInteger count = new AtomicInteger(0);
	private int count1 = 0;
	private static int countI = 0;
	
	public int increment() {
		return countI++;
	}
	
	public static synchronized void print() {
		System.out.println(countI + " ");
	}
	
	public int get() {
		return count1;
	}

	public void add() {
		count1++;
	}
}

