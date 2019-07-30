package leetcode.mock.google.random;

public class StingGCD {

	public static void main(String[] args) {
		String str1 = "TAUXXTAUXXTAUXXTAUXXTAUXX";
		String str2 = "TAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXX";
		String[] ss = str1.split(str2);
		System.out.println(new StingGCD().gcdOfStrings(str1, str2));
	}

	public String gcdOfStrings(String str1, String str2) {
		if (str2.length() > str1.length()) {
			String t = str2;
			str2 = str1;
			str1 = t;
		}
		String gcd = str2;
		int i = 2;
		while (gcd.length() > 1 || gcd.length()==str2.length()) {
//			System.out.println("GCD check: " + gcd);
			if (isDivisible(str1, gcd) && isDivisible(str2, gcd)) {
				return gcd;
			}
			while (str2.length() % i != 0 ) {
				i++;
			}
			int gcdLen = str2.length() / i;
			gcd = str2.substring(0, gcdLen);
		}
		return "";
	}

	private boolean isDivisible(String s, String t) {
		if (s.length() % t.length() > 0)
			return false;
		return s.split(t).length == 0;
	}

}
