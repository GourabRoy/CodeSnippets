package sample.example.inheritance;

public class A {
	
	public static String print() {
		String s = "Inside Static A";
		System.out.println(s);
		return s;
	}
	
	public String print2() {
		String s = "Inside Non-Static A";
		System.out.println(s);
		return s; 
	}
	
	private void print3(){}
}
