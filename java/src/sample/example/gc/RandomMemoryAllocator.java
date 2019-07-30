package sample.example.gc;

import java.util.LinkedList;
import java.util.List;

public class RandomMemoryAllocator {
	
	static public void allocate() {
		int SIZE = 1024*128;
		
		// Allocate Doubles :: SIZE * 8 bytes = 1MB
		List<Double> doubleList = new LinkedList<>();
		for(int i=0; i<SIZE; ) {
			double d = Math.random();
			if( d > 0.5) {
				doubleList.add(d);
				i++;
			}
		}
		
		// Allocate Integer Arrays of size 10 :: SIZE * 80 bytes = 10 MB
		List<Double[]> doubleArrayList = new LinkedList<>();
		for(int i=0; i<SIZE; ) {
			double d = Math.random();
			Double[] dArr = new Double[10];
			for(int j=0; j<10; j++) {
				dArr[j] = (d);
			}
			if( d > 0.5) {
				doubleArrayList.add(dArr);
				i++;
			}
		}
		
		// 44 MB + references consumed till now
		
		SIZE = 1;
		// Allocate Integer Arrays of size 10000 :: SIZE * 80000 bytes
		List<Double[]> doubleArrayList3 = new LinkedList<>();
		for(int i=0; i<SIZE; ) {
			double d = Math.random();
			Double[] dArr = new Double[10000];
			for(int j=0; j<10000; j++) {
				dArr[j] = d;
			}
			if( d > 0.5) {
				doubleArrayList3.add(dArr);
				i++;
			}
		}
		
		System.out.println("Allocated all data.");
	}

}
