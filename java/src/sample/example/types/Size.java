package sample.example.types;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sample.example.instrumentation.Agent;

public class Size {
	
	interface Sizeable {
		long getSize();
	}
	
	static public class IntArray implements Serializable, Sizeable {
		private int[] array;
		public IntArray(int SIZE) {
			array = new int[SIZE];
		}
		
		public long getSize() {
			return Agent.getObjectSize(array);
		}
	}
	
	static public class IntegerArray implements Serializable, Sizeable {
		private Integer[] array;
		public IntegerArray(int SIZE) {
			array = new Integer[SIZE];
			for(int i=0; i<SIZE; i++) {
				array[i] = new Integer(i);
			}
		}

		public long getSize() {
			return Agent.getObjectSize(array);
		}
	}
	
	static public class ObjectArray implements Serializable, Sizeable {
		private Object[] array;
		public ObjectArray(int SIZE) {
			array = new Object[SIZE];
			for(int i=0; i<SIZE; i++) {
				array[i] = new String();
			}
		}

		public long getSize() {
			return Agent.getObjectSize(array);
		}
	}
	
	static public int getSizeInBytes(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			int size = baos.toByteArray().length;
			oos.close();
			baos.close();
			return size;
		} catch (IOException e) {
			System.out.println(e);
		}
		return -1;
	}
	
	static public long getInstrumentationSize(Sizeable object) {
		return //Agent.getObjectSize(object) + 
				object.getSize();
	}
	
	public static void main(String[] args) {
//		int size = 2;
		System.out.println("Size of Integer=" + getSizeInBytes(new Integer(0)) + " :: " + Agent.getObjectSize(new Integer(0)));
		
		for(int size=0; size<10; size++) {
			IntArray intArray = new IntArray(size);
			System.out.println("Size of int[" + size + "]=" + getSizeInBytes(intArray) + " :: " + getInstrumentationSize(intArray));
			IntegerArray integerArray = new IntegerArray(size);
			System.out.println("Size of Integer[" + size + "]=" + getSizeInBytes(integerArray) + " :: " + getInstrumentationSize(integerArray));
			ObjectArray objectArray = new ObjectArray(size);
			System.out.println("Size of Object[" + size + "]=" + getSizeInBytes(objectArray) + " :: " + getInstrumentationSize(objectArray));
		}
	}

}
