package sample.example.producerconsumer;

import java.util.List;

public class Producer implements Runnable {
	private List<String> queue;
	
	public Producer(List<String> queue) {
		this.queue = queue;
	}
	
	public void run() {
		while(true) {
			try {
				synchronized(queue) {
					if(queue.size() < 2) produce();
					queue.notifyAll();
				}
				Thread.sleep(new Double(1000*Math.random()).longValue());
			} catch (InterruptedException e) {
				
			}
		}
	}
	private void produce() {
		String message = "Message : " + Math.random();
		queue.add(message);
		System.out.println(Thread.currentThread() + " Produced : " + message);
	}

}
