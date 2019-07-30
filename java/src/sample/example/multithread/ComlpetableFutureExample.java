package sample.example.multithread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComlpetableFutureExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Supplier<String> supplier = new Supplier<String>() { 
			public String get() {
				for(int i=0; i<5; i++) {
					try {
						Thread.sleep(1000);
						System.out.println("Seconds passed : " + (i+1));
					} catch (InterruptedException e) {
						System.out.println("Interrupted");
					}
				}
				return " World!";
			}
		};
		System.out.println("Message: " + CompletableFuture.supplyAsync(supplier).thenApply(s -> "Hello" + s).get());
	}

}
