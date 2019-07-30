package hackerrank.javaadv.datastructures;

import java.util.*;

public class ParenthesisBalanceChecker {
	
	enum ParenthesisType {
		CURLY,
		SQUARE,
		ROUND;
	}
	
	public static class Parenthesis {
		private ParenthesisType type;
		private boolean isOpen;
		
		public Parenthesis(char c) {
			switch(c) {
				case '[':
					type = ParenthesisType.SQUARE;
					isOpen = true;
					break;
				case ']':
					type = ParenthesisType.SQUARE;
					isOpen = false;
					break;
				case '{':
					type = ParenthesisType.CURLY;
					isOpen = true;
					break;
				case '}':
					type = ParenthesisType.CURLY;
					isOpen = false;
					break;
				case '(':
					type = ParenthesisType.ROUND;
					isOpen = true;
					break;
				case ')':
					type = ParenthesisType.ROUND;
					isOpen = false;
					break;
			}
		}
		
		public Parenthesis(ParenthesisType type, boolean isOpen) {
			super();
			this.type = type;
			this.isOpen = isOpen;
		}
		
		public ParenthesisType getType() {
			return type;
		}
		public boolean isOpen() {
			return isOpen;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (isOpen ? 1231 : 1237);
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Parenthesis other = (Parenthesis) obj;
			if (isOpen != other.isOpen)
				return false;
			if (type != other.type)
				return false;
			return true;
		}
	}
	
	public static boolean isBalancedParenthesis(String s) {
		Stack<Parenthesis> stack = new Stack<>();
		for(char c : s.toCharArray()) {
			Parenthesis p = new Parenthesis(c);
			if(p.isOpen()) {
				stack.push(p);
			} else {
				if(stack.isEmpty()) return false;
				Parenthesis open = stack.pop();
				if(open.getType() != p.type || !open.isOpen())
					return false;
			}
		}
		return stack.isEmpty();
	}
	
	public static void main(String []argh)
	{
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNext()) {
			String input=sc.next();
            //Complete the code
			System.out.println(isBalancedParenthesis(input));
		}
		
	}
}
