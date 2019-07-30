package sample.example.lambdaexp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	
	public static boolean isVowel(char c) {
		switch(Character.toLowerCase(c)) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return true;
		}
		
		return false;
	}
	
	public static boolean containsNVowels(String s, int n) {
		int j=0;
		for(int i=0; i<s.length() && j<n; i++)
			if(isVowel(s.charAt(i))) j++;
		
		return j==n;	
	}
	
	public static void main(String[] args) {
		List<String> names = new ArrayList<>();
		for(String s : new String[]{"Gourab", "Apurva", "Sai", "Joy", "Satyan", "Purvesh", "Deepak", "Amit"}) {
			names.add(s); 
		}
		
		//Collections.sort(names, (a,b)->a.length()%2-b.length()%2);
		Stream<String> filteredNameStream = names.stream().filter(a -> a.length()>2).filter(name->containsNVowels(name, 3));
		
		filteredNameStream.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
				
			}

		});
		
		Collection<String> c = names.parallelStream().filter(a -> a.length()>2).filter(name->containsNVowels(name, 2)).collect(Collectors.toList());
		c.forEach(new Consumer<String>() {
			public void accept(String s) {
				System.out.println(s);
			}
		});

		
		
		
		
	}
}
