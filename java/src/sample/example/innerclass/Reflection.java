package sample.example.innerclass;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflection {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Class cA = A.B.class;
		for(Constructor con : cA.getConstructors()) {
			System.out.println("Constructor : " + con);
		}
		for(Constructor con : cA.getDeclaredConstructors()) {
			System.out.println("Declared Constructor : " + con);
		}
		for(Method m : cA.getMethods()) {
			System.out.println("Method : " + m);
		}
		for(Method m : cA.getDeclaredMethods()) {
			System.out.println("Declared Method : " + m);
		}
		
		Constructor c = cA.getDeclaredConstructor(A.class);
		System.out.println(c.isAccessible());
		c.setAccessible(true);
		Method m = cA.getDeclaredMethod("getText");
		m.setAccessible(true);
		System.out.println(c.isAccessible());
		out.println("A.B.getText() : " 
							+ m.invoke(
										c.newInstance(new A())));
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Integer> values = new ArrayList<>();
		Arrays.stream(br.readLine().trim().split(" "))
			.filter(s->s.trim().length()>0)
			.forEach(s->values.add(Integer.parseInt(s.trim())));
		System.out.println(values);
	}
	
	static class A  {
		private class B {
			private String getText() {
				return "A.B";
			}
		}
	}

}
