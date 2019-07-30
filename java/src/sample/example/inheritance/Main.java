package sample.example.inheritance;

public class Main {

	public static void main(String[] args) {
		A a = new A();
		a.print();
		a.print2();
		
		A b = new B();
		b.print();
		System.out.println(b);
		b.print2();

		B b2 = new B();
		b2.print();
		System.out.println(b2.getClass());
		b2.print2();
	}

}
