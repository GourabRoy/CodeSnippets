package sample.example.niochannels.remoteproxy;

import sample.example.propertycontrol.PropertyLoader;

public class RemoteProxyTestMain {

	public static void main(String[] args) throws InterruptedException {
		new PropertyLoader().init();
		MatrixProxy proxy = new MatrixProxy();
		while(true) {
			test(proxy);
			Thread.sleep(5*1000);
		}
	}

	private static void test(MatrixProxy proxy) {
		Matrix.Size size = new Matrix.Size(5, 5);
		Matrix m1 = new Matrix(size);
		Matrix m2 = new Matrix(size);
		
		int X = new Double((Math.random()*10.0)).intValue();
		for(int i=0; i<size.getX(); i++) {
			for(int j=0; j<size.getY(); j++) {
				m1.set(i, j, X-i-j);
				m2.set(i, j, i+j);
			}
		}
		
		Matrix result = proxy.add(m1, m2);
		System.out.println(X);
		System.out.println(result);

	}
}
