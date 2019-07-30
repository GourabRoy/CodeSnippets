package hackerrank.javaadv.datastructures;

import java.util.*;

public class LeapOrWalk {

	public static boolean canWin(int leap, int[] game) {
		// Return true if you can win the game; otherwise, return false.
		Set<Integer> traversed = new HashSet<>();
		return canWinRecursive(0, leap, game, traversed);
	}
	
	public static boolean canWinRecursive(int i, int leap, int[] game, Set<Integer> traversed) {
//		System.out.println(i);
		traversed.add(i);
		// terminal condition
		if(i>game.length-1)
			return true;
		if(i<0)
			return false;
		if(game[i] != 0)
			return false;
		
		if(!traversed.contains(i+leap) && canWinRecursive(i+leap, leap, game, traversed)) 
			return true;
		if(!traversed.contains(i+1) && canWinRecursive(i+1, leap, game, traversed))
			return true;
		if(!traversed.contains(i-1) && canWinRecursive(i-1, leap, game, traversed))
			return true;
		return false;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int q = scan.nextInt();
		while (q-- > 0) {
			int n = scan.nextInt();
			int leap = scan.nextInt();

			int[] game = new int[n];
			for (int i = 0; i < n; i++) {
				game[i] = scan.nextInt();
			}

			System.out.println((canWin(leap, game)) ? "YES" : "NO");
		}
		scan.close();
	}
}
