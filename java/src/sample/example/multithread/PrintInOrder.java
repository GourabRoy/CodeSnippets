package sample.example.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintInOrder {

	static class PrintFirst implements Runnable {
		volatile private AtomicInteger i;
		public PrintFirst(AtomicInteger i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 0){System.out.println("Waiting for one.");}
			System.out.println("one");
			i.getAndIncrement();
		}
	}
	
	static class PrintSecond implements Runnable {
		volatile private AtomicInteger i;
		public PrintSecond(AtomicInteger i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 1){System.out.println("Waiting for two.");}
			System.out.println("two");
			i.getAndIncrement();
		}
	}
	
	static class PrintThird implements Runnable {
		volatile private AtomicInteger i;
		public PrintThird(AtomicInteger i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 2){System.out.println("Waiting for three.");}
			System.out.println("three");
			i.getAndIncrement();
		}
	}
	
	static class ThreadA extends Thread {
		public void start() {
			
		}
	}
	
	public static void main(String[] args) {
		AtomicInteger i = new AtomicInteger();
		Runnable printFirst = new PrintFirst(i) ;
		Runnable printSecond = new PrintSecond(i) ;
		Runnable printThird = new PrintThird(i) ;
		Thread[] threads = new Thread[]{new Thread(printFirst), new Thread(printSecond), new Thread(printThird)};

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String input = br.readLine();
			String[] order = input.substring(1, 6).split(",");
			for(String threadIndex : order) {
				threads[Integer.parseInt(threadIndex)-1].start();
			}
		} catch(IOException e) {
		}
	}
	
    public void first(Runnable printFirst) throws InterruptedException {
        
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {
        
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

}
