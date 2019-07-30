package sample.example.multithread;

public class FooBar {

	static class FooBarMutex {
		boolean isFoo = true;
		public boolean isFoo() {
			return isFoo;
		}
		public void toggle() {
			isFoo = Boolean.logicalXor(isFoo, true);
		}
	}
	
	static class PrintFoo implements Runnable {
		private FooBarMutex mutex;
		private int n;
		public PrintFoo(FooBarMutex mutex, int n) {
			this.mutex = mutex;
			this.n = n;
		}
		public void run() {
			for(int i=0; i<n; i++) {
				try {
					synchronized(mutex) {
						while(!mutex.isFoo()){
//							System.out.println("Waiting for foo mutex " + i);
							mutex.wait();
//							System.out.println("Acquired foo mutex " + i);
						}
						System.out.print("Foo");
						mutex.toggle();
//						System.out.println("Releasing bar mutex " + i);
						mutex.notifyAll();
					}
					Thread.sleep(new Double(1000*Math.random()).longValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class PrintBar implements Runnable {
		private FooBarMutex mutex;
		private int n;
		public PrintBar(FooBarMutex mutex, int n) {
			this.mutex = mutex;
			this.n = n;
		}
		public void run() {
			for(int i=0; i<n; i++) {
				try {
					synchronized(mutex) {
						while(mutex.isFoo()){
//							System.out.println("Waiting for bar mutex " + i);
							mutex.wait();
//							System.out.println("Acquired bar mutex " + i);
						}
						System.out.println("Bar " + i);
						mutex.toggle();
//						System.out.println("Releasing foo mutex " + i);
						mutex.notifyAll();
					}
					Thread.sleep(new Double(1000*Math.random()).longValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		FooBarMutex mutex = new FooBarMutex();
		int n = 10;
		new Thread(new PrintBar(mutex, n)).start();
		new Thread(new PrintFoo(mutex, n)).start();
	}

}
