package sample.example.niochannels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializerDeserializer {
	static byte[] fromObject = null;
	static byte[] toObject = null;
	
	public static byte[] getByteArray(Object o) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.flush();
		byte[] byteArray = baos.toByteArray();
		oos.close();
		baos.close();
		System.out.println(Thread.currentThread().getName() + " Object Serialized: byteArraySize=" + byteArray.length);
		return byteArray;
	}
	
	public static Object getObject(byte[] byteArray) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object o = ois.readObject();
		ois.close();
		bais.close();
		System.out.println(Thread.currentThread().getName() + " Object De-serialized: byteArraySize=" + byteArray.length);
		return o;
	}

	static void compareByteArrays() {
		if(fromObject == null || toObject == null) {
			System.out.println("NUll byte array.");
			return;
		}
		if(fromObject.length != toObject.length) {
			System.out.println("Byte arrays of different length.");
			return;
		}
		for(int i=0; i<fromObject.length; i++) {
			if(fromObject[i] != toObject[i]) {
				System.out.println("Byte different at " + i + " fromObj=" + fromObject[i] + " toObject=" + toObject[i]);
			}
		}
	}
}
