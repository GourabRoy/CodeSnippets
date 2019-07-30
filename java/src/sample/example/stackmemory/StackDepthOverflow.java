package sample.example.stackmemory;

public class StackDepthOverflow {
	
	public static void main(String[] args) {
		recurse(0);
	}
	
	static void recurse(int depth) {
		System.out.println(depth);
		recurse(depth+1);
	}

}