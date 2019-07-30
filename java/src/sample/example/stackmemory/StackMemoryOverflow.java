package sample.example.stackmemory;

public class StackMemoryOverflow {
	
	public static void main(String[] args) {
		tooBig();
	}
	
	static void tooBig() {
		int[] arr = new int[Integer.MAX_VALUE];
	}
}