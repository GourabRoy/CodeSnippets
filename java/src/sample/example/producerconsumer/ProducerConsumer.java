package sample.example.producerconsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> queue = new LinkedList<>();
		
		Producer p1 = new Producer(queue);
		Producer p2 = new Producer(queue);
		Producer p3 = new Producer(queue);
		
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		
		ExecutorService executor = Executors.newFixedThreadPool(6);
		executor.execute(p1);
		executor.execute(p2);
		executor.execute(p3);
		executor.execute(c1);
		executor.execute(p2);
		
	}

}
