package sample.example.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class FutureExample1 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		Function<String, Void> printHello = new Function<String, Void>() {
			public Void apply(String name) {
				System.out.println("Hello " + name);
				return null;
			}
		};
		executor.execute(new Runnable() {
			public void run() {
				for(int i=0; i<5; i++) {
					try {
						Thread.sleep(1000);
						System.out.println("Seconds passed : " + (i+1));
					} catch (InterruptedException e) {
						System.out.println("Interrupted");
					}
				}
				printHello.apply("Gourab");
			}
		});
	}

}
