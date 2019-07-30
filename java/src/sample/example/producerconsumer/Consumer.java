package sample.example.producerconsumer;

import java.util.List;

public class Consumer implements Runnable {
	private List<String> queue;
	
	public Consumer(List<String> queue) {
		this.queue = queue;
	}
	
	public void run() {
		while(true) {
			try {
				synchronized(queue) {
					queue.wait();
					if(queue.size() > 0) consume();
					queue.notify();
				}
				Thread.sleep(new Double(1000*Math.random()).longValue());
			} catch (InterruptedException e) {
				// todo
			}
		}
	}
	
	private void consume() {
		System.out.println(Thread.currentThread() + " Consumed : " + queue.remove(0));
	}
}
