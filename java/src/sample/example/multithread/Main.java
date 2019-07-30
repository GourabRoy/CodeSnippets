package sample.example.multithread;

public class Main {
	
	public static void main(String[] args) {
		Counter c = new Counter();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				Singleton instance = Singleton.INSTANCE;
				while(c.count.get()<100) {
					int x;
					if((x=c.count.get()) % 3 == 0) {
						System.out.println(" Thread 1 :: " + Thread.currentThread().getName() + " : " + (x+1));
						c.count.getAndIncrement();
					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				Singleton instance = Singleton.INSTANCE;
				while(c.count.get()<100) {
					int x;
					if((x=c.count.get()) % 3 == 1) {
						System.out.println(" Thread 2 :: " + Thread.currentThread().getName() + " : " + (x+1));
						c.count.getAndIncrement();
					}
				}
			}
		});

		Thread t3 = new Thread(new Runnable() {
			public void run() {
				Singleton instance = Singleton.INSTANCE;
				while(c.count.get()<100) {
					int x;
					if((x=c.count.get()) % 3 == 2) {
						System.out.println(" Thread 3 :: " + Thread.currentThread().getName() + " : " + (x+1));
						c.count.getAndIncrement();
					}
				}
				for(int i=0; i<Integer.SIZE; i++) {
					int COUNT_BITS = Integer.SIZE - i;
					int CAPACITY   = (1 << COUNT_BITS);
					int CAPACITY2   = (1 << COUNT_BITS) - 1;
					System.out.println(i + " : " + COUNT_BITS + " : " + CAPACITY + " : " + CAPACITY2);
				}
			    
			}
		});
		t1.start();
		t2.start();
		t3.start();
	}

}
