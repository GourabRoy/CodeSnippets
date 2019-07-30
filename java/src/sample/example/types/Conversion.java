package sample.example.types;

public class Conversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		precisionLossDuringNarrowing();
		complement();
		precisionLossDuringWidening();
	}

	public static void precisionLossDuringNarrowing() {
		for(long l=Integer.MAX_VALUE-1; l<(long)Integer.MAX_VALUE+10l; l++) {
			int i = (int)l;
			System.out.println(l);
			if(i<0) {
				System.out.println("l=" + l + " i=" + i + " sign inverted. precesion loss");
				break;
			}
		}
	}
	
	public static void precisionLossDuringWidening() {
		for(int i = -10; i<10; i++) {
			long l = i;
			System.out.println(i + " : " + l);
		}
	}

	public static void complement() {
		for(int i=-20; i<20; i++) {
			System.out.println(i + " " + ~i + " " + (Integer.MAX_VALUE - i));
		}
		test3(0);
	}
	
	public static double test2(double d) {
		return d;
	}

	public static long test3(long d) {
		return d;
	}
}
