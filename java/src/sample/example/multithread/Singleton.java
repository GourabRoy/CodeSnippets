package sample.example.multithread;

public class Singleton {
	public static final Singleton INSTANCE = new Singleton();
	static {
		System.out.println("Loaded by thred : " + Thread.currentThread().getName());
	}
	private Singleton(){}
}
