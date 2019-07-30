package sample.example.inheritance;

public class B extends A {
	
	public static String print() {
		String s = "Inside Static B";
		System.out.println(s);
		return s;
	}
	
	public String print2() {
		String s = "Inside Non-Static B";
		System.out.println(s);
		return s; 
	}
	
	public int print3(){return 0;}
}
