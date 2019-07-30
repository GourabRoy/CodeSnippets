package sample.example.multithread;

// using wait and notify
public class PrintInOrder2 {

	static class PrintFirst implements Runnable {
		volatile private Counter i;
		public PrintFirst(Counter i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 0){
				synchronized(i) {
					System.out.println("Acquired lock waiting for one.");
					try {
						i.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					if(i.get() % 3 != 0) {
//						System.out.println("Released lock waiting for one");
//						i.notifyAll();
//						continue;
//					} 
				}
			}
			
			synchronized(i) {
				System.out.println("one");
				i.add();
				System.out.println("Released lock printing one");
				i.notify();
			}
		}
	}
	
	static class PrintSecond implements Runnable {
		volatile private Counter i;
		public PrintSecond(Counter i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 1){
				synchronized(i) {
					System.out.println("Acquired lock waiting for two.");
					try {
						i.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					if(i.get() % 3 != 1) {
//						System.out.println("Released lock waiting for two");
//						i.notifyAll();
//						continue;
//					}
				}
			}
			synchronized(i) {
				System.out.println("two");
				i.add();;
				System.out.println("Released lock printing two.");
				i.notifyAll();
			}
		}
	}
	
	static class PrintThird implements Runnable {
		volatile private Counter i;
		public PrintThird(Counter i) {
			this.i = i;
		}
		
		public void run() {
			while(i.get() % 3 != 2){
				synchronized(i) {
					System.out.println("Acquired lock waiting for three.");
					try {
						i.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(i.get() % 3 != 2) {
						System.out.println("Released lock waiting for three");
						i.notifyAll();
						continue;
					}
				}
			}
			synchronized(i) {
				System.out.println("three");
				System.out.println("Released lock printing three");
				i.notifyAll();
			}
		}
	}
	
	public static void main(String[] args) {
		Counter i = new Counter();
		Runnable printFirst = new PrintFirst(i) ;
		Runnable printSecond = new PrintSecond(i) ;
		Runnable printThird = new PrintThird(i) ;

		new Thread(printThird).start();
		new Thread(printSecond).start();
		new Thread(printFirst).start();
	}
}
