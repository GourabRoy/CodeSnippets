package sample.example.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CompletableFuture<String> cf = CompletableFuture.supplyAsync(new Supplier<String>() {
			@Override
			public String get() {
				int i=0;
				while(i++ < 20){
					try {
						Thread.sleep(1000*5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(i);
				}
				return Integer.toString(i);
			}
		});
		
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("cf.get() = " + cf.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}).start();

		int i=0;
		while(i++ < 5){
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//cf.complete("500");
		
	}

}
